package az.javafx.model;

import az.javafx.exceptions.*;

public class Person {

    private Long id;
    private String name;
    private String surname;
    private String DOB;
    private String seriaNum;
    private boolean active;
    private String email;
    private String phone;
    private String gender;
    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() != 0) {
           this.name = name.trim();
       } else {
           throw new NameException("Adinizi daxil edin");
       }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname.length() != 0) {
             this.surname = surname.trim();
         } else {
             throw new SurnameException("Soyadinizi daxil edin");
         }
        }


    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        if (DOB == "null") {
           throw new DOBException("Dogum tarixinizi daxil edin");
       } else if (DOB.length() != 0) {
           this.DOB = DOB.trim();

       } else {
           throw new DOBException("Dogum tarixinizi daxil edin");

       }
    }

    public String getSeriaNum() {
        return seriaNum;
    }

    public void setSeriaNum(String seriaNum) {
        if (seriaNum.length() != 0) {
           if (seriaNum.length() == 7) {
               this.seriaNum = seriaNum.trim();
           } else {
               throw new SeriaNumExceptions("daxil ediyiniz melumat 7 simvoldan ibaret olalidir");

           }

       } else {
           throw new SeriaNumExceptions("Seria nomrenizi daxil edin");
       }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        if (email == null) {
            return ("melumat yoxdur");}
        return email;
    }

    public void setEmail(String email) {
        if (email.length() != 0) {
          this.email = email;
      } else {
          throw new EmailException("Emailinizi daxil edin");
      }
    }

    public String getPhone() {
        if (phone == null) {
           return ("melumat yoxdur");
        }
        return phone;

    }

    public void setPhone(String phone) {
         if (phone.length() != 0) {
        this.phone = phone;
    } else {
        throw new PhoneException("Nomrenizi daxil edin");
    }
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", DOB='" + DOB + '\'' +
                ", seriaNum='" + seriaNum + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
