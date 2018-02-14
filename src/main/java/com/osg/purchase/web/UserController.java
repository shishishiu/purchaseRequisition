package com.osg.purchase.web;

import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.osg.purchase.entity.DepartmentEntity;
import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.excel.ExcelBuilder;
import com.osg.purchase.excel.ExcelBuilderUsuario;
import com.osg.purchase.form.AddUsuarioForm;
import com.osg.purchase.form.AutentificaForm;
import com.osg.purchase.repository.MemberRepository;
import com.osg.purchase.repository.DepartmentRepository;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	
    @RequestMapping("")
    public String index() {
        return "user/search";
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String index2() {
        return "user/search";
    }

    @RequestMapping(value="/search", method=RequestMethod.POST)
    public ModelAndView search(String keyword, HttpServletRequest request) {
    	
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/search");
        
        if (StringUtils.isNotEmpty(keyword)) {
        	mv = mostrarList(mv, keyword);
          
        	request.getSession().setAttribute("keyword",keyword);
          
        }
        return mv;
    }
  
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addIndex(Model model) {
    	
    	model.addAttribute("form", new AddUsuarioForm());
    	ModelAndView mv = new ModelAndView();
    	
    	createDepartmentCombo(mv);
    	
        mv.setViewName("usuario/add");
    	
        return mv;
   }

	@RequestMapping(value="/add", params = "insert", method=RequestMethod.POST)
    public ModelAndView addMember(@ModelAttribute("form") @Valid AddUsuarioForm form, BindingResult result) {
    	
        ModelAndView mv = new ModelAndView();

        MemberEntity member = new MemberEntity();
        BeanUtils.copyProperties(form, member);

        if (result.hasErrors()) {
        
        	createDepartmentCombo(mv);
        	mv.setViewName("usuario/add");

        } else{
        
        	DepartmentEntity entity = departmentRepository.findByDepartmentName(form.getDepartmentname());
        	member.setDepartment(entity);
        	
	        memberRepository.save(member);

	        mv = mostrarList(mv, member.getUserId());

	        mv.setViewName("usuario/buscar");
	        
        }
        
        return mv;
   }

    @RequestMapping(value="/add", params = "update", method=RequestMethod.POST)
    public ModelAndView updateMember(@ModelAttribute("form") @Valid AddUsuarioForm form, BindingResult result) {
    	
        ModelAndView mv = new ModelAndView();

        MemberEntity member = new MemberEntity();
        BeanUtils.copyProperties(form, member);
       	member.setId(form.getHidId());

        if (result.hasErrors()) {
        
        	createDepartmentCombo(mv);
        	mv.setViewName("usuario/add");

        } else{
        
        	DepartmentEntity depentity = departmentRepository.findByDepartmentName(form.getDepartmentname());
        	member.setDepartment(depentity);
         	
	        memberRepository.save(member);

	        mv = mostrarList(mv, member.getUserId());

	        mv.setViewName("usuario/buscar");
	        
        }
        
        return mv;
   }


    @RequestMapping(value="/add", params = "back", method=RequestMethod.POST)
    public ModelAndView backFromAdd(HttpServletRequest request) {
    	
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession(false);
        String keyword = (String)session.getAttribute("keyword");
        
        mv = mostrarList(mv, keyword);
       
    	mv.setViewName("usuario/buscar");

        return mv;
   }

    @RequestMapping(value="/edit", params = "back", method=RequestMethod.POST)
    public ModelAndView backFromEdit(HttpServletRequest request) {
    	
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession(false);
        String keyword = (String)session.getAttribute("keyword");
        
        mv = mostrarList(mv, keyword);

    	mv.setViewName("usuario/buscar");

        return mv;
   }

	@RequestMapping(value="/{loginId}", method=RequestMethod.GET)
    public ModelAndView edit(@PathVariable("loginId") String loginId) {
    	
    	ModelAndView mv = new ModelAndView();

    	MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(loginId,1);
    	
    	
    	AddUsuarioForm form = new AddUsuarioForm();
        BeanUtils.copyProperties(entity, form);
        form.setDepartmentname(entity.getDepartment().getName());
        form.setHidId(entity.getId());
    	mv.addObject("form", form);
    	
    	createDepartmentCombo(mv);
    	mv.setViewName("usuario/add");

		return mv;
    }
 
	@RequestMapping(value="/dl", method=RequestMethod.POST)
    public ModelAndView dl(String keyword) {
    	
		
		ModelAndView mv = new ModelAndView(new ExcelBuilder());

        if (StringUtils.isNotEmpty(keyword)) {
        	mv = mostrarList(mv, keyword);         
        }
         
        mv.addObject("fileName", "POI" + ".xlsx");

        return mv;
    }
    
	@RequestMapping(value="/dl/{loginId}", method=RequestMethod.POST)
//    public ModelAndView dlByLoginId(@PathVariable("loginId") String loginId) {
    public ModelAndView dlByLoginId(@PathVariable("loginId") String loginId) {
    	
    	ModelAndView mv = new ModelAndView(new ExcelBuilderUsuario(),"message", "test");

    	MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(loginId,1);
    	    	
    	AddUsuarioForm form = new AddUsuarioForm();
        BeanUtils.copyProperties(entity, form);
        form.setDepartmentname(entity.getDepartment().getName());
        form.setHidId(entity.getId());
    	mv.addObject("form", form);
    	
    	createDepartmentCombo(mv);

//        mv.addObject("fileName", "Usuario.txt");

        return new ModelAndView(new ExcelBuilderUsuario(),"message", "test");
    }
    
	

	@RequestMapping(value="delete/{loginId}", method=RequestMethod.POST)
    public ModelAndView delete(@PathVariable("loginId") String loginId, String keyword) {
    	
    	ModelAndView mv = new ModelAndView();

    	MemberEntity entity = memberRepository.findByUserIdAndIsDeleted(loginId,1);
    	entity.setIsDeleted(1);
    	memberRepository.save(entity);
    	
        mv = mostrarList(mv, keyword);
    	
    	mv.setViewName("usuario/buscar");
   	
		return mv;
    }
    
    private ModelAndView mostrarList(ModelAndView mv, String keyword){
        if (StringUtils.isNotEmpty(keyword)) {
            List<MemberEntity> list = memberRepository.findUsers(keyword);
            mv.addObject("list", list);
            mv.addObject("keyword",keyword);
        }
       
     	return mv;
    }
    
    private void createDepartmentCombo(ModelAndView mv) {
    	List<DepartmentEntity> list = departmentRepository.findByIsDeletedOrderByDepartmentName(0);
    	mv.addObject("departmentList", list);
		
	}


}