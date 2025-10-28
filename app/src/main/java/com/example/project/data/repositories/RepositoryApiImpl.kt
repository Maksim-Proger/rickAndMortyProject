package com.example.project.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.project.data.api.Api
import com.example.project.data.local.AppDatabase
import com.example.project.data.mappers.toDomain
import com.example.project.data.mappers.toEntity
import com.example.project.data.paging.MainRemoteMediator
import com.example.project.data.paging.SearchFilterRemoteMediator
import com.example.project.data.paging.SearchRemoteMediator
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
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override fun searchMethod(name: String): Flow<PagingData<DomainModelCharacter>> {
        val pagingSourceFactory = { db.characterDao().searchCharacters(name) }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = SearchRemoteMediator(api, db, name),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it.map { entity -> entity.toDomain() } }
    }

    override fun searchFilterMethod(
        name: String?,
        status: String?,
        gender: String?
    ): Flow<PagingData<DomainModelCharacter>> {
        val pagingSourceFactory = { db.characterDao().filterCharacters(name, status, gender) }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = SearchFilterRemoteMediator(api, db, name, status, gender),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it.map { entity -> entity.toDomain() } }
    }

    override suspend fun getCharacterById(id: Int): DomainModelCharacter {
        return try {
            // 1️⃣ Попробовать получить из API
            api.getCharacterById(id).toDomain()
        } catch (e: Exception) {
            // 2️⃣ Если ошибка сети — достать из локальной базы
            val local = db.characterDao().getCharacterById(id)
            local?.toDomain() ?: throw e
        }
    }
}
