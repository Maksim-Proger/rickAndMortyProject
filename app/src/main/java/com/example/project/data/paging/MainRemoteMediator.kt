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
class MainRemoteMediator(
    private val api: Api,
    private val db: AppDatabase
) : RemoteMediator<Int, CharacterEntity>() {

    private var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> currentPage + 1
        }

        return try {
            val response = api.getCharacters(page)
            val characters = response.results.map { it.toEntity() }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.characterDao().clearAll()
                }
                db.characterDao().insertAll(characters)
            }

            currentPage = page

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
