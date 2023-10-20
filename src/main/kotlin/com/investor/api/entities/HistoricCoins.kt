package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.sql.Date

@Entity(name = "tb_historicCoins")
class HistoricCoins(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @OneToOne
    var investor: Investor?=null,
    @Column(name="tb_name")
    var name: MutableList<String>?=null,
    @Column(name="tb_dateView")
    var dateView: MutableList<Date>?=null,
    @Column(name="tb_value")
    var value: MutableList<Double?>?=null

) {

    fun listCoins()=name


}