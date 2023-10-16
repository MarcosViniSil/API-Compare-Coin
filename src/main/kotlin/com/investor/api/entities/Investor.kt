package com.investor.api.entities

import jakarta.persistence.*

@Entity(name = "tb_investor")
class Investor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false, name = "tb_name") var name: String = "",
    @Column(unique = true, name = "tb_email") var email: String = "",
    @Column(name = "tb_password") var password: String = "",
    @Column(name = "tb_CoinMain") var coinMain: String = "",
    @Column(name = "tb_CoinSecond") var coinSecond: String = "",
    @OneToOne @JoinColumn(name = "tb_HistoricCoins")
    var historicCoins: HistoricCoins,
    @OneToOne @JoinColumn(name = "emails_historic") var historicEmails: HistoricEmails
) {
}