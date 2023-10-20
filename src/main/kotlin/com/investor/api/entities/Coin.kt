package com.investor.api.entities

import jakarta.persistence.*
import java.sql.Date

@Embeddable
class Coin (
    @Column var name:String,
    @Column var dateView:Date,
    @Column var value:Double?


) {

}