package com.example.project.domain.usecases

import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.repositories.RepositoryApi
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repositoryApi: RepositoryApi
) {
    suspend operator fun invoke(id: Int): DomainModelCharacter {
        return repositoryApi.getCharacterById(id)
    }
}