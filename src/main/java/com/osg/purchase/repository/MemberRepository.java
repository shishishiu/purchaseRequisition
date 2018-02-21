package com.osg.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.osg.purchase.entity.MemberEntity;


public interface MemberRepository extends JpaRepository<MemberEntity, Long>
{
    public MemberEntity findByUserId(String userId);
    
    @Query("select a from master_users a where (a.username like %:keyword% or a.userId like %:keyword%) and a.isDeleted=0 order by a.userId")
    List<MemberEntity> findUsers(@Param("keyword") String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE master_users SET password=?1, username=?2, department_id=?3, email=?4, is_deleted=?5, updated_user_id=?6 WHERE user_id=?7")
    void updateMember(String password, String username, int departmentId, 
    		String email, int isDeleted, String updateUserId, String userId);


}