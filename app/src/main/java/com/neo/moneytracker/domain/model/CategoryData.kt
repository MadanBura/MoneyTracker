package com.neo.moneytracker.domain.model

data class CategoryData(
    val expenses: List<Category>,
    val income: List<Category>
)