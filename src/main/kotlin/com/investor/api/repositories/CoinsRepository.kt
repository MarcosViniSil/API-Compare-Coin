package com.investor.api.repositories

import com.investor.api.entities.Coin
import org.springframework.data.jpa.repository.JpaRepository

interface CoinsRepository:JpaRepository<Coin,Long> {
}