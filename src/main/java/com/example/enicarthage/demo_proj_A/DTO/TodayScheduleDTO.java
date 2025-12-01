package com.example.enicarthage.demo_proj_A.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayScheduleDTO {
    private String time;
    private String subject;
    private String room;
    private String teacher;
}