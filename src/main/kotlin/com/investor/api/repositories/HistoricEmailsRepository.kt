package com.investor.api.repositories

import com.investor.api.entities.HistoricEmails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Date

interface HistoricEmailsRepository: JpaRepository<HistoricEmails, Long> {
    @Query(nativeQuery = true, value = "SELECT tb_code_email FROM tb_historic_emails WHERE id = :idInvestor")
    fun listAllCodes(idInvestor:Long?):MutableList<String>?


    @Query(nativeQuery = true, value = "SELECT tb_message_email FROM tb_historic_emails WHERE id = :idInvestor")
    fun listAllMessages(idInvestor:Long?):MutableList<String>?


    @Query(nativeQuery = true, value = "SELECT tb_date_email FROM tb_historic_emails WHERE id = :idInvestor")
    fun listAllDates(idInvestor:Long?):MutableList<Date>?


}