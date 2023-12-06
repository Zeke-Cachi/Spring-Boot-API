package com.job_applicants.Api;

import com.job_applicants.applicant.Applicant;
import com.job_applicants.applicant.ApplicantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ApplicantRepositoryTest {

  @Autowired
  private ApplicantRepository testRepository;

  @AfterEach
  void tearDown() {
    testRepository.deleteAll();
  }

  //------------------------
  @Test
  void testSearchIfEmailExists() {
    String testEmail = "testemail@gmail.com";
    Applicant testApplicant = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);

    testRepository.save(testApplicant);

    Applicant searchedApplicant = testRepository.searchByEmail(testEmail).get();

    assertThat(searchedApplicant.getEmail()).isEqualTo(testEmail);
  }

  //------------------------
  @Test
  void testSearchIfEmailDoesNotExists() {
    String testEmail = "testemail@gmail.com";

    Optional<Applicant> searchedApplicant = testRepository.searchByEmail(testEmail);

    assertThat(searchedApplicant).isEmpty();
  }

  //------------------------
  @Test
  void testSearchIfIdExists() {
    Long testId = 2L;
    Applicant testApplicantById = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);

    testRepository.save(testApplicantById);

    Applicant testApplicant = testRepository.searchById(testApplicantById.getId()).get();

    assertThat(testApplicant.getId()).isEqualTo(testId);

  }
}