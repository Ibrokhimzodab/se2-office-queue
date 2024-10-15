package com.group11.office_queue.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long id;
    private String waitListCode;
    private LocalDateTime dateTime;
    private LocalTime estimatedTime;
}
