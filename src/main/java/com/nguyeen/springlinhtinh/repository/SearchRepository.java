package com.nguyeen.springlinhtinh.repository;

import com.nguyeen.springlinhtinh.dto.PageResponse;
import com.nguyeen.springlinhtinh.dto.response.User.UserResponse;
import com.nguyeen.springlinhtinh.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Page<User> getUsersWithSortByColumnAndSearch(int page, int size, String column, String search) {
        //query list user
        StringBuilder sqlQuery = new StringBuilder("SELECT u FROM User u WHERE 1=1");
        if(StringUtils.hasLength(search)){
            sqlQuery.append(" or u.firstName LIKE LOWER(:firstName)");
            sqlQuery.append(" or u.lastName LIKE LOWER(:lastName)");
            sqlQuery.append(" or u.phoneNumber LIKE (:phoneNumber)");
            sqlQuery.append(" or u.email LIKE LOWER(:email)");
        }

        Query selectedQuery = entityManager.createQuery(sqlQuery.toString(), User.class);
        selectedQuery.setFirstResult(page);
        selectedQuery.setMaxResults(size);
        if(StringUtils.hasLength(search)){
            selectedQuery.setParameter("firstName", String.format("%%%s%%", search));// = %search%
            selectedQuery.setParameter("lastName", String.format("%%%s%%", search));
            selectedQuery.setParameter("phoneNumber", String.format("%%%s%%", search));
            selectedQuery.setParameter("email", String.format("%%%s%%", search));
        }
        List<User> users = selectedQuery.getResultList();

        //query total record
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u WHERE 1=1");
        if(StringUtils.hasLength(search)){
            sqlCountQuery.append(" or u.firstName LIKE LOWER(?1)");
            sqlCountQuery.append(" or u.lastName LIKE LOWER(?2)");
            sqlCountQuery.append(" or u.phoneNumber LIKE (?3)");
            sqlCountQuery.append(" or u.email LIKE LOWER(?4)");
        }

        Query selectedCountQuery = entityManager.createQuery(sqlCountQuery.toString(), User.class);
        if(StringUtils.hasLength(search)){
            selectedCountQuery.setParameter(1, String.format("%%%s%%", search));
            selectedCountQuery.setParameter(2, String.format("%%%s%%", search));
            selectedCountQuery.setParameter(3, String.format("%%%s%%", search));
            selectedCountQuery.setParameter(4, String.format("%%%s%%", search));
        }
        Long totalElement = (Long) selectedCountQuery.getSingleResult();


        Pageable pageable = PageRequest.of(page, size);
        Page<?> pagee = new PageImpl<>(users, pageable, totalElement);
        return new PageImpl<>(users, pageable, users.size());
//        return PageResponse.builder()
//                .page(page)
//                .size(size)
//                .total(pagee.getTotalElements())
//                .items(users)
//                .build();
    }
}
