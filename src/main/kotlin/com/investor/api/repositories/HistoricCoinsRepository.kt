package com.investor.api.repositories

import com.investor.api.entities.HistoricCoins
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HistoricCoinsRepository: JpaRepository<HistoricCoins, Long> {


}