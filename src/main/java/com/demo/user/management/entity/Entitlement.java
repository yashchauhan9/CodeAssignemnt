package com.demo.user.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entitlement")
@Data
//TODO
public class Entitlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "details")
    private String entitlementDetails;

}
