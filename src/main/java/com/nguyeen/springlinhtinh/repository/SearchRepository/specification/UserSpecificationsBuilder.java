package com.nguyeen.springlinhtinh.repository.SearchRepository.specification;

import com.nguyeen.springlinhtinh.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.nguyeen.springlinhtinh.repository.SearchRepository.specification.SearchOperation.*;

public final  class UserSpecificationsBuilder {

    public final List<SpecSearchCriteria> params;

    public UserSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    // API
    public UserSpecificationsBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public UserSpecificationsBuilder with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation searchOperation = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (searchOperation != null) {
            if (searchOperation == EQUALITY) {
                final boolean startWithAsterisk = prefix != null && prefix.contains(ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    searchOperation = CONTAINS;
                } else if (startWithAsterisk) {
                    searchOperation = ENDS_WITH;
                } else if (endWithAsterisk) {
                    searchOperation = STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, searchOperation, value));
        }
        return this;
    }

    //build query
    public Specification<User> build() {
        if (params.isEmpty())
            return null;

        Specification<User> result = new UserSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new UserSpecification(params.get(i)))
                    : Specification.where(result).and(new UserSpecification(params.get(i)));
        }

        return result;
    }

    public UserSpecificationsBuilder with(UserSpecification spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public UserSpecificationsBuilder with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
