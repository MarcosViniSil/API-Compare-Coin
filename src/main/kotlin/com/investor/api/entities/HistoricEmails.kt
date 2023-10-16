package com.investor.api.entities

import jakarta.persistence.*

@Entity(name = "tb_historicEmails")
class HistoricEmails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @OneToOne(mappedBy = "historicEmails") var investor: Investor,
    @Column @ElementCollection var emails: List<Email> = mutableListOf()
) {
}