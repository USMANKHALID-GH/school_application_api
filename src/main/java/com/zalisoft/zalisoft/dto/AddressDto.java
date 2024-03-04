package com.zalisoft.zalisoft.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AddressDto extends BaseDto {


    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
