package com.job_applicants.Api;

import com.job_applicants.applicant.Applicant;
import com.job_applicants.applicant.ApplicantRepository;
import com.job_applicants.applicant.ApplicantService;
import com.job_applicants.applicant.CustomSearchesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicantServiceTest {

  @Mock
  private ApplicantService testApplicantService;
  @Mock
  private ApplicantRepository testAppRepository;
  @Mock
  private CustomSearchesRepository testCustomSearchesRepository;

  @BeforeEach
  void setUp() {
    testApplicantService = new ApplicantService(testAppRepository, testCustomSearchesRepository);
  }



  @Test
  void getAllApplicants() {
    testApplicantService.getAllApplicants();
    verify(testAppRepository).findAll();
  }

  @Test
  void testIfOneApplicantIsCreated() {
    Applicant testApplicant = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);
    testApplicantService.createOneApplicant(testApplicant);

    ArgumentCaptor<Applicant> applicantArgumentCaptor = ArgumentCaptor.forClass(Applicant.class);

    verify(testAppRepository).save(applicantArgumentCaptor.capture());

    Applicant capturedApplicant = applicantArgumentCaptor.getValue();

    assertThat(capturedApplicant).isEqualTo(testApplicant);
  }

  @Test
  void testErrorWhenEmailIsTaken() {
    Applicant testApplicant = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);
    testApplicantService.createOneApplicant(testApplicant);

    given(testAppRepository.searchByEmail(testApplicant.getEmail())).willReturn(Optional.of(testApplicant));
    assertThatThrownBy(() -> testApplicantService.createOneApplicant(testApplicant))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("applicant with email " + testApplicant.getEmail() + " already exists");
  }

  @Test
  @Disabled
  void updateApplicant() {
  }

  @Test
  @Disabled
  void deleteApplicant() {
  }
}