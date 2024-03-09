package com.usman.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;

@Data
@Embeddable
public class UserInformation {

    @Column(name = "first_name", length = 150)
    private String firstName;

    @Column(name = "last_name", length = 150)
    private String lastName;


    @Column(name = "phone", length = 20, nullable = false, unique = true)
    private String phone;
    @Column(name = "tc_number", length = 11)
    private String tcNumber;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "qualification")
    private String qualification;

    @Embedded
    private  Address address;

}
