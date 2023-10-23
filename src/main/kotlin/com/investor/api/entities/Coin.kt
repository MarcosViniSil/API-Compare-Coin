package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name="tb_Coin")
class Coin (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "tb_name")
    var name:String?="",

    @Column(name="tb_date")
    var dateView:Date?=null,

    @Column (name="tb_value")
    var value:Double?=null,

    @ManyToOne
    @JsonBackReference
    var historic:HistoricCoins?=null



) {

}