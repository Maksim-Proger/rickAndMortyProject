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
class SearchFilterRemoteMediator(
    private val api: Api,
    private val db: AppDatabase,
    private val name: String?,
    private val status: String?,
    private val gender: String?
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) return MediatorResult.Success(true)
                (lastItem.id / state.config.pageSize) + 1
            }
        }

        return try {
            val response = api.searchFilterMethod(name, status, gender, page)
            val characters = response.results.map { it.toEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.characterDao().clearAll()
                }
                db.characterDao().insertAll(characters)
            }

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
