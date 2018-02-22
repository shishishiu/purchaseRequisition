package com.osg.purchase.web;

import java.net.URI;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.form.AutentificaForm;
import com.osg.purchase.form.PasswordEditForm;
import com.osg.purchase.service.MemberServiceImpl;

@Controller
public class AutentificaController {

	@Autowired
	MemberServiceImpl memberService;
	@Autowired 
	MessageSource msg;
	
    @RequestMapping("/")
    public String index(UriComponentsBuilder builder) {

        URI location = builder.path("/purchase/search").build().toUri();
        return "redirect:" + location.toString();
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("AutentificaForm", new AutentificaForm());
        return "login";
    }

    @RequestMapping(value="/editPassword", method=RequestMethod.GET)
    public ModelAndView changePassword(Principal principal) {
		
    	ModelAndView mv = new ModelAndView();

    	Authentication authentication = (Authentication) principal;
		MemberEntity user = (MemberEntity) authentication.getPrincipal();

		mv.setViewName("editPassword");
		
		PasswordEditForm form = new PasswordEditForm();
		form.setUserId(user.getUserId());
    	mv.addObject("form", form);
		mv.addObject("isDone", false);
    	
		 return mv;
    }

    @RequestMapping(value="/editPassword", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("form") @Valid PasswordEditForm form, BindingResult result, Principal principal) {

        ModelAndView mv = new ModelAndView();
    	Authentication authentication = (Authentication) principal;
		MemberEntity user = (MemberEntity) authentication.getPrincipal();
		mv.addObject("isDone", false);

        if (result.hasErrors()) {
            
        	mv.setViewName("editPassword");
        	return mv;

        } else{
        	
    		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    		String passwordCrypto;    		
    		passwordCrypto = bcrypt.encode(form.getPasswordNew());
         	
    		user.setPassword(passwordCrypto);
    		
    		memberService.updateMember(user, user.getUserId());
    		
    		mv.addObject("isDone", true);
        	
        }
        
		 return mv;
    }

}