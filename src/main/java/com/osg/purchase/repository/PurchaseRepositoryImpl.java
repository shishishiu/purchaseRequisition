package com.osg.purchase.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.form.PurchaseCriteriaForm;

public class PurchaseRepositoryImpl implements CustomizedPurchaseRepository {

    @PersistenceContext
    EntityManager entityManager;


	public List<PurchaseEntity> findPurchases(PurchaseCriteriaForm criteria) {

        final List<String> andConditions = new ArrayList<String>();
        final Map<String, Object> bindParameters = new HashMap<String, Object>();

        if (criteria.getPId()!= null && !criteria.getPId().equals("")) {
        	
        	int purchaseId = 0;
        	try {
            	purchaseId = Integer.parseInt(criteria.getPId());        		
        	}catch(NumberFormatException ex) {
        		
        	}
        	
            andConditions.add("p.purchaseId = :purchaseId");
            bindParameters.put("purchaseId", purchaseId);
        }
        if (criteria.getSolicitanteId()!= null && !criteria.getSolicitanteId().equals("")) {
            andConditions.add("p.userId like :userId");
            bindParameters.put("userId", "%" + criteria.getSolicitanteId() + "%");
        }
        if (criteria.getApplicatedAtFrom()!= null && !criteria.getApplicatedAtFrom().equals("")) {
            andConditions.add("p.applicatedAt >= :applicatedAtFrom");
            bindParameters.put("applicatedAtFrom", criteria.getApplicatedAtFrom());
        }
        if (criteria.getApplicatedAtTo()!= null && !criteria.getApplicatedAtTo().equals("")) {
            andConditions.add("p.applicatedAt <= :applicatedAtTo");
            bindParameters.put("applicatedAtTo", criteria.getApplicatedAtTo());
        }
        if (criteria.getDeliveryDateFrom()!= null && !criteria.getDeliveryDateFrom().equals("")) {
            andConditions.add("p.deliveryDate >= :deliveryDateFrom");
            bindParameters.put("deliveryDateFrom", criteria.getDeliveryDateFrom());
        }
        if (criteria.getDeliveryDateTo()!= null && !criteria.getDeliveryDateTo().equals("")) {
            andConditions.add("p.deliveryDate <= :deliveryDateTo");
            bindParameters.put("deliveryDateTo", criteria.getDeliveryDateTo());
        }
        if(criteria.getCheckIsDelivered() != null && criteria.getCheckIsDelivered().length == 1) {
            andConditions.add("p.isDelivered = :isDelivered");
            bindParameters.put("isDelivered", Integer.parseInt(criteria.getCheckIsDelivered()[0]));
        }

        final StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT p FROM table_purchases p WHERE p.isDeleted = 0");
        
        Iterator<String> andConditionsIt = andConditions.iterator();
//        if (andConditionsIt.hasNext()) {
//            queryString.append(" WHERE ").append(andConditionsIt.next());
//        }
        while (andConditionsIt.hasNext()) {
            queryString.append(" AND ").append(andConditionsIt.next());
        }

        queryString.append(" ORDER BY p.isDelivered, p.applicatedAt");

        final TypedQuery<PurchaseEntity> findQuery = entityManager.createQuery(
                queryString.toString(), PurchaseEntity.class);

        for (Map.Entry<String, Object> bindParameter : bindParameters
                .entrySet()) {
            findQuery.setParameter(bindParameter.getKey(), bindParameter
                    .getValue());
        }

        return findQuery.getResultList();
	}

}
