package com.osg.purchase.web;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.DepartmentEntity;
import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseEntity.CompanyValue;
import com.osg.purchase.entity.PurchaseEntity.IsDomesticValue;
import com.osg.purchase.form.AddUsuarioForm;
import com.osg.purchase.form.PurchaseCriteriaForm;
import com.osg.purchase.form.PurchaseHeaderEditForm;
import com.osg.purchase.repository.PurchaseRepository;

@Controller
@RequestMapping("/purchase")
@SessionAttributes(names="purchaseCriteriaForm")
public class PurchaseController {

	@Autowired
	PurchaseRepository purchaseRepository;
	
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
    public ModelAndView edit(@PathVariable("purchaseId") String purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	setPurchase(purchaseId, mv);

    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/detail");

		return mv;
    }

	private void setPurchase(String purchaseId, ModelAndView mv) {
    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("purchase", form);
 
    	mv.addObject("itemList", entity.getPurchaseItemList());
		
	}


	@RequestMapping(value="/editHeader/{purchaseId}", method=RequestMethod.GET)
    public ModelAndView editHeader(@PathVariable("purchaseId") String purchaseId) {
    	
    	ModelAndView mv = new ModelAndView();

    	PurchaseEntity entity = purchaseRepository.findByPurchaseId(purchaseId);
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
    	BeanUtils.copyProperties(entity, form);
    	form.setUsername(entity.getMember().getUsername());
    	mv.addObject("purchase", form);
 
    	mv.addObject("itemList", entity.getPurchaseItemList());

    	createIsDomesticCombo(mv);
    	createCompanyCombo(mv);

    	mv.setViewName("purchase/editHeader");

		return mv;
    }

	@RequestMapping(value="/saveHeader", method=RequestMethod.POST)
    public ModelAndView saveHeader(@ModelAttribute("purchaseHeaderEditForm") @Valid PurchaseHeaderEditForm form, BindingResult result) {
    	
    	ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            
        	mv.setViewName("purchase/editHeader");

        } else{

        	PurchaseEntity entity = new PurchaseEntity();
        	BeanUtils.copyProperties(form, entity);
        	
        	purchaseRepository.save(entity);
        	
        	setPurchase(form.getPurchaseId(), mv);
        	mv.setViewName("purchase/detail");
        	
        }
        

		return mv;
    }

   @RequestMapping(value="/add", method= { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView addIndex(Model model, Principal principal, @ModelAttribute MemberEntity loggedInUser) {
    	
    	PurchaseHeaderEditForm form = new PurchaseHeaderEditForm();
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