package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.MmChoice;
import com.diasonti.descriptiontinder.data.entity.MmChoice_;
import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.entity.UserAccount_;
import com.diasonti.descriptiontinder.data.enums.GenderPreference;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Optional;

public class CustomUserAccountRepositoryImpl implements CustomUserAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserAccount> findNextMatchmakingCandidate(Long sourceUserId, GenderPreference genderPref, int ageFrom, int ageTo, int proximity) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        final CriteriaQuery<UserAccount> uaQuery = cb.createQuery(UserAccount.class);
        final Root<UserAccount> userAccount = uaQuery.from(UserAccount.class);

        final Subquery<MmChoice> choiceSubquery = uaQuery.subquery(MmChoice.class);
        final Root<MmChoice> choice = choiceSubquery.from(MmChoice.class);
        choiceSubquery.select(choice).where(cb.and(
                cb.equal(choice.get(MmChoice_.source).get(UserAccount_.id), sourceUserId),
                cb.equal(choice.get(MmChoice_.target), userAccount)
        ));

        uaQuery.select(userAccount).where(cb.and(
                cb.notEqual(userAccount.get(UserAccount_.id), sourceUserId),
                cb.between(userAccount.get(UserAccount_.age), ageFrom, ageTo),
                userAccount.get(UserAccount_.gender).in(Arrays.asList(genderPref.getGenders())),
                cb.not(cb.exists(choiceSubquery)
        )));

        final TypedQuery<UserAccount> typedQuery = entityManager.createQuery(uaQuery);
        UserAccount result;
        try {
            result = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        return Optional.ofNullable(result);
    }

}
