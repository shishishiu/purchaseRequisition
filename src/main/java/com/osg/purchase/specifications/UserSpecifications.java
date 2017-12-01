package com.osg.purchase.specifications;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.osg.purchase.entity.PurchaseEntity;

public class UserSpecifications {

	/***
	 * 
	 * @param userId
	 * @return
	 */
	public static Specification<PurchaseEntity> applicantUserIdContains(String userId) {
        return StringUtils.isEmpty(userId) ? null : new Specification<PurchaseEntity>() {
            @Override
            public Predicate toPredicate(Root<PurchaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("userId"), "%" + userId + "%");
            }
        };
	}
    public static Specification<PurchaseEntity> applicatedAtContains(String keyApplicatedAt) {
        return keyApplicatedAt == null || keyApplicatedAt.equals("") ? null : new Specification<PurchaseEntity>() {
            @Override
            public Predicate toPredicate(Root<PurchaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
            	
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date convertedCurrentDate;
                java.sql.Date keySqlDateApplicatedAt = null;
        		try {
        			convertedCurrentDate = sdf.parse(keyApplicatedAt);
        			keySqlDateApplicatedAt = new java.sql.Date(convertedCurrentDate.getTime());
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}

                return cb.equal(root.get("applicatedAt"), keySqlDateApplicatedAt);
            }
        };
    }
    public static Specification<PurchaseEntity> deliveryDateContains(String keyDeliveryDate) {
        return keyDeliveryDate == null || keyDeliveryDate.equals("")  ? null : new Specification<PurchaseEntity>() {
            @Override
            public Predicate toPredicate(Root<PurchaseEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date convertedCurrentDate;
                java.sql.Date keySqlDateDeliveryDate = null;
        		try {
        			convertedCurrentDate = sdf.parse(keyDeliveryDate);
        			keySqlDateDeliveryDate = new java.sql.Date(convertedCurrentDate.getTime());
        		} catch (ParseException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}

            	return cb.equal(root.get("deliveryDate"), keySqlDateDeliveryDate);
            }
        };
    }

}
