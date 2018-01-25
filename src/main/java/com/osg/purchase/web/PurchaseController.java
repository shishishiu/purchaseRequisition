package com.osg.purchase.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.osg.purchase.form.PurchaseHeaderEditForm;
import com.osg.purchase.form.PurchaseItemEditForm;
import com.osg.purchase.repository.PurchaseItemRepository;
import com.osg.purchase.repository.PurchaseRepository;
import com.osg.purchase.service.PurchaseItemService;
import com.osg.purchase.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
@SessionAttributes(names={"sessionPurchaseCriteriaForm","sessionPurchaseHeaderEditForm"})
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
    /***
     * save header in the session when create new header and new item
     * @return
     */
    @ModelAttribute("sessionPurchaseHeaderEditForm")
    public PurchaseHeaderEditForm setSessionPurchaseHeaderEditForm(){
        return new PurchaseHeaderEditForm();
    }

	
	@RequestMapping(value="/search", method=RequestMethod.GET)
    public ModelAndView index(SessionStatus sessionStatus) {
		
		sessionStatus.setComplete();
		
		PurchaseCriteriaForm form = new PurchaseCriteriaForm();
		ModelAndView mv = new ModelAndView();
		mv.addObject("purchaseCriteriaForm", form);
		mv.setViewName("purchase/search");
		
		 return mv;
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

        if (result.hasErrors()) {
        	return mv;
        		
        }
        	
        //add session
        model.addAttribute("sessionPurchaseCriteriaForm", form);        

        List<PurchaseEntity> list = searchPurchase(form);
        
        mv.addObject("list", list);
          
        return mv;
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


	@RequestMapping(value="/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("purchaseId") int purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	setPurchase(purchaseId, mv);

    	mv.setViewName("purchase/detail");

		return mv;
    }

	private void setPurchase(int purchaseId, ModelAndView mv) {
    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("form", form);
 
       	List<PurchaseItemEntity> list = entity.getPurchaseItemList().stream()
    	.filter(p -> p.getIsDeleted()==0).collect(Collectors.toList());
    	mv.addObject("itemList", list);
		
	}

	@RequestMapping(value="/eheader/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView editHeader(@PathVariable("purchaseId") int purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("form", form);
 
    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/editHeader");

		return mv;
    }

	@RequestMapping(value="/saveHeader", method=RequestMethod.POST)
    public ModelAndView saveHeader(@ModelAttribute("form") @Valid PurchaseHeaderEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser, Model model) {
    	
    	ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createIsDomesticCombo(mv);
        	createCompanyCombo(mv);        	
        	mv.setViewName("purchase/editHeader");
        	return mv;
        		
        } else{

        	if(form.getPurchaseId()<=0) {
            	
                //add session
        		model.addAttribute("sessionPurchaseHeaderEditForm", form);
        		return addItem(0);
        		        		
        	}else {
            	PurchaseEntity entity = new PurchaseEntity();
            	BeanUtils.copyProperties(form, entity);
            	
            	purchaseService.updatePurchaseHeader(entity, loggedInUser.getUserId());
            	
            	setPurchase(form.getPurchaseId(), mv);
            	mv.setViewName("purchase/detail");
        		
        	}
        	
    		return mv;
        	
        }
        
    }

	@RequestMapping(value="/eitem/{purchaseItemId}", method=RequestMethod.GET)
    public ModelAndView editItem(@PathVariable("purchaseItemId") int purchaseItemId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseItemEntity entity = purchaseItemRepository.findByPurchaseItemId(purchaseItemId);
    	
    	PurchaseItemEditForm form = new PurchaseItemEditForm();
    	BeanUtils.copyProperties(entity, form);
    	mv.addObject("form", form);
 
    	createCurrencyCombo(mv);
    	
    	mv.setViewName("purchase/editItem");

		return mv;
    }

	@Transactional
	@RequestMapping(value="/saveItem", method=RequestMethod.POST)
    public ModelAndView saveItem(@ModelAttribute("form") @Valid PurchaseItemEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser, 
    		@ModelAttribute("sessionPurchaseHeaderEditForm")PurchaseHeaderEditForm purchaseHeaderEditForm,
    		Model model) {
    	
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createCurrencyCombo(mv);        	
        	mv.setViewName("purchase/editItem");
        	return mv;
        		
        } else{


        	PurchaseItemEntity entity = new PurchaseItemEntity();
        	BeanUtils.copyProperties(form, entity);
        	
        	
        	if(purchaseHeaderEditForm.getPurchaseId()<=0) {
        		//register new Header
            	PurchaseEntity pentity = new PurchaseEntity();
            	BeanUtils.copyProperties(purchaseHeaderEditForm, pentity);
            	
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
            
            if(purchaseHeaderEditForm.getPurchaseId()<=0) {
            	
	            PurchaseCriteriaForm sform = new PurchaseCriteriaForm();
	            sform.setPId(Integer.toString(entity.getPurchaseId()));
	            mv.setViewName("purchase/search");        	
		        //add session
		        model.addAttribute("sessionPurchaseCriteriaForm", sform);        
	
	            mv.addObject("purchaseCriteriaForm", sform);
	            List<PurchaseEntity> list = searchPurchase(sform);
	            
	            mv.addObject("list", list);
	            
            } else {
            
            	setPurchase(form.getPurchaseId(), mv);
            	mv.setViewName("purchase/detail");
           	
            	
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
    	
    	ModelAndView mv = new ModelAndView();
    	
    	purchaseItemService.deleteLogical(loggedInUser.getUserId(), purchaseItemId);
    	
    	setPurchase(purchaseId, mv);
    	mv.setViewName("purchase/detail");
		return mv;
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

	@RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addHeader(@ModelAttribute MemberEntity loggedInUser) {
    	
    	ModelAndView mv = new ModelAndView();
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	form.setUserId(loggedInUser.getUserId());
    	form.setUsername(loggedInUser.getUsername());
    	mv.addObject("form", form);
 
    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/editHeader");

		return mv;
    }


   @RequestMapping(value="/addItem/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView addItem(@PathVariable("purchaseId") int purchaseId) {
    	
	   	ModelAndView mv = new ModelAndView();
	
	   	PurchaseItemEditForm form = new PurchaseItemEditForm();
	   	form.setPurchaseId(purchaseId);
	   	mv.addObject("form", form);
	
	   	createCurrencyCombo(mv);
	   	
	   	mv.setViewName("purchase/editItem");

    	
        return mv;
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

    @RequestMapping(value="/back", method=RequestMethod.POST)
    public ModelAndView backToSearch(@ModelAttribute("sessionPurchaseCriteriaForm")PurchaseCriteriaForm form, 
    		SessionStatus sessionStatus) {
    	
        ModelAndView mv = new ModelAndView();
        mv.addObject("purchaseCriteriaForm", form);
        List<PurchaseEntity> list = searchPurchase(form);        
        mv.addObject("list", list);
      
        mv.setViewName("purchase/search");
        

        return mv;
   }

    @Autowired
    ResourceLoader resourceLoader;

	@RequestMapping(value="/dl", method=RequestMethod.POST)
    public ModelAndView dl(@RequestParam("purchaseId") int purchaseId) {

        String filepath = "static/excel/templatePurchase.xlsx";
        Resource resource = resourceLoader.getResource("classpath:" + filepath);
        String filepathImg = "static/excel/logo.jpg";
        Resource resourceimg = resourceLoader.getResource("classpath:" + filepathImg);
        File file = null;
        File fileImg = null;
        try
        {
			file = resource.getFile();
			fileImg = resourceimg.getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	Map<String, Object> map = new HashMap<>();
    	map.put("purchase", entity);
    	map.put("template", file);
    	map.put("logo", fileImg);

		ModelAndView mv = new ModelAndView(new ExcelBuilder(),map);

         
//        mv.addObject("fileName", "POI" + ".xlsx");

        return mv;
    }

   
}