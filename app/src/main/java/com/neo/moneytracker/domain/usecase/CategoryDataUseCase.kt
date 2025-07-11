package com.neo.moneytracker.domain.usecase

import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.repository.CategoryRepository

class CategoryDataUseCase(private val repository: CategoryRepository) {
    operator fun invoke(): CategoryData {
        return repository.getCategoryData()
    }
}