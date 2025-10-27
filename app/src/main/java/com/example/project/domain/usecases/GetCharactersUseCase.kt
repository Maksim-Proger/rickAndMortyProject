package com.example.project.domain.usecases

import androidx.paging.PagingData
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.repositories.RepositoryApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repositoryApi: RepositoryApi
) {
    operator fun invoke(): Flow<PagingData<DomainModelCharacter>> {
        return repositoryApi.getCharacters()
    }
}