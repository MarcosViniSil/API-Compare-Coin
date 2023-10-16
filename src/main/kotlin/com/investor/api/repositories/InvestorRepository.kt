package com.investor.api.repositories

import com.investor.api.entities.Investor
import org.springframework.data.jpa.repository.JpaRepository

interface InvestorRepository:JpaRepository<Investor,Long> {
}