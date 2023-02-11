package com.service.pichincha.entities;

import com.service.pichincha.entities.enums.GenderPerson;
import com.service.pichincha.entities.enums.IdentificationPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    @Id
    @GeneratedValue
    private UUID personId;
    @NotNull
    private String fullName;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderPerson genderPerson;
    @Positive
    private Integer age;
    @NotNull
    private String dni;
    @NotNull
    @Enumerated(EnumType.STRING)
    private IdentificationPattern identificationPattern;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    private Date createDate;
    @OneToOne(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Client client;
}
