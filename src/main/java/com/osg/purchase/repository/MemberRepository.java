package com.osg.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.osg.purchase.entity.MemberEntity;


public interface MemberRepository extends JpaRepository<MemberEntity, Long>
{
    public MemberEntity findByUserIdAndIsDeleted(String userId, int isDeleted);
    
    @Query("select a from master_users a where (a.username like %:keyword% or a.userId like %:keyword%) and a.isDeleted=0 order by a.userId")
    List<MemberEntity> findUsers(@Param("keyword") String keyword);


}