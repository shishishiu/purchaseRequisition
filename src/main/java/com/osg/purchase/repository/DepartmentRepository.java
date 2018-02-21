package com.osg.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.osg.purchase.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

    public DepartmentEntity findById(int id);
    public DepartmentEntity findByDepartmentId(String departmentId);
    public DepartmentEntity findByDepartmentName(String departmentName);

}
