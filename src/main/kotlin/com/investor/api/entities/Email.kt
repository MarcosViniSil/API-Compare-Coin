package com.investor.api.entities

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name="tb_Email")
class Email(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "tb_code")
    var code:Long,

    @Column(name = "tb_message")
    var message:String,

    @Column(name = "tb_dateEmail")
    var dateEmail:Date,

    @ManyToOne
    @JsonBackReference
    var historice:HistoricEmails?=null
) {

}