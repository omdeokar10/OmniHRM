package com.example.performance_management.entity.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {

    String city;
    String region;
    String latitude;
    String longitude;

}
