package com.job_applicants.applicant;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applicants")
public class ApplicantController {
  private final ApplicantService applicantService;

  @Autowired
  public ApplicantController(ApplicantService applicantService) {
    this.applicantService = applicantService;
  }
  //-----------------------------------------------------------------------------------------
  @GetMapping
  public ResponseEntity<List<Applicant>> getApplicants() {
    List<Applicant> allApplicants = this.applicantService.getAllApplicants();
    return new ResponseEntity<>(allApplicants, HttpStatus.OK);
  }

  //-----------------------------------------------------------------------------------------
  @GetMapping("/custom-search/{fieldName}/{fieldValue}")
  public ResponseEntity<List<Applicant>> customSearch(
          @PathVariable String fieldName,
          @PathVariable String fieldValue) {
    return this.applicantService.customSearch(fieldName, fieldValue);

  }

  //-----------------------------------------------------------------------------------------
  @PostMapping
  public ResponseEntity<Applicant> createApplicant(@Valid @RequestBody Applicant applicant) {
    return this.applicantService.createOneApplicant(applicant);
  }

  //-----------------------------------------------------------------------------------------
  @PostMapping("/createmany")
  public ResponseEntity<ArrayList<Applicant>> createManyApplicants(@Valid @RequestBody ArrayList<Applicant> applicantList) {
    return this.applicantService.createManyApplicants(applicantList);
  }

  //-----------------------------------------------------------------------------------------
  @PutMapping("/update/{id}")
  public ResponseEntity<Applicant> updateApplicant(
          @PathVariable Long id,
          @RequestBody Applicant updatedApplicant) {
    return this.applicantService.updateApplicant(id, updatedApplicant);
  }

  //-----------------------------------------------------------------------------------------
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteApplicant(@PathVariable Long id) {
    return this.applicantService.deleteApplicant(id);
  }

  //-----------------------------------------------------------------------------------------
  @DeleteMapping("/delete/many/{idList}")
  public ResponseEntity<String> deleteManyApplicants(@PathVariable List<Long> idList) {
    return this.applicantService.deleteManyApplicants(idList);
  }

}
