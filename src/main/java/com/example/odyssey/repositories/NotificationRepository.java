package com.example.odyssey.repositories;

import com.example.odyssey.entity.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE (:receiverId IS NULL OR n.receiver.id = :receiverId) " +
            "AND (COALESCE(:types, NULL) IS NULL OR n.type IN :types)" +
            "AND (:read IS NULL OR :read = TRUE OR n.read = :read)")
    List<Notification> findAllByFilter(
            @Param("receiverId") Long receiverId,
            @Param("types") List<Notification.Type> types,
            @Param("read") Boolean read);
}
