package curs.narcis.myrecipeapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    init {
        Log.e("MORROCOTUDO", "HoLA")
        fetchCategories()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                Log.e("MORROCOTUDO", "HoLA2")
                val response = recipeService.getCategories()
                Log.e("MORROCOTUDO", response.toString())
                _categoriesState.value = _categoriesState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )
            } catch (e: Exception){
                Log.e("MORROCOTUDO", e.message.toString())
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching categories ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )

}