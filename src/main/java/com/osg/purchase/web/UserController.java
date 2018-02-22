package com.osg.purchase.web;

import org.apache.commons.lang3.StringUtils;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.DepartmentEntity;
import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.form.UserEditForm;
import com.osg.purchase.repository.MemberRepository;
import com.osg.purchase.service.MemberServiceImpl;
import com.osg.purchase.repository.DepartmentRepository;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	MemberServiceImpl memberService;
	
    @RequestMapping(value= {"/search", "/"})
    public ModelAndView index() {
    	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/search");

        mv = mostrarList(mv,"");   	
    	
        return mv;
    }

    @RequestMapping(value="/search", method=RequestMethod.POST)
    public ModelAndView search(String keyword, HttpServletRequest request) {
    	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/search");
        
       	mv = mostrarList(mv, keyword);
          
       	request.getSession().setAttribute("keyword",keyword);
          
        return mv;
    }

	@RequestMapping(value="/{userId}", method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("userId") String userId) {
    	
    	ModelAndView mv = new ModelAndView();

    	MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(userId, 0);
    	mv.addObject("form", entity);
     	
    	mv.setViewName("user/detail");

		return mv;
    }

    @RequestMapping(value="/back", method=RequestMethod.GET)
    public ModelAndView backToSearch(HttpServletRequest request) {
    	
        ModelAndView mv = new ModelAndView();
        
        String keyword = (String)request.getSession().getAttribute("keyword");
        mv = mostrarList(mv, keyword);
        mv.addObject("keyword", keyword);

        mv.setViewName("user/search");
        return mv;
   }

	@RequestMapping(value="delete", method=RequestMethod.POST)
    public ModelAndView delete(@RequestParam("userId") String userId, HttpServletRequest request, Principal principal) {
    	
    	MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(userId, 0);
    	entity.setIsDeleted(1);
    	
    	Authentication authentication = (Authentication) principal;
		MemberEntity user = (MemberEntity) authentication.getPrincipal();
        
    	memberService.updateMember(entity, user.getUserId());
    	
    	return backToSearch(request);
   	
    }

	@RequestMapping(value="/edit", method=RequestMethod.POST)
    public ModelAndView edit(@RequestParam("userId") String userId) {
    	
    	ModelAndView mv = new ModelAndView();
    	UserEditForm form = new UserEditForm();
    	form.setIsNew(true);

    	if(!userId.equals("")) {

    		MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(userId, 0);
        	BeanUtils.copyProperties(entity, form);
        	form.setDepartmentId(entity.getDepartment().getId());
        	form.setId(entity.getId());
        	form.setIsNew(false);
    	
    	}
    	
    	mv.addObject("form", form);
    	createDepartmentCombo(mv);

    	mv.setViewName("user/edit");

		return mv;
    }

	@RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("form") @Valid UserEditForm form, BindingResult result, Principal principal) {
    	
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
        
        	createDepartmentCombo(mv);
        	mv.setViewName("user/edit");
        	return mv;

        } else{

        	Authentication authentication = (Authentication) principal;
    		MemberEntity user = (MemberEntity) authentication.getPrincipal();

            MemberEntity member = new MemberEntity();
            BeanUtils.copyProperties(form, member);

    		DepartmentEntity de = departmentRepository.findById(form.getDepartmentId());
    		member.setDepartment(de);
    		
    		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
    		String passwordCrypto;    		

        	if(form.getId() <= 0) {
        	
        		//insert
        		member.setCreatedUserId(user.getUserId());
        		member.setUpdatedUserId(user.getUserId());
        		passwordCrypto = bcrypt.encode("99999");
         		member.setPassword(passwordCrypto);
        		member = memberRepository.save(member);
        		
            	mv.setViewName("user/search");
    	        mv = mostrarList(mv, form.getUserId());
        		
        	} else {
        		
        		//update
        		MemberEntity mActual = memberRepository.findByUserIdAndIsDeleted(form.getUserId(), 0);
        		member.setPassword(mActual.getPassword());
        		memberService.updateMember(member, user.getUserId());
        		return showDetail(form.getUserId());
        	}
	        
        }
        
        return mv;
   }

    private ModelAndView mostrarList(ModelAndView mv, String keyword){
        List<MemberEntity> list = null;
        
        if (StringUtils.isNotEmpty(keyword)) {
        
        	list = memberRepository.findUsers(keyword);
        
        } else {
        	
        	list = memberRepository.findAll();
        }
        
        list = list.stream()
            	.filter(p -> p.getIsDeleted()==0).collect(Collectors.toList());
        mv.addObject("list", list);
        mv.addObject("keyword",keyword);
       
     	return mv;
    }
    
    private void createDepartmentCombo(ModelAndView mv) {
    	List<DepartmentEntity> list = departmentRepository.findAll(new Sort(Sort.Direction.ASC,"order")).stream()
            	.filter(p -> p.getIsDeleted()==0).collect(Collectors.toList());
    	mv.addObject("departmentList", list);
		
	}


}