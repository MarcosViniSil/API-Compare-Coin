package com.investor.api.API

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


data class ReturnJsonApi(
    @JsonProperty("code") val code: String?=null,
    @JsonProperty("codein") val codein: String?=null,
    @JsonProperty("name") val name: String?=null,
    @JsonProperty("high") val high: String?=null,
    @JsonProperty("low") val low: String?=null,
    @JsonProperty("varBid") val varBid: String?=null,
    @JsonProperty("pctChange") val pctChange: String?=null,
    @JsonProperty("bid") val bid: String?=null,
    @JsonProperty("ask") val ask: String?=null,
    @JsonProperty("timestamp") val timestamp: String?=null,
    @JsonProperty("create_date") val create_date: String?=null
)

