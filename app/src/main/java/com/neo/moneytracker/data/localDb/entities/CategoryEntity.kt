package com.neo.moneytracker.data.localDb.entities

data class CategoryEntity(
    val name: String,
    val subcategories: List<SubCategoryEntity>
)