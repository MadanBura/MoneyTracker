package com.neo.moneytracker.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neo.moneytracker.data.localDb.entities.AddAccountEntity
import com.neo.moneytracker.data.localDb.entities.TransactionEntity
import com.neo.moneytracker.data.mapper.toDataEntity
import com.neo.moneytracker.data.mapper.toDomainModel
import com.neo.moneytracker.domain.model.AddAccount
import com.neo.moneytracker.domain.repository.AccountRepository
import com.neo.moneytracker.domain.repository.TransactionRepository
import com.neo.moneytracker.domain.usecase.AccountDataUseCase
import com.neo.moneytracker.domain.usecase.AddTransactionUseCase
import com.neo.moneytracker.domain.usecase.CalculateTotalUseCase
import com.neo.moneytracker.domain.usecase.CategoryDataUseCase
import com.neo.moneytracker.domain.usecase.DateWiseTotalUseCase
import com.neo.moneytracker.domain.usecase.GetTransactionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val useCase: AccountDataUseCase,
    private val categoryUseCase: CategoryDataUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val calculateTotalsUseCase: CalculateTotalUseCase,
    private val dateWiseTotalUseCase: DateWiseTotalUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val repository: TransactionRepository
): ViewModel() {

    init {
//        loadCategories()
        getAccounts()
    }

    private val _assets = MutableStateFlow(0L)
    val assets: StateFlow<Long> = _assets

    private val _liabilities = MutableStateFlow(0L)
    val liabilities: StateFlow<Long> = _liabilities

    val netWorth: StateFlow<Long> = MutableStateFlow(0L)

    private val _accounts = MutableStateFlow<List<AddAccountEntity>>(emptyList())
    val accounts: StateFlow<List<AddAccountEntity>> = _accounts

    private val _singleAccount = MutableStateFlow<AddAccountEntity?>(null)
    val singleAccount: StateFlow<AddAccountEntity?> = _singleAccount


    private val _categoryMap = mutableStateOf<Map<String, List<Pair<String, Int>>>>(emptyMap())
    val categoryMap: State<Map<String, List<Pair<String, Int>>>> = _categoryMap

    private val _subcategoryAdded = mutableStateOf(false)
    val subcategoryAdded: State<Boolean> get() = _subcategoryAdded


    private val _transactions = getTransactionsUseCase()
        .map { list -> list.map { it.toDataEntity() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val transactions: StateFlow<List<TransactionEntity>> = _transactions

    val incomeTotalAmount = _transactions.map {
        calculateTotalsUseCase(it.map { t -> t.toDomainModel() }).first
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val expensesTotalAmount = _transactions.map {
        calculateTotalsUseCase(it.map { t -> t.toDomainModel() }).second
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val dateWiseTotals = _transactions.map {
        dateWiseTotalUseCase(it.map { t -> t.toDomainModel() })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())


    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible


    fun addAccount(account: AddAccount){
        viewModelScope.launch{
            useCase.addAccount(account)
        }
    }

    fun reorderAccounts(fromIndex: Int, toIndex: Int) {
        val currentList = _accounts.value.toMutableList()
        val movedItem = currentList.removeAt(fromIndex)
        currentList.add(toIndex, movedItem)
        _accounts.value = currentList
    }


    private fun getAccounts() {
        viewModelScope.launch {
            useCase.getAccount().collect { list ->
                _accounts.value = list
                calculateAssetsAndLiabilities(list)
            }
        }
    }

    fun delAccount(account: AddAccountEntity){
        viewModelScope.launch{
            useCase.deleteAccount(id = account.id)
        }
    }


    fun updateAccount(account: AddAccount){
        viewModelScope.launch {
            useCase.update(account)
        }
    }


    private fun calculateAssetsAndLiabilities(list: List<AddAccountEntity>) {
        val (liabilityItems, assetItems) = list.partition { it.liabilities }

        _assets.value = assetItems.sumOf { it.amount }
        _liabilities.value = liabilityItems.sumOf { it.amount }
        (netWorth as MutableStateFlow).value = _assets.value - _liabilities.value
    }

    fun getAccountById(id: Int){
        viewModelScope.launch {
            _singleAccount.value = useCase.getAccountById(id)
        }
    }

    fun loadCategories() {
        if (_categoryMap == null) {

            Log.d("ACCOUNT_VIEWMODEL", "${_categoryMap.toString()}")
            return
        }
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


    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            addTransactionUseCase(transaction.toDomainModel())
        }
    }

    fun updateTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.updateTransaction(transaction.toDomainModel())
        }
    }

    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch {
            repository.deleteTransaction(transactionId)
        }
    }

    fun setDialogVisible(visible: Boolean) {
        _isDialogVisible.value = visible
    }
}