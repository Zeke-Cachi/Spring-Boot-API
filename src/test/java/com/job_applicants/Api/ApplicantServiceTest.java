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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    given(testAppRepository.searchByEmail(anyString())).willReturn(Optional.of(testApplicant));

    assertThatThrownBy( () -> testApplicantService.createOneApplicant(testApplicant))
            .isInstanceOf(ResponseStatusException.class)
            .hasMessageContaining("applicant with email " + testApplicant.getEmail() + " already exists");
  }

  @Test
  void testIfApplicantIsUpdated() {

    Applicant testApplicant = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);

    when(testAppRepository.searchById(anyLong())).thenReturn(Optional.of(testApplicant));

    testApplicantService.updateApplicant(1L, testApplicant);

    verify(testAppRepository).searchById(1L);

    verify(testAppRepository).save(testApplicant);
  }

  @Test
  void deleteApplicant() {
    Applicant testApplicant = new Applicant(
            "testName",
            "testLastname",
            "testemail@gmail.com",
            5,
            true);

    when(testAppRepository.searchById(anyLong())).thenReturn(Optional.of(testApplicant));

    Long applicantIdToDelete = 1L;

    ResponseEntity<String> response = testApplicantService.deleteApplicant(applicantIdToDelete);

    verify(testAppRepository, times(1)).searchById(applicantIdToDelete);
    verify(testAppRepository, times(1)).delete(testApplicant);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("The applicant with id " + applicantIdToDelete + " has been successfully deleted");
  }
}