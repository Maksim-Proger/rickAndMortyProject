package com.example.project.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.usecases.GetCharacterByIdUseCase
import com.example.project.domain.usecases.GetCharactersUseCase
import com.example.project.domain.usecases.SearchFilterUseCase
import com.example.project.domain.usecases.SearchMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val searchMethodUseCase: SearchMethodUseCase,
    private val searchFilterUseCase: SearchFilterUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _filterParams = MutableStateFlow(FilterParams())
    val filterParams = _filterParams.asStateFlow()

    private val _character = MutableStateFlow<DomainModelCharacter?>(null)
    val character = _character.asStateFlow()

    private val _searchFlow =
        combine(searchText.debounce(300), _filterParams) { searchWord, filter ->
            Pair(searchWord, filter)
        }.flatMapLatest { (searchWord, filter) ->
            when {
                filter.hasFilter() -> {
                    searchFilterUseCase(
                        name = filter.name.ifBlank { null },
                        status = filter.status.ifBlank { null },
                        gender = filter.gender.ifBlank { null }
                    )
                }

                searchWord.isNotBlank() -> {
                    searchMethodUseCase(searchWord)
                }

                else -> {
                    getCharactersUseCase()
                }
            }
        }


    val characters = _searchFlow.cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun applyFilter(name: String?, status: String?, gender: String?) {
        _filterParams.value = FilterParams(
            name = name.orEmpty(),
            status = status.orEmpty(),
            gender = gender.orEmpty()
        )
    }

    fun clearFilter() {
        _filterParams.value = FilterParams()
    }

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            try {
                _character.value = getCharacterByIdUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class FilterParams(
    val name: String = "",
    val status: String = "",
    val gender: String = ""
) {
    fun hasFilter(): Boolean = name.isNotBlank() || status.isNotBlank() || gender.isNotBlank()
}
