package com.fifthcorp.Hamza.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaseDTO {

    private Long leaseId;
    private ContactDTO tenant;   // Tenant info as ContactDTO
    private ContactDTO landlord; // Landlord info as ContactDTO
    private String unitName;
    private double rent;
    private int duration; // In months
    private String frequency;
}
