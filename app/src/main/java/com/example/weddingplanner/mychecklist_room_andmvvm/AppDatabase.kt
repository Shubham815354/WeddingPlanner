package com.example.weddingplanner.mychecklist_room_andmvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserDatabase::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun user_dao(): UserDao

    companion object {
        @Volatile
        private var CHINSTANCE: AppDatabase? = null

        // 🔹 Migration from version 2 to 3: add isDefault column
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add isDefault column with default value 1
                database.execSQL(
                    "ALTER TABLE check_db ADD COLUMN isDefault INTEGER NOT NULL DEFAULT 1"
                )
            }
        }

        fun getDatabase_check(context: Context): AppDatabase {
            return CHINSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "checklist_database"
                )
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Insert default tasks
                            val defaultTasks = listOf(
                                "Venue Booking",
                                "Photography",
                                "Catering",
                                "Mehendi",
                                "Sangeet",
                                "Honeymoon Booking"
                            )
                            defaultTasks.forEach {
                                db.execSQL(
                                    "INSERT INTO check_db (taskName, checked, isDefault) VALUES ('$it', 0, 1)"
                                )
                            }
                        }
                    })

                    .build()


                CHINSTANCE = instance
                instance
            }
        }
    }
}
