package com.kaizen_team.kopiku.model

data class Order (
    val products: ArrayList<ItemOrder> = arrayListOf()
)
