package com.example.project.domain.usecases

import androidx.paging.PagingData
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.repositories.RepositoryApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFilterUseCase @Inject constructor(
    private val repositoryApi: RepositoryApi
) {
    operator fun invoke(
        name: String?,
        status: String?,
        gender: String?
    ): Flow<PagingData<DomainModelCharacter>> {
        return repositoryApi.searchFilterMethod(name, status, gender)
    }
}

