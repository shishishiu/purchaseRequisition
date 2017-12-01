package com.osg.purchase.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.DepartmentEntity;
import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseEntity.CompanyValue;
import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;
import com.osg.purchase.form.AddUsuarioForm;
import com.osg.purchase.form.PurchaseCriteria;
import com.osg.purchase.form.PurchaseEditForm;
import com.osg.purchase.repository.PurchaseRepository;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	
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
    public ModelAndView search(@ModelAttribute("form") @Valid PurchaseCriteria form, BindingResult result) {
    	  	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("purchase/search");

//        List<PurchaseEntity> list = purchaseRepository.findAll(Specifications
//        		.where(applicantUserIdContains(keyApplicant))
//        		.and(applicatedAtContains(keyApplicatedAt))
//        		.and(deliveryDateContains(keyDeliveryDate)));
        
        PurchaseCriteria purchaseCriteriaForm = new PurchaseCriteria(); 
        BeanUtils.copyProperties(form, purchaseCriteriaForm);
        List<PurchaseEntity> list = purchaseRepository.findPurchases(purchaseCriteriaForm);
        
        mv.addObject("list", list);        
          
        return mv;
    }
    
	@RequestMapping(value="/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("purchaseId") String purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseEditForm form = new PurchaseEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("purchase", form);
 
    	mv.addObject("itemList", entity.getPurchaseItemList());

    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/edit");

		return mv;
    }
 
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addIndex(Model model, Principal principal, @ModelAttribute MemberEntity loggedInUser) {
    	
    	PurchaseEditForm form = new PurchaseEditForm();
		form.setUserId(loggedInUser.getUserId());
		form.setUsername(loggedInUser.getUsername());

    	model.addAttribute("form", form);
    	ModelAndView mv = new ModelAndView();
    	
    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	
        mv.setViewName("purchase/edit");
    	
        return mv;
   }

	private void createCompanyCombo(ModelAndView mv) {
    	mv.addObject("companyList", CompanyValue.values());
	}

	private void createIsDomesticCombo(ModelAndView mv) {
    	mv.addObject("isDomesticList", IsDomesticValue.values());
	}


   
}