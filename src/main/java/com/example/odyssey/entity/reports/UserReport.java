package com.example.odyssey.entity.reports;

import com.example.odyssey.entity.users.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReport extends Report {
    private User reported;
}
