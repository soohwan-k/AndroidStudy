package org.tech.town.calculator.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.tech.town.calculator.model.History

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert
    fun insertHistory(history: History)

    @Query("DELETE FROM history")
    fun deleteAll()
}