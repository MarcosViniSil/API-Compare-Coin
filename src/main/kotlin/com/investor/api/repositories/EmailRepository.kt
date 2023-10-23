package com.investor.api.repositories

import com.investor.api.entities.Email
import org.springframework.data.jpa.repository.JpaRepository

interface EmailRepository:JpaRepository<Email,Long> {
}