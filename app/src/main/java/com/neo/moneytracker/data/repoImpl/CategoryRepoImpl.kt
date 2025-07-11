package com.neo.moneytracker.data.repoImpl

import android.content.Context
import com.google.gson.Gson
import com.neo.moneytracker.domain.model.CategoryData
import com.neo.moneytracker.domain.repository.CategoryRepository

class CategoryRepoImpl(private val context: Context) : CategoryRepository {
    override fun getCategoryData(): CategoryData {
        val json = context.assets.open("categories.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(json, CategoryData::class.java)
    }
}