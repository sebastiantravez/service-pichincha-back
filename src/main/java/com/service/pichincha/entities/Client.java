package com.service.pichincha.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Client extends Person{
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String password;
    @NotNull
    private Boolean status = Boolean.TRUE;
    @NotNull
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
    private List<Account> accounts;
}
