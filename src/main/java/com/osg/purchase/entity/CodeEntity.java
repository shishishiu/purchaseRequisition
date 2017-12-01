package com.osg.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "master_codes")
@Table(name = "master_codes")
public class CodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private int groupCode;
    @Column
    private int code;
    @Column
    private String value;
    @Column
    private int order;
    @Column(nullable = false)
    private int isDeleted;

    public int getId()
   {
       return this.id;
   }
    public int getGroupCode()
   {
       return this.groupCode;
   }
    public int getCode()
   {
       return this.code;
   }
    public String getValue()
   {
       return this.value;
   }
    public int getOrder()
   {
       return this.order;
   }
    public int getIsDeleted()
   {
       return this.isDeleted;
   }

}
