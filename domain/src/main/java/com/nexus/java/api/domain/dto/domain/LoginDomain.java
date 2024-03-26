package com.nexus.java.api.domain.dto.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDomain {

    @JsonProperty(namespace = "username")
    String name = "teste";

    @JsonProperty(namespace = "token")
    String token = "brearer dfgfde";
}
