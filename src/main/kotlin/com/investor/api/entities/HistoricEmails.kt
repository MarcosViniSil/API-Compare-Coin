package com.investor.api.entities


import jakarta.persistence.*
import java.sql.Date

@Entity(name = "tb_historicEmails")
class HistoricEmails(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,

    @OneToOne(mappedBy = "historicEmails") var investor: Investor,

    @Column(name="tb_codeEmail")
    var codeEmail: MutableList<String>?=null,

    @Column(name="tb_messageEmail")
    var message: MutableList<String>?=null,

    @Column(name="tb_dateEmail")
    var dateEmail: MutableList<Date>?=null,
) {
}