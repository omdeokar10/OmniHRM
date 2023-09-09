package com.deokarkaustubh.database.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserTimeTracker {

    @Id
    @GeneratedValue
    private Long trackerId;

    public Instant startTime;

    public Instant endTime;

    public LocalDate localDate;

    public String workDescription;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User userId;

    public UserTimeTracker(Instant startTime, Instant endTime, LocalDate localDate, String workDescription, User userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.localDate = localDate;
        this.workDescription = workDescription;
        this.userId = userId;
    }
}
