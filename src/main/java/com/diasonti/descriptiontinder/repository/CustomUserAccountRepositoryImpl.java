package com.diasonti.descriptiontinder.repository;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.enums.GenderPreference;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

public class CustomUserAccountRepositoryImpl implements CustomUserAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserAccount> findNextMatchmakingCandidate(Long sourceUserId, GenderPreference genderPref, int ageFrom, int ageTo, int proximity) {
        // TODO: 24/02/2019 Rewrite using JPA CriteriaBuilder
        String psqlQuery = "WITH prev_targets AS (\n" +
                "  SELECT target_user_id FROM matchmaking_choice WHERE source_user_id = :source_id\n" +
                ")\n" +
                "SELECT * \n" +
                "FROM user_account ua \n" +
                "WHERE ua.id != :source_id \n" +
                "  AND ua.id NOT IN (SELECT target_user_id FROM prev_targets)\n" +
                "  AND ua.age BETWEEN :ageFrom AND :ageTo\n" +
                "  AND ua.gender IN (" + genderPref.getGendersAsString() + ")\n" +
                "ORDER BY ua.id ASC\n" +
                "LIMIT 1;";
        final Query query = entityManager.createNativeQuery(psqlQuery, UserAccount.class);
        query.setParameter("source_id", sourceUserId);
        query.setParameter("ageFrom", ageFrom);
        query.setParameter("ageTo", ageTo);
//        query.setParameter("proximity", proximity);
        UserAccount result;
        try {
            result = (UserAccount) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        return Optional.ofNullable(result);
    }

}
