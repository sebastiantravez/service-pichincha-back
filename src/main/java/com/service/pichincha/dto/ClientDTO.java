package com.service.pichincha.dto;

import com.service.pichincha.entities.enums.GenderPerson;
import com.service.pichincha.entities.enums.IdentificationPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long clientId;
    private String fullName;
    private GenderPerson genderPerson;
    private Integer age;
    private String dni;
    private IdentificationPattern identificationPattern;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
