package com.job_applicants.Api;

import com.job_applicants.applicant.Applicant;
import com.job_applicants.applicant.ApplicantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicantRepositoryTest {

  @Autowired
  private ApplicantRepository testRepository;

  @Test
  void testSearchByEmail() {
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
}