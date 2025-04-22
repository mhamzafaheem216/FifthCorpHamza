package com.fifthcorp.Hamza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fifthcorp.Hamza.entities.Lease;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    Lease findByUnitId(Long unitId);
}