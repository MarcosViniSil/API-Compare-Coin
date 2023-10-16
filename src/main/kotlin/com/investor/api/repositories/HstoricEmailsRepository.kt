package com.investor.api.repositories

import com.investor.api.entities.HistoricEmails
import org.springframework.data.jpa.repository.JpaRepository

interface HstoricEmailsRepository: JpaRepository<HistoricEmails, Long> {
}