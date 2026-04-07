package org.ganapati.project.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.ganapati.project.ecommerce.enums.RoleType;

import java.io.Serializable;

@Entity
@Data
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private RoleType role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
//    @ToString.Exclude
    private User user;
    private boolean status;
}
