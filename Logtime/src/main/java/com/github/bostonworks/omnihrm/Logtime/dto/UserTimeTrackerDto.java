package com.github.bostonworks.omnihrm.Logtime.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTimeTrackerDto {

    private String username;

    @Nullable private String description;

}
