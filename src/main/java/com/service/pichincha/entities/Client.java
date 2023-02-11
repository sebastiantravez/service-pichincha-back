package com.service.pichincha.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue
    private UUID clientId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;
    @NotNull
    private String password;
    @Builder.Default
    private Boolean status = Boolean.TRUE;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();
}
