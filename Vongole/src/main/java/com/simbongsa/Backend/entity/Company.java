package com.simbongsa.Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simbongsa.Backend.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "username", nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_image")
    private String companyImage;

    @Column(name = "company_num")
    private String companyNum;

    @Column(name = "certificate_image")
    private String certificateImage;

    @Column(name = "company_phone_num")
    private String companyPhoneNum;

    @Column(name = "email")
    private String email;

    @Column(name = "introduction")
    private String introduction;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void updateCompany() {

    }
}
