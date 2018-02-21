package com.osg.purchase.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.repository.MemberRepository;

@Service
public class MemberServiceImpl implements UserDetailsService
{
	@Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (StringUtils.isEmpty(username))
        {
        	throw new UsernameNotFoundException("Username is empty");
        }

        MemberEntity memberEntity = memberRepository.findByUserId(username);
        if (memberEntity == null || memberEntity.getIsDeleted() == 1 )
        {
        	throw new UsernameNotFoundException("User not found: " + username);
        }

        return memberEntity;
    }
    
	public void updateMember(MemberEntity entity, String loggedInUserId) {
				
    	memberRepository.updateMember(entity.getPassword(), entity.getUsername(), 
    			entity.getDepartment().getId(), entity.getEmail(), entity.getIsDeleted(), loggedInUserId, entity.getUserId());

		
	}

    
}