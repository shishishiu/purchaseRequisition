package com.osg.purchase.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.osg.purchase.entity.MemberEntity;

@ControllerAdvice
public class PrincipalControllerAdvice {

	@ModelAttribute
	public MemberEntity getLoggedInUser(Principal principal) {
		return
		Optional.ofNullable(principal)
		.filter(p -> p instanceof Authentication).map(p -> (Authentication) p)
		.map(a -> a.getPrincipal())
		.filter(p -> p instanceof MemberEntity).map(p -> (MemberEntity) p)
		.orElse(null);
	}

}