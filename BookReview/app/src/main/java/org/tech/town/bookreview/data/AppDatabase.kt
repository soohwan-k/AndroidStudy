package org.tech.town.bookreview.data

import androidx.room.Database
import androidx.room.RoomDatabase
import org.tech.town.bookreview.data.dao.HistoryDao
import org.tech.town.bookreview.data.dao.ReviewDao
import org.tech.town.bookreview.data.model.History
import org.tech.town.bookreview.data.model.Review

@Database(entities = [History::class, Review::class],version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao
}