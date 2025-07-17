package com.neo.moneytracker.domain.repository

import com.neo.moneytracker.domain.model.CategoryData

interface CategoryRepository{
    fun getCategoryData() : CategoryData


}