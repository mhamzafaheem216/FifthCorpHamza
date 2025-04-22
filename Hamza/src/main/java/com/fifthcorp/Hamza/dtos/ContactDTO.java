package com.fifthcorp.Hamza.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String name;
    private String type; // "Landlord" or "Tenant"
    private String contactInfo; // Phone or Email
}