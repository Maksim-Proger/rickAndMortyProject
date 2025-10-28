package com.example.project.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.project.data.api.Api
import com.example.project.data.local.AppDatabase
import com.example.project.data.local.CharacterEntity
import com.example.project.data.mappers.toEntity

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val api: Api,
    private val db: AppDatabase,
    private val query: String
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) return MediatorResult.Success(true)
                (lastItem.id / state.config.pageSize) + 1
            }
        }

        return try {
            // Проверяем, есть ли локальные результаты
            val localCount = db.characterDao().countByName(query)
            if (localCount > 0 && loadType == LoadType.REFRESH) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            // Запрашиваем из API
            val response = api.searchMethod(query, page)
            val characters = response.results.map { it.toEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.characterDao().clearSearchResults(query)
                }
                db.characterDao().insertAll(characters)
            }

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
