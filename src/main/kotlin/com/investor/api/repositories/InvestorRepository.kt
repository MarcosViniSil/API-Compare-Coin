package com.investor.api.repositories

import com.investor.api.entities.Investor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InvestorRepository:JpaRepository<Investor,Long> {


    @Query(nativeQuery = true, value = "SELECT id FROM tb_investor WHERE tb_email = :name AND tb_password = :password")
    fun findIdInvestor(name:String,password:String):Long?
}