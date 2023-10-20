package com.investor.api.dtos

class EmailInvestorsDTO(var name:String,var coinMainName:String,var coinSecondName:String,
                         var priceCoinMain:Double?=null,var priceCoinSecond:Double?=null, var email:String){
}