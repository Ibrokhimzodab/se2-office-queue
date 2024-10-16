package com.group11.office_queue.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueDTO {

    private Long serviceId;
    private String serviceName;
    private List<TicketDTO> tickets;
}
