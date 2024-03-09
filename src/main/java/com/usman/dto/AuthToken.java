package com.usman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    @JsonProperty("token")
    private String jwtToken;

    private Long expireTime;

    private List<String> privileges;
}
