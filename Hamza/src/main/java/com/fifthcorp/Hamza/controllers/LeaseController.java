package com.fifthcorp.Hamza.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fifthcorp.Hamza.commons.APIEndpoints;
import com.fifthcorp.Hamza.commons.Helper;
import com.fifthcorp.Hamza.commons.Response;
import com.fifthcorp.Hamza.dtos.ContactDTO;
import com.fifthcorp.Hamza.dtos.LeaseDTO;
import com.fifthcorp.Hamza.entities.Contact;
import com.fifthcorp.Hamza.entities.Lease;
import com.fifthcorp.Hamza.services.LeaseService;

import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(APIEndpoints.LEASE_ENDPOINT)
@RequiredArgsConstructor
public class LeaseController {

    private final LeaseService leaseService;


    @GetMapping("fetchAll")
    @ApiOperation(value = "Fetch All Leases")
    public ResponseEntity<Object> getAll(HttpServletRequest request) {
        if (!Helper.validateToken(request)) {
            return new ResponseEntity<>(new Response("Unauthorized: Invalid token"), HttpStatus.UNAUTHORIZED);
        }

        List<Lease> leases = leaseService.getAllLeases();
        if (leases.isEmpty()) {
            return new ResponseEntity<>(new Response("No leases found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(leases, HttpStatus.OK);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add New Lease")
    public ResponseEntity<Response> create(@RequestBody String obj, HttpServletRequest request) {
        if (!Helper.validateToken(request)) {
            return new ResponseEntity<>(new Response("Unauthorized: Invalid token"), HttpStatus.UNAUTHORIZED);
        }

        try {
            Lease createdLease = leaseService.createLease(obj);
            return new ResponseEntity<>(new Response("Lease created successfully", createdLease), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response("Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @GetMapping("/details/{unitId}")
    @ApiOperation(value = "Get Lease Details")
    public ResponseEntity<Object> getLeaseDetails(@PathVariable Long unitId, HttpServletRequest request) {
        if (!Helper.validateToken(request)) {
            return new ResponseEntity<>(new Response("Unauthorized: Invalid token"), HttpStatus.UNAUTHORIZED);
        }

        Lease lease = leaseService.getLeaseByUnit(unitId);
        if (lease == null) {
            return new ResponseEntity<>(new Response("No lease found for unit ID: " + unitId), HttpStatus.OK);
        }

        Contact tenant = lease.getTenant();
        Contact landlord = lease.getLandlord(); 

        ContactDTO tenantDTO = new ContactDTO(tenant.getId(), tenant.getName(), tenant.getType(), tenant.getContactInfo());
        ContactDTO landlordDTO = new ContactDTO(landlord.getId(), landlord.getName(), landlord.getType(), landlord.getContactInfo());

        LeaseDTO leaseDTO = new LeaseDTO(
            lease.getId(),
            tenantDTO,
            landlordDTO,
            lease.getUnit().getType(),
            lease.getRentAmount(),
            lease.getDuration(),
            lease.getPaymentFrequency()
        );

        return new ResponseEntity<>(leaseDTO, HttpStatus.OK);
    }
    
    
}
