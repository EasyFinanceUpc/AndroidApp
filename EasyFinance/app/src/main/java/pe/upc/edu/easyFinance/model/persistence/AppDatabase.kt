package pe.upc.edu.easyFinance.model.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.upc.edu.easyFinance.model.persistence.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): UserDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context, AppDatabase::class.java,
                    "easyFinance")
                    .allowMainThreadQueries().build()
            }
            return INSTANCE as AppDatabase
        }
    }
}