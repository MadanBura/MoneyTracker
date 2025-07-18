package com.neo.moneytracker.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.neo.moneytracker.domain.usecase.CategoryDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
            "expenses" to expenses,
            "income" to income,
            "transfer" to transfer
        )
    }

    fun removeSubCategory(category: String, item: Pair<String, Int>) {
        val updatedList = _categoryMap.value[category]?.toMutableList()?.apply {
            remove(item)
        }
        if (updatedList != null) {
            _categoryMap.value = _categoryMap.value.toMutableMap().apply {
                this[category] = updatedList
            }
        }
    }

    fun reorderSubCategory(category: String, from: Int, to: Int) {
        val currentList = _categoryMap.value[category]?.toMutableList() ?: return
        val item = currentList.removeAt(from)
        currentList.add(to, item)

        _categoryMap.value = _categoryMap.value.toMutableMap().apply {
            this[category] = currentList
        }
    }
}
