package com.service.pichincha.dto;

import com.service.pichincha.entities.enums.GenderPerson;
import com.service.pichincha.entities.enums.IdentificationPattern;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@Builder
public class ClientDTO {
    private UUID clientId;
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
