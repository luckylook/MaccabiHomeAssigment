package com.example.maccabi.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maccabi.data.domain.CatalogState
import com.example.maccabi.data.domain.Category
import com.example.maccabi.data.repositories.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogState())
    var uiState = _uiState.asStateFlow()

    private val _categorySelected = MutableLiveData<Category>()
    var categorySelected: LiveData<Category> = _categorySelected

    init {
        fetchCatalog()
    }

    private fun fetchCatalog() {
        updateLoader(true)
        updateError(false)
        viewModelScope.launch {
            try {
                catalogRepository.categoriesFlow.collectLatest {
                    updateCategories(it)
                    updateLoader(false)
                }
            } catch (error: Exception) {
                error.printStackTrace()
                updateError(true, error.message)
                updateLoader(false)
            }

        }

    }

    private fun updateCategories(categories: List<Category>) {
        _uiState.update { currentState ->
            currentState.copy(
                categories = categories
            )
        }
    }

    private fun updateLoader(loadingState: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = loadingState
            )
        }
    }

    private fun updateError(errorState: Boolean, errorMessage: String? = null) {
        _uiState.update { currentState ->
            currentState.copy(
                isError = errorState,
                errorMessage = errorMessage
            )
        }
    }

    fun setSelectedCategory(category: Category) {
        _categorySelected.value = category
    }
}