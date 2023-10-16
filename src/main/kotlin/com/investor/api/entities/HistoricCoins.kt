package com.investor.api.entities

import jakarta.persistence.*
@Entity(name="tb_historicCoins")
class HistoricCoins(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

     @OneToOne(mappedBy = "historicCoins")
    var investor: Investor,
    @Column @ElementCollection var coins: List<Coin> = mutableListOf()
) {


}