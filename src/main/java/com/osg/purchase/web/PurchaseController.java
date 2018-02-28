package com.osg.purchase.web;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseEntity.CompanyValue;
import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;
import com.osg.purchase.entity.PurchaseItemEntity;
import com.osg.purchase.entity.PurchaseItemEntity.Currency;
import com.osg.purchase.excel.ExcelBuilder;
import com.osg.purchase.form.PurchaseCriteriaForm;
import com.osg.purchase.form.PurchaseHeaderDeliveredDateForm;
import com.osg.purchase.form.PurchaseHeaderEditForm;
import com.osg.purchase.form.PurchaseItemEditForm;
import com.osg.purchase.repository.PurchaseItemRepository;
import com.osg.purchase.repository.PurchaseRepository;
import com.osg.purchase.service.PurchaseItemService;
import com.osg.purchase.service.PurchaseService;

@Controller
@RequestMapping("purchase")
//@SessionAttributes(names={"sessionPurchaseCriteriaForm","sessionPurchaseHeaderEditForm"})
@SessionAttributes(names={"sessionPurchaseCriteriaForm"})
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	@Autowired
	PurchaseItemRepository purchaseItemRepository;
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	PurchaseItemService purchaseItemService;

	/***
	 * save search condition in the session
	 * @return
	 */
    @ModelAttribute("sessionPurchaseCriteriaForm")
    public PurchaseCriteriaForm setSessionPurchaseCriteriaForm(){
        return new PurchaseCriteriaForm();
    }
