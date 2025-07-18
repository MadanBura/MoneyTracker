package com.neo.moneytracker.ui.viewmodel

import android.content.Context
import android.util.Log
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

    private val _subcategoryAdded = mutableStateOf(false)
    val subcategoryAdded: State<Boolean> get() = _subcategoryAdded

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
            "expenses" to expenses,
            "income" to income,
            "transfer" to transfer
        )
    }

    fun addCategory(type: String, name: String, iconRes: Int) {
        val updatedList = categoryMap.value[type]?.toMutableList() ?: mutableListOf()
        updatedList.add(name to iconRes)
        _categoryMap.value = categoryMap.value.toMutableMap().apply {
            put(type, updatedList)
        }
    }

    fun addSubcategory(type: String, subcategoryName: String, iconRes: Int) {

        Log.d("AddViewModel", "Before adding subcategory: ${categoryMap.value}")

        val updatedList = categoryMap.value[type]?.toMutableList() ?: mutableListOf()
        updatedList.add(0,subcategoryName to iconRes)
        _categoryMap.value = categoryMap.value.toMutableMap().apply {
            put(type, updatedList)
        }
        Log.d("AddViewModel", "After adding subcategory: ${categoryMap.value}")
        _subcategoryAdded.value = true
    }
}
