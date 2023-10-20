package com.investor.api.entities

import jakarta.persistence.*
import org.hibernate.annotations.Cascade

@Entity(name = "tb_investor")
class Investor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false, name = "tb_name") var name: String = "",
    @Column(unique = true, name = "tb_email") var email: String = "",
    @Column(name = "tb_password") var password: String = "",
    @Column(name = "tb_CoinMain") var coinMain: String = "",
    @Column(name = "tb_CoinSecond") var coinSecond: String = "",
    @OneToOne(cascade = [CascadeType.ALL])

    var historicCoins: HistoricCoins?=null,
    @OneToOne @JoinColumn(name = "emails_historic") var historicEmails: HistoricEmails?=null
) {
}