package com.example.project.domain.repositories

import androidx.paging.PagingData
import com.example.project.domain.models.DomainModelCharacter
import kotlinx.coroutines.flow.Flow

interface RepositoryApi {
    fun getCharacters(): Flow<PagingData<DomainModelCharacter>>
    fun searchMethod(name: String): Flow<PagingData<DomainModelCharacter>>
    fun searchFilterMethod(name: String?, status: String?, gender: String?):
            Flow<PagingData<DomainModelCharacter>>

    suspend fun getCharacterById(id: Int): DomainModelCharacter
}