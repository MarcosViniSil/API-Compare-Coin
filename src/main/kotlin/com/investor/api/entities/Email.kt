package com.investor.api.entities

import jakarta.persistence.Embeddable
import java.sql.Date

@Embeddable
class Email(
    var code:Long,
    var message:String,
    var dateEmail:Date
) {

}