package com.neo.moneytracker.data.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.repository.CategoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.neo.moneytracker.R
import com.neo.moneytracker.data.localDb.entities.CategoryDataEntity
import com.neo.moneytracker.data.localDb.entities.CategoryEntity
import com.neo.moneytracker.data.mapper.toDomain
import com.neo.moneytracker.domain.model.Category
import com.neo.moneytracker.domain.model.SubCategory
import java.io.InputStreamReader

class CategoryRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CategoryRepository {

    override fun getCategoryData(): CategoryData {
        val inputStream = context.assets.open("Categories.json")
        val reader = InputStreamReader(inputStream)

        val dataDto = Gson().fromJson(reader, CategoryDataEntity::class.java)

        val domainData = dataDto.toDomain()

        val iconResolver: (String) -> Int = { iconName ->
            context.resources.getIdentifier(iconName, "drawable", context.packageName)
                .takeIf { it != 0 } ?: R.drawable.notfound
        }

        fun updateIconRes(categories: List<Category>): List<Category> {
            return categories.map { category ->
                category.copy(
                    subcategories = category.subcategories.map { subCategory ->
                        subCategory.copy(iconResId = iconResolver(subCategory.icon))
                    }
                )
            }
        }

        val incomeWithIcons = updateIconRes(domainData.income)
        val expensesWithIcons = updateIconRes(domainData.expenses)
        val transferWithIcons = updateIconRes(domainData.transfer)

        val settingsSubcategory = SubCategory(
            name = "Settings",
            icon = "settings",
            iconResId = iconResolver("settings")
        )

        fun addSettings(categories: List<Category>): List<Category> =
            categories.map { it.copy(subcategories = it.subcategories + settingsSubcategory) }

        return CategoryData(
            income = addSettings(incomeWithIcons),
            expenses = addSettings(expensesWithIcons),
            transfer = addSettings(transferWithIcons)
        )
    }
}
