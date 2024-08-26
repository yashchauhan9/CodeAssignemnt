package com.demo.user.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "audits")
@Data
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuditType auditType;

    private Long userId;

    @Lob
    private String before;

    @Lob
    private String after;

    private ZonedDateTime createdTime;


    public Audit(AuditType type, Long userId, String before, String after) {
        this.auditType = type;
        this.userId = userId;
        this.before = before;
        this.after = after;
        this.createdTime = ZonedDateTime.now();
    }
}
