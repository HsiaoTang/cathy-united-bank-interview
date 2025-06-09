package com.cub.interview.cathyunitedbankinterview.repository;

import com.cub.interview.cathyunitedbankinterview.bean.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

}
