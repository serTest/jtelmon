/*
 * http://localhost:8080/FirstSpringMVCProject/admissionForm.html
 * 
 * http://www.gontu.org/spring-mvc-tutorials-20-form-validations-04-pattern-past-max/
 * 
 * Spring MVC Tutorials 20 - Form Validations 04 (@Pattern, @Past, @Max and some more...)
 * https://www.youtube.com/watch?v=93RZPA_n3DU
 * 
 */
package com.gontuseries.studentadmissioncontroller;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Student {

	@Pattern(regexp="[^0-9]*")
	private String studentName;
	
	@Size(min=2,max=30)
	private String studentHobby;

	@Max(2222)
	private Long studentMobile;
	
	@Past
	private Date studentDOB;

	private ArrayList<String> studentSkills;

	private Address studentAddress;

	public Address getStudentAddress() {
		return studentAddress;
	}
	public void setStudentAddress(Address studentAddress) {
		this.studentAddress = studentAddress;
	}

	public Long getStudentMobile() {
		return studentMobile;
	}
	public void setStudentMobile(Long studentMobile) {
		this.studentMobile = studentMobile;
	}
	public Date getStudentDOB() {
		return studentDOB;
	}
	public void setStudentDOB(Date studentDOB) {
		this.studentDOB = studentDOB;
	}
	public ArrayList<String> getStudentSkills() {
		return studentSkills;
	}
	public void setStudentSkills(ArrayList<String> studentSkills) {
		this.studentSkills = studentSkills;
	}

	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentHobby() {
		return studentHobby;
	}
	public void setStudentHobby(String studentHobby) {
		this.studentHobby = studentHobby;
	}
}
