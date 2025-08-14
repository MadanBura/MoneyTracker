package com.neo.moneytracker.data.localDb.entities

data class CategoryDataEntity(
    val expenses: List<CategoryEntity>,
    val income: List<CategoryEntity>,
    val transfer: List<CategoryEntity>
)