package org.tech.town.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import org.tech.town.calculator.dao.HistoryDao
import org.tech.town.calculator.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}