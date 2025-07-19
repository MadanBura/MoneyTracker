package com.neo.moneytracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UiStateViewModel : ViewModel() {
    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible

    fun setDialogVisible(visible: Boolean) {
        _isDialogVisible.value = visible
    }
}