package com.zalisoft.zalisoft.dto;


import com.zalisoft.zalisoft.model.Faculty;
import com.zalisoft.zalisoft.model.Personal;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class DepartmentDto extends BaseDto{

    private Long id;
    private String name;
    private String code;

}
