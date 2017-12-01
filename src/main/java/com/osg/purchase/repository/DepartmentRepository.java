package com.osg.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osg.purchase.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

    public DepartmentEntity findById(int id);
    public DepartmentEntity findByDepartmentName(String departmentName);
    public List<DepartmentEntity> findByIsDeletedOrderByDepartmentName(int isDeleted);

}
