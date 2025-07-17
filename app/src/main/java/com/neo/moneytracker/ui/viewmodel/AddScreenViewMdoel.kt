package com.neo.moneytracker.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.neo.moneytracker.domain.usecase.CategoryDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.neo.moneytracker.R
import dagger.hilt.android.qualifiers.ApplicationContext
@HiltViewModel
class AddViewModel @Inject constructor(
    private val categoryUseCase: CategoryDataUseCase
) : ViewModel() {

    private val _categoryMap = mutableStateOf<Map<String, List<Pair<String, Int>>>>(emptyMap())
    val categoryMap: State<Map<String, List<Pair<String, Int>>>> = _categoryMap

    init {
        loadCategories()
    }

    private fun loadCategories() {
        val data = categoryUseCase()

        val income = data.income.flatMap { it.subcategories }.map {
            it.name to it.iconResId
        }

        val expenses = data.expenses.flatMap { it.subcategories }.map {
            it.name to it.iconResId
        }

        val transfer = data.transfer.flatMap { it.subcategories }.map {
            it.name to it.iconResId
        }

        _categoryMap.value = mapOf(
            "Expenses" to expenses,
            "Income" to income,
            "Transfer" to transfer
        )
    }
}
