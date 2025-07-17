package com.neo.moneytracker.domain.model

data class Category(
    val name: String,
    val subcategories: List<SubCategory>
)