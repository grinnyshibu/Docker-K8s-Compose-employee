package com.qburst.employeeproxy.model;

import java.util.List;

public class EmployeeDTO {

    private Long empID;
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private Long contact;
    private Long emergencyContact;
    private List<LeavesDTO> leaves;
    private AddressDTO permanentAddressDTO;
    private AddressDTO currentAddressDTO;
    private List<SalaryDTO> salaryDTO;

    public Long getEmpID() {
        return empID;
    }

    public void setEmpID(Long empID) {
        this.empID = empID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Long getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(Long emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public List<LeavesDTO> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<LeavesDTO> leaves) {
        this.leaves = leaves;
    }

    public List<SalaryDTO> getSalary() {
        return salaryDTO;
    }

    public void setSalary(List<SalaryDTO> salaryDTO) {
        this.salaryDTO = salaryDTO;
    }

    public AddressDTO getPermanentAddress() {
        return permanentAddressDTO;
    }

    public void setPermanentAddress(AddressDTO permanentAddressDTO) {
        this.permanentAddressDTO = permanentAddressDTO;
    }

    public AddressDTO getCurrentAddress() {
        return currentAddressDTO;
    }

    public void setCurrentAddress(AddressDTO currentAddressDTO) {
        this.currentAddressDTO = currentAddressDTO;
    }
}
