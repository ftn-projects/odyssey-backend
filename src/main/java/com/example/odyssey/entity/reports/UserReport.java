package com.example.odyssey.entity.reports;

import com.example.odyssey.entity.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_reports")
public class UserReport extends Report {
    @ManyToOne
    private User reportedUser;

    public UserReport(Long id, String description, LocalDateTime submissionDate, User submitter, User reportedUser) {
        super(id, description, submissionDate, submitter);
        this.reportedUser = reportedUser;
    }
}
