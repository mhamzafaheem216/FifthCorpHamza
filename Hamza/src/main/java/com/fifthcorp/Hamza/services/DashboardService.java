package com.fifthcorp.Hamza.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fifthcorp.Hamza.entities.Contact;
import com.fifthcorp.Hamza.entities.Lease;
import com.fifthcorp.Hamza.entities.Unit;
import com.fifthcorp.Hamza.repository.ContactRepository;
import com.fifthcorp.Hamza.repository.LeaseRepository;
import com.fifthcorp.Hamza.repository.UnitRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	@Autowired
    UnitRepository unitRepository;
	
	@Autowired
    ContactRepository contactRepository;
	
	@Autowired
	LeaseRepository leaseRepository;

    
    public Map<String, Object> getUnitStatusSummary() {
        long totalUnits = unitRepository.count();
        long vacantUnits = unitRepository.countByStatus("VACANT");
        long leasedUnits = unitRepository.countByStatus("LEASED");

        Map<String, Object> statusSummary = new HashMap<>();
        statusSummary.put("totalUnits", totalUnits);
        statusSummary.put("vacantUnits", vacantUnits);
        statusSummary.put("leasedUnits", leasedUnits);

        return statusSummary;
    }

    // Get list of landlords and how many units they own
    public List<Map<String, Object>> getLandlordUnitCount() {
        List<Map<String, Object>> landlordSummary = new ArrayList<>();

        List<Contact> landlords = contactRepository.findByType("LANDLORD");
        for (Contact landlord : landlords) {
            long unitCount = unitRepository.countByOwner(landlord);
            Map<String, Object> landlordData = new HashMap<>();
            landlordData.put("landlordName", landlord.getName());
            landlordData.put("unitCount", unitCount);
            landlordSummary.add(landlordData);
        }

        return landlordSummary;
    }

    public double getLeasedIncomeSummary() {
        List<Unit> leasedUnits = unitRepository.findByStatus("LEASED");
        double totalRentIncome = 0.0;

        for (Unit unit : leasedUnits) {
            totalRentIncome += unit.getValue();
        }

        return totalRentIncome;
    }

    public Unit getTestLeaseInfo() {
        return unitRepository.findFirstByStatus("LEASED");
    }

    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> dashboardSummary = new HashMap<>();
        dashboardSummary.put("unitStatusSummary", getUnitStatusSummary());
        dashboardSummary.put("landlordUnitSummary", getLandlordUnitCount());
        dashboardSummary.put("leasedIncomeSummary", getLeasedIncomeSummary());
        dashboardSummary.put("testLeaseInfo", getTestLeaseInfo());

        return dashboardSummary;
    }
    
    public Map<String, Object> getLinkedTestData() {
        Unit unit = unitRepository.findFirstByStatus("LEASED");
        if (unit == null) {
            throw new RuntimeException("No leased unit found for the test.");
        }

        Contact landlord = unit.getOwner();
        Lease lease = leaseRepository.findByUnitId(unit.getId());
        Contact tenant = lease != null ? lease.getTenant() : null;
        Map<String, Object> response = new HashMap<>();

        response.put("Unit Info ", unit);
        response.put("Lease Info ", lease);
        response.put("Tenant Info", tenant);
        response.put("Landlord Info", landlord);

        return response;
    }
}


