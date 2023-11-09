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
@Table(name="usertimetracker")
public class UserTimeTracker {

    @Id
    @GeneratedValue
    @Column(name = "trackerid")
    private Long trackerId;

    @Column(name = "starttime")
    public Instant startTime;

    @Column(name = "endtime")
    public Instant endTime;


    @Column(name = "localdate")
    public LocalDate localDate;

    @Column(name = "workdescription")
    public String workDescription;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User userId;

    public UserTimeTracker(Instant startTime, Instant endTime, LocalDate localDate, String workDescription, User userId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.localDate = localDate;
        this.workDescription = workDescription;
        this.userId = userId;
    }
}
