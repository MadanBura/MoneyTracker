package com.neo.moneytracker.data.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.repository.CategoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.neo.moneytracker.R
import com.neo.moneytracker.domain.model.Category
import com.neo.moneytracker.domain.model.SubCategory
import java.io.InputStreamReader

class CategoryRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CategoryRepository {
    override fun getCategoryData(): CategoryData {
        val inputStream = context.assets.open("Categories.json")
        val reader = InputStreamReader(inputStream)
        val data = Gson().fromJson(reader, CategoryData::class.java)

        val incomeSubcategories = data.income.flatMap { category ->
            category.subcategories.map {
                it.copy(iconResId = getIconResId(it.icon))
            }
        }.toMutableList()

        val expenseSubcategories = data.expenses.flatMap { category ->
            category.subcategories.map {
                it.copy(iconResId = getIconResId(it.icon))
            }
        }.toMutableList()

        val transferSubcategories = data.transfer.flatMap { category ->
            category.subcategories.map {
                it.copy(iconResId = getIconResId(it.icon))
            }
        }.toMutableList()

        val settingsSubcategory = SubCategory(
            name = "Settings",
            icon = "settings",
            iconResId = getIconResId("settings")
        )

        // Add Settings once per category type
        incomeSubcategories.add(settingsSubcategory)
        expenseSubcategories.add(settingsSubcategory)
        transferSubcategories.add(settingsSubcategory)

        return CategoryData(
            income = listOf(
                Category(
                    name = "Income",
                    subcategories = incomeSubcategories
                )
            ),
            expenses = listOf(
               Category(
                    name = "Expenses",
                    subcategories = expenseSubcategories
                )
            ),
            transfer = listOf(
                    Category(
                    name = "Transfer",
                    subcategories = transferSubcategories
                )
            )
        )
    }

    private fun getIconResId(iconName: String): Int {
        return context.resources.getIdentifier(iconName, "drawable", context.packageName)
            .takeIf { it != 0 } ?: R.drawable.notfound
    }
}