package com.example.reservation.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    @ManyToOne
    private NotificationType type;
    private List<String> parameters = new ArrayList<>();
    @CreatedDate
    private LocalDate createdAt;

    public Notification(String email, NotificationType type, List<String> parameters) {
        this.email = email;
        this.type = type;
        this.parameters = parameters;
    }
}
