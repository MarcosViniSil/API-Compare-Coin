package com.investor.api.entities

import jakarta.persistence.*
import java.sql.Date

@Embeddable
class Coin (
    var name:String,
    var dateView:Date,
    var value:Double
) {

}