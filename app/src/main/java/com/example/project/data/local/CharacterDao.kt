package com.example.project.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%' ORDER BY id ASC")
    fun searchCharacters(name: String): PagingSource<Int, CharacterEntity>

    @Query("SELECT COUNT(*) FROM characters WHERE name LIKE '%' || :name || '%'")
    suspend fun countByName(name: String): Int

    @Query("DELETE FROM characters WHERE name LIKE '%' || :name || '%'")
    suspend fun clearSearchResults(name: String)

    @Query(
        """
        SELECT * FROM characters 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%') 
          AND (:status IS NULL OR LOWER(status) = LOWER(:status))
          AND (:gender IS NULL OR LOWER(gender) = LOWER(:gender))
        ORDER BY id ASC
    """
    )
    fun filterCharacters(
        name: String?,
        status: String?,
        gender: String?
    ): PagingSource<Int, CharacterEntity>

    @Query(
        """
        DELETE FROM characters 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%') 
          AND (:status IS NULL OR LOWER(status) = LOWER(:status))
          AND (:gender IS NULL OR LOWER(gender) = LOWER(:gender))
    """
    )
    suspend fun clearFiltered(name: String?, status: String?, gender: String?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}

