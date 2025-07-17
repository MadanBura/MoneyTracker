package com.neo.moneytracker.utils

import android.content.Context
import com.google.gson.Gson
import com.neo.moneytracker.domain.model.CategoryData
import java.io.InputStreamReader
import com.neo.moneytracker.R

//object ReaderHelper {
//    fun loadFullCategoryMap(context: Context): Map<String, List<String>> {
//        val inputStream = context.assets.open("Category.json")
//        val reader = InputStreamReader(inputStream)
//        val data = Gson().fromJson(reader, CategoryData::class.java)
//
//        val incomeMap = "Income" to data.income.flatMap { it.subcategories }
//        val expenseMap = "Expenses" to data.expenses.flatMap { it.subcategories }
//
//        val transferMap = "Transfer" to listOf("Bank Transfer", "UPI")
//
//        return mapOf(incomeMap, expenseMap, transferMap)
//    }
//}

object ReaderHelper {
    fun loadFullCategoryMap(context: Context): Map<String, List<Pair<String, Int>>> {
        val inputStream = context.assets.open("Categories.json")
        val reader = InputStreamReader(inputStream)
        val data = Gson().fromJson(reader, CategoryData::class.java)

        val income = data.income.flatMap { it.subcategories }.map {
            it.name to getIconResId(context, it.icon)
        }

        val expenses = data.expenses.flatMap { it.subcategories }.map {
            it.name to getIconResId(context, it.icon)
        }

        val transfer = data.transfer.flatMap { it.subcategories }.map {
            it.name to getIconResId(context, it.icon)
        }

        return mapOf(
            "Income" to income,
            "Expenses" to expenses,
            "Transfer" to transfer
        )
    }

    private fun getIconResId(context: Context, iconName: String): Int {
        return context.resources.getIdentifier(iconName, "drawable", context.packageName)
            .takeIf { it != 0 } ?: R.drawable.notfound
    }
}