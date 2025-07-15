package com.neo.moneytracker.data.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.repository.CategoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.neo.moneytracker.R
import java.io.InputStreamReader

class CategoryRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CategoryRepository {
    override fun getCategoryData(): CategoryData {
        val inputStream = context.assets.open("Categories.json")
        val reader = InputStreamReader(inputStream)
        val data = Gson().fromJson(reader, CategoryData::class.java)

        return CategoryData(
            income = data.income.map { category ->
                category.copy(subcategories = category.subcategories.map {
                    it.copy(iconResId = getIconResId(it.icon))
                })
            },
            expenses = data.expenses.map { category ->
                category.copy(subcategories = category.subcategories.map {
                    it.copy(iconResId = getIconResId(it.icon))
                })
            },
            transfer = data.transfer.map { category ->
                category.copy(subcategories = category.subcategories.map {
                    it.copy(iconResId = getIconResId(it.icon))
                })
            }
        )
    }

    private fun getIconResId(iconName: String): Int {
        return context.resources.getIdentifier(iconName, "drawable", context.packageName)
            .takeIf { it != 0 } ?: R.drawable.notfound
    }
}