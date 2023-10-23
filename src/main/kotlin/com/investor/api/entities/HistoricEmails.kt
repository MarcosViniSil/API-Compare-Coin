package com.investor.api.entities


import jakarta.persistence.*
import java.sql.Date

@Entity(name = "tb_historicEmails")
class HistoricEmails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL]) var investor: Investor?=null,

    @OneToMany(mappedBy = "historice", cascade = [CascadeType.ALL],fetch = FetchType.EAGER)
    var emails:MutableList<Email>? =null


) {


}