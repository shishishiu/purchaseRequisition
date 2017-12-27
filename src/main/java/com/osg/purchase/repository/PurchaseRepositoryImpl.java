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
        final Map<String, String> bindParameters = new HashMap<String, String>();

        if (criteria.getUserId()!= null && !criteria.getUserId().equals("")) {
            andConditions.add("p.userId like :userId");
            bindParameters.put("userId", "%" + criteria.getUserId() + "%");
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

        final StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT p FROM table_purchases p");
//        queryString.append(" JOIN p.userId u");
//        queryString.append(" JOIN master_codes comp ON comp.group_code = 2 AND comp.code = p.company AND comp.is_deleted = 0");
//        queryString.append(" JOIN code isDomestic ON isDomestic.groupCcode = 1 AND isDomestic.code = p.isDomestic AND isDomestic.is_deleted = 0");
//        queryString.append(" JOIN users u ON p.user_id = u.user_id ");
        
        Iterator<String> andConditionsIt = andConditions.iterator();
        if (andConditionsIt.hasNext()) {
            queryString.append(" WHERE ").append(andConditionsIt.next());
        }
        while (andConditionsIt.hasNext()) {
            queryString.append(" AND ").append(andConditionsIt.next());
        }

        queryString.append(" ORDER BY p.applicatedAt");

        final TypedQuery<PurchaseEntity> findQuery = entityManager.createQuery(
                queryString.toString(), PurchaseEntity.class);

        for (Map.Entry<String, String> bindParameter : bindParameters
                .entrySet()) {
            findQuery.setParameter(bindParameter.getKey(), bindParameter
                    .getValue());
        }

        return findQuery.getResultList();
	}

}
