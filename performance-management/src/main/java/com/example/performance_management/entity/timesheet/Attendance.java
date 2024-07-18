package com.example.performance_management.entity.timesheet;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attendance {

    @Id
    public Long id;

    //userid.

    //date.

    //ispresent = true.

}
