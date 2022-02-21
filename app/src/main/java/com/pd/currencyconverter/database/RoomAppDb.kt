package com.pd.currencyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils

@Database(entities = [EmployeeEntity::class, AlarmEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class RoomAppDb : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                val tableName = ConstantUtils.TABLE_NAME_CARD_INFORMATION
                database.execSQL("ALTER TABLE `cardInformation` ADD COLUMN `age` TEXT")
            }
        }

        fun getAppDatabase(context: Context): RoomAppDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    ConstantUtils.DATABASE_NAME
                ).allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }
            return INSTANCE
        }

    }
}