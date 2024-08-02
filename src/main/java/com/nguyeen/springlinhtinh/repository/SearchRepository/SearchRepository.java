package com.nguyeen.springlinhtinh.repository.SearchRepository;

import com.nguyeen.springlinhtinh.entity.Address;
import com.nguyeen.springlinhtinh.entity.User;
import com.nguyeen.springlinhtinh.repository.SearchRepository.criteria.SearchCriteria;
import com.nguyeen.springlinhtinh.repository.SearchRepository.criteria.UserSearchCriteriaQueryConsumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nguyeen.springlinhtinh.constant.AppConst.SEARCH_OPERATOR;
import static com.nguyeen.springlinhtinh.constant.AppConst.SORT_BY;

@Slf4j
@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager entityManager;
    //cung cấp các API cho việc tương tác với các Entity

    public Page<User> getUsersWithSortByColumnAndSearch(int page, int size, String sortBy, String search) {
        //get list user
        //StringBuilder sqlQuery = new StringBuilder("SELECT NEW com.nguyeen.springlinhtinh.dto.response.UserResponse
        // (u.id, u.firstName, u.lastName, u.email) FROM User u WHERE 1=1");
        StringBuilder sqlQuery = new StringBuilder("SELECT u FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and u.firstName LIKE LOWER(:firstName)");
            sqlQuery.append(" or u.lastName LIKE LOWER(:lastName)");
            sqlQuery.append(" or u.email LIKE LOWER(:email)");
        }

        if(StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile(SORT_BY);// Biên dịch quy tắc thành đối tượng Pattern
            Matcher matcher = pattern.matcher(sortBy);// "(\\w+?)(:)(asc|desc)" -> username:asc
            if (matcher.find()) {
                sqlQuery.append(String.format(" ORDER BY u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        Query selectedQuery = entityManager.createQuery(sqlQuery.toString());
        selectedQuery.setFirstResult(page * size);//page 2 * size 10 = 20
        selectedQuery.setMaxResults(size);
        if (StringUtils.hasLength(search)) {
            //firstName = %john% được gán vào :firstName trên sqlQuery
            selectedQuery.setParameter("firstName", String.format("%%%s%%", search));// = %search%
            selectedQuery.setParameter("lastName", String.format("%%%s%%", search));
            selectedQuery.setParameter("email", String.format("%%%s%%", search));
        }
        List<User> users = selectedQuery.getResultList();

        //query total record
        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(*) FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" and u.firstName LIKE LOWER(?1)");//truyền cách 2
            sqlCountQuery.append(" or u.lastName LIKE LOWER(?2)");
            sqlCountQuery.append(" or u.email LIKE LOWER(?3)");
        }

        Query selectedCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectedCountQuery.setParameter(1, String.format("%%%s%%", search));
            selectedCountQuery.setParameter(2, String.format("%%%s%%", search));
            selectedCountQuery.setParameter(3, String.format("%%%s%%", search));
        }
        //JPA không biết loại dữ liệu cụ thể mà truy vấn trả về -> object
        Long totalElement = (Long) selectedCountQuery.getSingleResult();

        //hỗ trợ phân trang trong truy vấn cơ sở dữ liệu
        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(users, pageable, totalElement);
    }

    public Page<User> advancedSearchUser(int page, int size, String sortBy, String address, String... search) {
        List<SearchCriteria> criteriaList = new ArrayList<>();
        if (search != null) {
            for (String s : search) {
                Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                    //dob > value
                }
            }
        }

        List<User> users = getUsers(page,size, criteriaList,sortBy,address);
        Long totalElement = getTotalElement(criteriaList,address);
        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(users, pageable, totalElement.intValue());
    }

    private List<User> getUsers(int page, int size, List<SearchCriteria> criteriaList, String sortBy, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();//create criteriaBuilder
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);//lenh select
        Root<User> root = criteriaQuery.from(User.class);//đại diện cho entity sẽ truy vấn tới / from User

        // xử lý điều kiện tìm kiếm
        //.conjunction() dựng predicate có thể kết hợp nhiều điều kiện lại với nhau (trong consumer)
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer consumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        // xử lý join address
        if(StringUtils.hasLength(address)){
            //Address là đối tượng liên kết với User thông qua thuộc tính "addresses
            Join<Address,User> addressUserJoin = root.join("addresses",JoinType.INNER);//JoinType.INNER
            Predicate addressPredicate = criteriaBuilder.like(addressUserJoin.get("city"),"%" + address + "%");
            criteriaQuery.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(consumer);
            predicate = consumer.getPredicate();
            criteriaQuery.where(predicate);
        }


        if(StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(columnName)));
                }else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(columnName)));
                }
            }
        }

        return entityManager.createQuery(criteriaQuery)
                            .setFirstResult(page)
                            .setMaxResults(size)
                            .getResultList();
    }

    private Long getTotalElement(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer consumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, predicate, root);

        if(StringUtils.hasLength(address)){
            Join<Address,User> addressUserJoin = root.join("addresses",JoinType.INNER);//JoinType.INNER
            Predicate addressPredicate = criteriaBuilder.like(addressUserJoin.get("city"),"%" + address + "%");
            criteriaQuery.select(criteriaBuilder.count(root));// = select count from root
            criteriaQuery.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(consumer);
            predicate = consumer.getPredicate();
            criteriaQuery.select(criteriaBuilder.count(root));//count
            criteriaQuery.where(predicate);
        }

        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }
}