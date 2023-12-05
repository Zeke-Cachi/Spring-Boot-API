package com.job_applicants.applicant;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomSearchesRepository {
  @PersistenceContext
  EntityManager entityManager;

  public Optional<List<Applicant>> customSearch(String fieldName, String fieldValue) {
    EntityManager entManager = entityManager;
    CriteriaBuilder criteriaBuilder = entManager.getCriteriaBuilder();
    CriteriaQuery<Applicant> criteriaQuery = criteriaBuilder.createQuery(Applicant.class);
    Root<Applicant> root = criteriaQuery.from(Applicant.class);
    criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(fieldName), fieldValue));
    List<Applicant> resultList = entManager.createQuery(criteriaQuery).getResultList();
    return Optional.ofNullable(resultList);
  }
}
