package com.service.pichincha.dto;

import com.service.pichincha.entities.enums.GenderPerson;
import com.service.pichincha.entities.enums.IdentificationPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long clientId;
    @NotNull
    private String fullName;
    @NotNull
    private GenderPerson genderPerson;
    @Positive
    private Integer age;
    @NotNull
    private String dni;
    @NotNull
    private IdentificationPattern identificationPattern;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @NotNull
    private String password;
    @NotNull
    private Boolean status;
}
