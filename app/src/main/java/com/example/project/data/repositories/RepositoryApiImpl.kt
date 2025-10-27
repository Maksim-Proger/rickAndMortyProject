package com.example.project.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.project.data.api.Api
import com.example.project.domain.models.DomainModelCharacter
import com.example.project.domain.repositories.RepositoryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryApiImpl @Inject constructor(
    private val api: Api,
    private val db: AppDatabase
) : RepositoryApi {

    override fun getCharacters(): Flow<PagingData<DomainModelCharacter>> {
        val pagingSourceFactory = { db.characterDao().getAllCharacters() }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MainRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchMethod(name: String): Flow<PagingData<DomainModelCharacter>> {
        val pagingSourceFactory = { db.characterDao().searchCharacters(name) }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MainRemoteMediator(api, db),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getCharacterById(id: Int): DomainModelCharacter {
        return api.getCharacterById(id).toDomain()
    }
}
