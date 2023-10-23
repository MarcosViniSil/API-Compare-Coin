package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.sql.Date

@Entity(name = "tb_historicCoins")
class HistoricCoins(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var investor: Investor?=null,

    @OneToMany(mappedBy = "historic", cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var coins:MutableList<Coin>? =null

) {



}