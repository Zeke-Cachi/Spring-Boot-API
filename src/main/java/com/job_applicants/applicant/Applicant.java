package com.job_applicants.applicant;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Entity
@Table(name="applicant")
public class Applicant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  @NotBlank(message = "field 'name' cannot be empty")
  private String name;
  @Column
  @NotBlank(message = "field 'email' cannot be empty")
  @Email(message = "the field 'email' must be of email format")
  private String email;
  @Column
  @NotBlank(message = "field 'lastname' cannot be empty")
  private String last_name;
  @Column
  @NotNull(message = "the field 'years_of_experience' cannot be null")
  private Integer years_of_experience;
  @Column
  @NotNull(message = "the field 'has_been_hired' cannot be null")
  private Boolean has_been_hired;


  public Applicant() {}
  public Applicant(Long id, String name, String lastName, String applicantEmail, Integer yearsOfExperience, Boolean hasBeenHired) {
    this.id = id;
    this.name = name;
    this.last_name = lastName;
    this.email = applicantEmail;
    this.years_of_experience = yearsOfExperience;
    this.has_been_hired = hasBeenHired;
  }

  public Applicant(String name, String lastName, String applicantEmail, Integer yearsOfExperience, Boolean hasBeenHired) {
    this.name = name;
    this.last_name = lastName;
    this.email = applicantEmail;
    this.years_of_experience = yearsOfExperience;
    this.has_been_hired = hasBeenHired;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return last_name;
  }

  public String getEmail() {
    return email;
  }

  public Integer getYearsOfExperience() {
    return years_of_experience;
  }

  public Boolean getHasBeenHired() {
    return has_been_hired;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastName(String lastName) {
    this.last_name = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setYearsOfExperience(Integer yearsOfExperience) {
    this.years_of_experience = yearsOfExperience;
  }

  public void setHasBeenHired(Boolean hasBeenHired) {
    this.has_been_hired = hasBeenHired;
  }
}
