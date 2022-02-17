package com.pd.currencyconverter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pd.currencyconverter.dataclass.AlarmEntity
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils

@Database(entities = [EmployeeEntity::class, AlarmEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class RoomAppDb : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null

        fun getAppDatabase(context: Context): RoomAppDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    ConstantUtils.DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }

    }
}