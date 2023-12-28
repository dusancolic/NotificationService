package com.example.reservation.repository;

import com.example.reservation.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByEmail(String email, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.createdAt between ?1 and ?2")
    Page<Notification> findAllInRange(LocalDate beginDate, LocalDate endDate, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.type.name = ?1")
    Page<Notification> findAllByType(String name, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.type.name = ?1 and n.email = ?2")
    Page<Notification> findAllByTypeAndByEmail(String name, String email,Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.createdAt between ?1 and ?2 and n.email = ?3")
    Page<Notification> findAllInRangeAndByEmail(LocalDate beginDate, LocalDate endDate, String email, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.createdAt between ?1 and ?2 and n.type.name = ?3")
    Page<Notification> findAllByTypeAndInRange(LocalDate beginDate, LocalDate endDate, String type, Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.type.name = ?1 and n.email = ?2 and n.createdAt between ?3 and ?4")
    Page<Notification> findAllByTypeAndByEmailAndInRange(String name, String email, LocalDate beginDate, LocalDate endDate, Pageable pageable);

}
