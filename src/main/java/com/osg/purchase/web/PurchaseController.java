package com.osg.purchase.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseEntity.CompanyValue;
import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;
import com.osg.purchase.entity.PurchaseItemEntity;
import com.osg.purchase.entity.PurchaseItemEntity.Currency;
import com.osg.purchase.form.PurchaseCriteriaForm;
import com.osg.purchase.form.PurchaseHeaderEditForm;
import com.osg.purchase.form.PurchaseItemEditForm;
import com.osg.purchase.repository.PurchaseItemRepository;
import com.osg.purchase.repository.PurchaseRepository;
import com.osg.purchase.service.PurchaseItemService;
import com.osg.purchase.service.PurchaseService;

@Controller
@RequestMapping("/purchase")
@SessionAttributes(names="purchaseCriteriaForm")
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	@Autowired
	PurchaseItemRepository purchaseItemRepository;
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	PurchaseItemService purchaseItemService;
	
    @ModelAttribute("purchaseCriteriaForm")
    public PurchaseCriteriaForm setPurchaseCriteriaForm(PurchaseCriteriaForm form){
    	//TODO Eliminar session
        return form;
    }

	
	@RequestMapping("/search")
    public String index() {
        return "purchase/search";
    }

    /***
     * Buscar Requisicion
     * @param form
     * @param result
     * @return
     */
    @RequestMapping(value="/search", method=RequestMethod.POST)
    public ModelAndView search(@ModelAttribute("purchaseCriteriaForm") @Valid PurchaseCriteriaForm form, BindingResult result) {
    	  	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("purchase/search");

        setPurchaseCriteriaForm(form);

        List<PurchaseEntity> list = searchPurchase(form);
        
        mv.addObject("list", list);
          
        return mv;
    }
    
	private List<PurchaseEntity> searchPurchase(PurchaseCriteriaForm form) {
        PurchaseCriteriaForm purchaseCriteriaForm = new PurchaseCriteriaForm(); 
        BeanUtils.copyProperties(form, purchaseCriteriaForm);
        return purchaseRepository.findPurchases(purchaseCriteriaForm);
	}


	@RequestMapping(value="/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("purchaseId") String purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	setPurchase(purchaseId, mv);

    	mv.setViewName("purchase/detail");

		return mv;
    }

	private void setPurchase(String purchaseId, ModelAndView mv) {
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
    public ModelAndView editHeader(@PathVariable("purchaseId") String purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("form", form);
 
//    	mv.addObject("itemList", entity.getPurchaseItemList());

    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/editHeader");

		return mv;
    }

	@RequestMapping(value="/saveHeader", method=RequestMethod.POST)
    public ModelAndView saveHeader(@ModelAttribute("form") @Valid PurchaseHeaderEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser) {
    	
    	ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createIsDomesticCombo(mv);
        	createCompanyCombo(mv);        	
        	mv.setViewName("purchase/editHeader");
        	return mv;
        		
        } else{

        	PurchaseEntity entity = new PurchaseEntity();
        	BeanUtils.copyProperties(form, entity);
        	
        	purchaseService.updatePurchaseHeader(entity, loggedInUser.getUserId());
        	
        	setPurchase(form.getPurchaseId(), mv);
        	mv.setViewName("purchase/detail");
    		return mv;
        	
        }
        
    }

	@RequestMapping(value="/eitem/{purchaseItemId}", method=RequestMethod.GET)
    public ModelAndView editItem(@PathVariable("purchaseItemId") int purchaseItemId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseItemEntity entity = purchaseItemRepository.findByPurchaseItemId(purchaseItemId);
    	
    	PurchaseItemEditForm form = new PurchaseItemEditForm();
    	BeanUtils.copyProperties(entity, form);
//    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("form", form);
 
    	createCurrencyCombo(mv);
    	
    	mv.setViewName("purchase/editItem");

		return mv;
    }

	@RequestMapping(value="/saveItem", method=RequestMethod.POST)
    public ModelAndView saveItem(@ModelAttribute("form") @Valid PurchaseItemEditForm form, 
    		BindingResult result, @ModelAttribute MemberEntity loggedInUser) {
    	
    	ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        	
        	createCurrencyCombo(mv);        	
        	mv.setViewName("purchase/editItem");
        	return mv;
        		
        } else{

        	PurchaseItemEntity entity = new PurchaseItemEntity();
        	BeanUtils.copyProperties(form, entity);

        	if(form.getPurchaseItemId() <= 0) {

        		entity.setCreatedUserId(loggedInUser.getUserId());
        		entity.setUpdatedUserId(loggedInUser.getUserId());
        		purchaseItemRepository.save(entity);
        		
        	}else {

            	purchaseItemService.updatePurchaseItem(entity, loggedInUser.getUserId());

        	}
        	
        	setPurchase(form.getPurchaseId(), mv);
        	mv.setViewName("purchase/detail");
    		return mv;
        	
        }
        
    }

	@RequestMapping(value="/deleteItem", method=RequestMethod.POST)
    public ModelAndView deleteItem(@RequestParam("purchaseId") String purchaseId, 
    		@RequestParam("purchaseItemId")int purchaseItemId, @ModelAttribute MemberEntity loggedInUser) {
    	
    	ModelAndView mv = new ModelAndView();
    	purchaseItemService.deleteLogical(loggedInUser.getUserId(), purchaseItemId);
    	
    	setPurchase(purchaseId, mv);
    	mv.setViewName("purchase/detail");
		return mv;
    	        
    }

   @RequestMapping(value="/addItem/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView addItem(@PathVariable("purchaseId") String purchaseId, @ModelAttribute MemberEntity loggedInUser) {
    	
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
    public ModelAndView backToSearch(@ModelAttribute("purchaseCriteriaForm")PurchaseCriteriaForm form) {
    	
        ModelAndView mv = new ModelAndView();
        mv.addObject("purchaseCriteriaForm", form);
        List<PurchaseEntity> list = searchPurchase(form);        
        mv.addObject("list", list);
      
        mv.setViewName("purchase/search");

        return mv;
   }

   
}