package com.example.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.usecases.GetCharacterByIdUseCase
import com.example.project.domain.usecases.GetCharactersUseCase
import com.example.project.domain.usecases.SearchMethodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val searchMethodUseCase: SearchMethodUseCase
): ViewModel()  {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _character = MutableStateFlow<DomainModelCharacter?>(null)
    val character = _character.asStateFlow()

    val characters = searchText
        .debounce(300)
        .flatMapLatest { searchWord ->
            if (searchWord.isBlank()) {
                getCharactersUseCase()
            } else {
                flowOf(PagingData.empty<DomainModelCharacter>()).onCompletion {
                    emitAll(searchMethodUseCase(searchWord))
                }
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
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