package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity(name = "tb_historicCoins")
class HistoricCoins(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @OneToOne
    @JsonIgnore
    var investor: Investor?=null,

    @Column @ElementCollection var coins: MutableList<Coin>?=null
) {

    fun listCoins()=coins


}