package com.pd.currencyconverter.database

import android.content.Context
import androidx.room.*
import com.pd.currencyconverter.dataclass.EmployeeEntity

@Database(entities = [EmployeeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomAppDb : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao?

    companion object {
        private var INSTANCE: RoomAppDb? = null

        fun getAppDatabase(context: Context): RoomAppDb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    "CardDB"
                ).allowMainThreadQueries().build()

            }
            return INSTANCE
        }

    }
}