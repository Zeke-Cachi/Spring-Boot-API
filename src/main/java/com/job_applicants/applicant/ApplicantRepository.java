package com.job_applicants.applicant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

  //-------------------
  Optional<Applicant> searchById(Long id);

  //-------------------
  @Query("SELECT a FROM Applicant a WHERE a.email = ?1")
  Optional<Applicant> searchByEmail(String email);

  //-------------------




}
