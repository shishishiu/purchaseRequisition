package com.osg.purchase.web;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.form.AutentificaForm;
import com.osg.purchase.service.MemberServiceImpl;

@Controller
public class AutentificaController {


	MemberServiceImpl memberService;
	
    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("menu")
    public String admin() {
        return "menu";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/mypage")
    public String mypage(Principal principal, Model model) {
		
    	Authentication authentication = (Authentication) principal;
		MemberEntity user = (MemberEntity) authentication.getPrincipal();
				
		model.addAttribute("user", user);
      return "myPage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("AutentificaForm", new AutentificaForm());
        return "login";
    }



}