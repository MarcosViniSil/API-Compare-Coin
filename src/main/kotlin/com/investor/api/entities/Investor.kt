package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.hibernate.annotations.Cascade

@Entity(name = "tb_investor")
class Investor(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false, name = "tb_name") var name: String = "",
    @Column(unique = true, name = "tb_email") var email: String = "",
    @Column(name = "tb_password") var password: String = "",
    @Column(name = "tb_CoinMain") var coinMainName: String = "",
    @Column(name = "tb_CoinSecond") var coinSecondName: String = "",
    @Column(name = "tb_coinMainPrice") var coinMainPrice: Double?=null,
    @Column(name = "tb_coinSecondPrice") var coinSecondPrice: Double?=null,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonBackReference
    var historicCoins: HistoricCoins?=null,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonBackReference
     var historicEmails: HistoricEmails?=null
) {
}