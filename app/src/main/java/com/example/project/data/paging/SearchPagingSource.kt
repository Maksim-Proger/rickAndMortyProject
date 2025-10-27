package com.example.project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.project.data.api.Api
import com.example.project.data.mappers.toDomain
import com.example.project.domain.models.DomainModelCharacter

class SearchPagingSource(
    private val api: Api,
    private val query: String
) : PagingSource<Int, DomainModelCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainModelCharacter> {
        val page = params.key ?: 1
        return try {
            val response = api.searchMethod(query, page)
            val items = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DomainModelCharacter>): Int? = null
}