//    /***
//     * save header in the session when create new header and new item
//     * @return
//     */
//    @ModelAttribute("sessionPurchaseHeaderEditForm")
//    public PurchaseHeaderEditForm setSessionPurchaseHeaderEditForm(){
//        return new PurchaseHeaderEditForm();
//    }

    final static Map<String, String> IS_DELIVERED_ITEMS = 
    		Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
    	    {
    	      put("No ha entregado", "0");
    	      put("Ya se entregó", "1");
    	    }
    	  });

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public ModelAndView index(SessionStatus sessionStatus, @ModelAttribute MemberEntity loggedInUser, Model model) {
		
		PurchaseCriteriaForm form = new PurchaseCriteriaForm();
		form.setSolicitanteId(loggedInUser.getUserId());
		
		return search(form, null, model);
		
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("isDeliveredItems", IS_DELIVERED_ITEMS);
//		mv.addObject("purchaseCriteriaForm", form);
//		mv.setViewName("purchase/search");
//
//        List<PurchaseEntity> list = searchPurchase(form);
//        
//        mv.addObject("list", list);
//
//        //add session
//        model.addAttribute("sessionPurchaseCriteriaForm", form);        
//
//		 return mv;
    }

	/***
	 * Search
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
    public ModelAndView search(@ModelAttribute("purchaseCriteriaForm") @Valid PurchaseCriteriaForm form, BindingResult result,
    		Model model) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("purchase/search");

        if (result != null && result.hasErrors()) {
        	return mv;
        		
        }
        	
        //add session
        model.addAttribute("sessionPurchaseCriteriaForm", form);        

        List<PurchaseEntity> list = searchPurchase(form);
        
		mv.addObject("isDeliveredItems", IS_DELIVERED_ITEMS);
		mv.addObject("list", list);
		mv.addObject("purchaseCriteriaForm", form);
          
        return mv;
    }
    

	@RequestMapping(value="/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("purchaseId") int purchaseId, @ModelAttribute MemberEntity loggedInUser) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	setPurchase(entity, mv);

    	mv.addObject("loggedinUser", loggedInUser.getUserId());
    	
    	mv.setViewName("purchase/detail");

		return mv;
    }

	@RequestMapping(value="/eheader", method=RequestMethod.POST)
    public ModelAndView editHeader(@RequestParam("purchaseId") int purchaseId, @ModelAttribute MemberEntity loggedInUser,
//    		@ModelAttribute("sessionPurchaseHeaderEditForm")PurchaseHeaderEditForm purchaseHeaderEditForm,
    		@ModelAttribute("headerForm") PurchaseHeaderEditForm headerForm) {
    	
    	ModelAndView mv = new ModelAndView();
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();

    	if(purchaseId == 0) {
    		
//    		if(purchaseHeaderEditForm != null) {
   			if(headerForm != null) {
    			
//    			BeanUtils.copyProperties(purchaseHeaderEditForm, form);
   				BeanUtils.copyProperties(headerForm, form);
   				
    			
    		}
    		
        	form.setUserId(loggedInUser.getUserId());
        	form.setUsername(loggedInUser.getUsername());
        	
    	}else {
        	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
        	BeanUtils.copyProperties(entity, form);
        	form.setUsername(entity.getMember().getUsername());
    	}
    	
    	mv.addObject("form", form);
 
    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/editHeader");

		return mv;
    }

	@RequestMapping(value="/saveHeader", method=RequestMethod.POST)
    public ModelAndView saveHeader(@RequestParam("purchaseId") int purchaseId, 
    		@ModelAttribute("form") @Valid PurchaseHeaderEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser, Model model) {
    	
    	ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createIsDomesticCombo(mv);
        	createCompanyCombo(mv);        	
        	mv.setViewName("purchase/editHeader");
        	return mv;
        		
        } else{

        	if(purchaseId<=0) {
            	
                //add session
//        		model.addAttribute("sessionPurchaseHeaderEditForm", form);
        		return editItem(purchaseId, 0, form);
        		        		
        	}else {
            	PurchaseEntity entity = new PurchaseEntity();
            	BeanUtils.copyProperties(form, entity);
            	
            	purchaseService.updatePurchaseHeader(entity, loggedInUser.getUserId());
            	
            	return showDetail(purchaseId, loggedInUser);
        		
        	}
        	        	
        }
        
    }

	@RequestMapping(value="/eitem", method=RequestMethod.POST)
    public ModelAndView editItem(@RequestParam("purchaseId") int purchaseId, 
    		@RequestParam("purchaseItemId")int purchaseItemId, PurchaseHeaderEditForm headerForm) {
    	
    	ModelAndView mv = new ModelAndView();
	   	PurchaseItemEditForm form = new PurchaseItemEditForm();

    	if(purchaseItemId <= 0) {

		   	form.setPurchaseId(purchaseId);
		   	mv.addObject("headerForm", headerForm);

    	} else {

        	PurchaseItemEntity entity = purchaseItemRepository.findByPurchaseItemId(purchaseItemId);
        	BeanUtils.copyProperties(entity, form);

    	}
    	
 
    	mv.addObject("form", form);
    	createCurrencyCombo(mv);
    	
    	mv.setViewName("purchase/editItem");

		return mv;
    }

	@Transactional
	@RequestMapping(value= {"/saveItem", "eheader/saveItem"}, method=RequestMethod.POST)
    public ModelAndView saveItem(@ModelAttribute("form") @Valid PurchaseItemEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser, 
//    		@ModelAttribute("sessionPurchaseHeaderEditForm")PurchaseHeaderEditForm purchaseHeaderEditForm,
    		@ModelAttribute("headerForm") PurchaseHeaderEditForm headerForm,
    		Model model, SessionStatus sessionStatus) {
    	
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createCurrencyCombo(mv);        	
        	mv.setViewName("purchase/editItem");
        	return mv;
        		
        } else{


        	PurchaseItemEntity entity = new PurchaseItemEntity();
        	BeanUtils.copyProperties(form, entity);

        	
        	if(headerForm.getPurchaseId()<=0) {
        		//register new Header
            	PurchaseEntity pentity = new PurchaseEntity();
            	BeanUtils.copyProperties(headerForm, pentity);
            	
            	pentity.setCreatedUserId(loggedInUser.getUserId());
            	pentity.setUpdatedUserId(loggedInUser.getUserId());
            	pentity = purchaseRepository.save(pentity);
            	
            	entity.setPurchaseId(pentity.getPurchaseId());
       		
        	}
        	            
            if(entity.getPurchaseItemId() <= 0) {

            	//register new Item
        		entity.setCreatedUserId(loggedInUser.getUserId());
        		entity.setUpdatedUserId(loggedInUser.getUserId());
        		purchaseItemRepository.save(entity);
        		
        	}else {
        		//update Item
            	purchaseItemService.updatePurchaseItem(entity, loggedInUser.getUserId());

        	}
            
            if(headerForm.getPurchaseId()<=0) {
            	
	            PurchaseCriteriaForm sform = new PurchaseCriteriaForm();
	            sform.setPId(Integer.toString(entity.getPurchaseId()));
	            mv.setViewName("purchase/search");
		        //add session
		        model.addAttribute("sessionPurchaseCriteriaForm", sform);        
	
	            mv.addObject("purchaseCriteriaForm", sform);
	            List<PurchaseEntity> list = searchPurchase(sform);
	            
	            mv.addObject("list", list);
	            
            } else {
            
            	return showDetail(entity.getPurchaseId(), loggedInUser);
            }
            
            return mv;
            
        }
        
    }

	/***
	 * delete an item
	 * @param purchaseId
	 * @param purchaseItemId
	 * @param loggedInUser
	 * @return
	 */
	@RequestMapping(value="/deleteItem", method=RequestMethod.POST)
    public ModelAndView deleteItem(@RequestParam("purchaseId") int purchaseId, 
    		@RequestParam("purchaseItemId")int purchaseItemId, @ModelAttribute MemberEntity loggedInUser) {
    	
    	purchaseItemService.deleteLogical(loggedInUser.getUserId(), purchaseItemId);
    	
		return showDetail(purchaseId,loggedInUser);
    }

	/***
	 * delete item and header
	 * @param purchaseId
	 * @param purchaseItemId
	 * @param loggedInUser
	 * @param form
	 * @param sessionStatus
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
    public ModelAndView delete(@RequestParam("purchaseId") int purchaseId, 
    		@RequestParam("purchaseItemId")int purchaseItemId, @ModelAttribute MemberEntity loggedInUser,
    		@ModelAttribute("sessionPurchaseCriteriaForm")PurchaseCriteriaForm form, 
    		SessionStatus sessionStatus) {
    	
    	
		//eliminar a item and the header
		purchaseService.deleteLogical(loggedInUser.getUserId(), purchaseId);
		purchaseItemService.deleteLogical(loggedInUser.getUserId(), purchaseItemId);
		return backToSearch(form, sessionStatus);
    }

    @RequestMapping(value="/back", method=RequestMethod.GET)
    public ModelAndView backToSearch(@ModelAttribute("sessionPurchaseCriteriaForm")PurchaseCriteriaForm form, 
    		SessionStatus sessionStatus) {
    	
        ModelAndView mv = new ModelAndView();
        mv.addObject("purchaseCriteriaForm", form);
        List<PurchaseEntity> list = searchPurchase(form);        
        mv.addObject("list", list);
		mv.addObject("isDeliveredItems", IS_DELIVERED_ITEMS);
        mv.setViewName("purchase/search");
//        sessionStatus.setComplete();

        return mv;
   }

    @Autowired
    ResourceLoader resourceLoader;

	@RequestMapping(value="/dl", method=RequestMethod.POST)
    public ModelAndView dl(@RequestParam("purchaseId") int purchaseId) {

        String filepathImg = "static/image/logo.jpg";
        Resource resourceimg = resourceLoader.getResource("classpath:" + filepathImg);
        File fileImg = null;
        try
        {
			fileImg = resourceimg.getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
       	List<PurchaseItemEntity> list = entity.getPurchaseItemList().stream()
    	.filter(p -> p.getIsDeleted()==0).collect(Collectors.toList());
       	entity.setPurchaseItemList(list);
       	
    	Map<String, Object> map = new HashMap<>();
    	map.put("purchase", entity);
    	map.put("logo", fileImg);

		ModelAndView mv = new ModelAndView(new ExcelBuilder(),map);

		return mv;
    }	

	@RequestMapping(value="/saveDeliverdDate", method=RequestMethod.POST)
    public ModelAndView saveDeliverdDate(@ModelAttribute("dForm") @Valid PurchaseHeaderDeliveredDateForm form, BindingResult result,
    		@RequestParam("purchaseId") int purchaseId,	@ModelAttribute MemberEntity loggedInUser) {
		
    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);    	
      
        if (result.hasErrors()) {
        	
            ModelAndView mv = new ModelAndView();
        	mv.setViewName("purchase/detail");

        	mv.addObject("form", entity);
        	 
        	mv.addObject("loggedinUser", loggedInUser.getUserId());

        	return mv;
        		
        } else{

	    	entity.setDeliveredDate(form.getDeliveredDate());
	    	entity.setIsDelivered(form.getIsDelivered());
	    	purchaseService.updatePurchaseHeader(entity, loggedInUser.getUserId());

        	return showDetail(purchaseId, loggedInUser);

        }

	}
	/***
	 * Search
	 * @param form
	 * @return
	 */
	private List<PurchaseEntity> searchPurchase(PurchaseCriteriaForm form) {
        PurchaseCriteriaForm sform = new PurchaseCriteriaForm(); 
        BeanUtils.copyProperties(form, sform);
        return purchaseRepository.findPurchases(sform);
	}

	private void setPurchase(PurchaseEntity entity, ModelAndView mv) {
    	
    	mv.addObject("form", entity);
 
//       	List<PurchaseItemEntity> list = entity.getPurchaseItemList().stream()
//    	.filter(p -> p.getIsDeleted()==0).collect(Collectors.toList());
//    	mv.addObject("itemList", list);
    	
    	if(entity.getIsDelivered()==0) {
    		mv.addObject("dForm",new PurchaseHeaderDeliveredDateForm());
    	}

		
	}

	private void createCompanyCombo(ModelAndView mv) {
    	mv.addObject("companyList", CompanyValue.values());
	}

	private void createIsDomesticCombo(ModelAndView mv) {
    	mv.addObject("isDomesticList", IsDomesticValue.values());
	}

	private void createCurrencyCombo(ModelAndView mv) {
    	mv.addObject("currencyList", Currency.values());
	}


   
}