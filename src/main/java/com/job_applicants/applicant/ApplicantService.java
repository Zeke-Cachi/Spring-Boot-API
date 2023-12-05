package com.job_applicants.applicant;

import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//-----------------------------------------------------------------------------------------
@Service
public class ApplicantService {
  private final ApplicantRepository applicantRepository;
  private final CustomSearchesRepository customSearchesRepository;

  @Autowired
  public ApplicantService(
          ApplicantRepository applicantRepository,
          CustomSearchesRepository customSearchesRepository) {
    this.applicantRepository = applicantRepository;
    this.customSearchesRepository = customSearchesRepository;
  }


  //-----------------------------------------------------------------------------------------
  public List<Applicant> getAllApplicants() {
    return applicantRepository.findAll();
  }

  //-----------------------------------------------------------------------------------------
  public ResponseEntity<List<Applicant>> customSearch(String fieldName, String fieldValue) {
    Optional<List<Applicant>> optionalCustomSearchResults = customSearchesRepository.customSearch(fieldName, fieldValue);
    if (optionalCustomSearchResults.isPresent()) {
      List<Applicant> customSearchResults = optionalCustomSearchResults.get();
      return ResponseEntity.status(HttpStatus.OK).body(customSearchResults);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No applicants were found");
    }
  }

  //-----------------------------------------------------------------------------------------

  public ResponseEntity<Applicant> createOneApplicant(Applicant applicant) {
    Optional<Applicant> searchApplicantByEmail = applicantRepository.searchByEmail(applicant.getEmail());
    if (searchApplicantByEmail.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "applicant with id " + applicant.getId() + " already exists");
    }
    Applicant savedApplicant = applicantRepository.save(applicant);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedApplicant);
  }

  //-----------------------------------------------------------------------------------------
  public ResponseEntity<ArrayList<Applicant>> createManyApplicants(ArrayList<Applicant> applicantList) {
    ArrayList<Applicant> savedApplicants = new ArrayList<>();
    for (Applicant applicant : applicantList) {
      Optional<Applicant> searchApplicantByEmail = applicantRepository.searchByEmail(applicant.getEmail());
      if (searchApplicantByEmail.isPresent()) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "this applicant with email " + applicant.getEmail() + " already exists");
      }
      savedApplicants.add(applicant);
    }
    applicantRepository.saveAll(savedApplicants);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedApplicants);
  }

  //-----------------------------------------------------------------------------------------
  public ResponseEntity<Applicant> updateApplicant(Long id, Applicant updatedApplicant) {
    Optional<Applicant> existingOptionalApplicant = applicantRepository.searchById(id);
    if (existingOptionalApplicant.isPresent()) {
      Applicant existingApplicant = existingOptionalApplicant.get();
      BeanUtils.copyProperties(updatedApplicant, existingApplicant);
      Applicant savedUpdatedApplicant = applicantRepository.save(existingApplicant);
      return ResponseEntity.status(HttpStatus.OK).body(savedUpdatedApplicant);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "No applicants with id " + id + " were found");
    }
  }

  //-----------------------------------------------------------------------------------------
  public ResponseEntity<String> deleteApplicant(Long id) {
    Optional<Applicant> optionalApplicantToDelete = applicantRepository.searchById(id);
    if (optionalApplicantToDelete.isPresent()) {
      Applicant applicantToDelete = optionalApplicantToDelete.get();
      applicantRepository.delete(applicantToDelete);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("The applicant with id " + id + " has been successfully deleted");
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No applicants with id " + id + " where found");
    }
  }

  //-----------------------------------------------------------------------------------------
  public ResponseEntity<String> deleteManyApplicants (List<Long> idList) {
    try {
      applicantRepository.deleteAllById(idList);
      return ResponseEntity.status(HttpStatus.OK).body("The applicants with the provided ids were successfully deleted");
    }  catch (EmptyResultDataAccessException noIdError) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("All or some of the ids were not found. Please check the sent ids");
    } catch (DataAccessException dataError) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There were issues with the provided data. Please check the sent ids");
    }
  }

}
