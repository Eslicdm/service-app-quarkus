package com.eslirodrigues.member.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false, length = 255)
    public String name;

    @Column(nullable = false, unique = true, length = 255)
    public String email;

    @Column(name = "birth_date")
    public LocalDate birthDate;

    @Column(length = 255)
    public String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", columnDefinition = "service_type")
    public ServiceType serviceType;

    @Column(name = "manager_id", nullable = false, length = 255)
    public String managerId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;
}
