package com.investor.api.repositories

import com.investor.api.entities.HistoricCoins
import org.springframework.data.jpa.repository.JpaRepository

interface HistoricCoinsRepository: JpaRepository<HistoricCoins, Long> {
}