package com.example.managetution;

public class TutorUsers {
        private String role, firstname,lastname,email,pass,institution,gender,batch,academicYear,contactInfo;

        public TutorUsers() {

        }

        public TutorUsers(String role, String firstname, String lastname, String email, String pass, String institution, String gender, String batch, String academicYear, String contactInfo) {
            this.role = role;
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.pass = pass;
            this.institution = institution;
            this.gender = gender;
            this.academicYear = academicYear;
            this.contactInfo = contactInfo;
        }

    public String getRole() {
        return role;
    }

    public void setRole(String firstname) {
        this.role = role;
    }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender =gender;
        }


        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getAcademicYear() {
            return academicYear;
        }

        public void setAcademicYear(String academicYear) {
            this.academicYear = academicYear;
        }

        public String getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }
}
