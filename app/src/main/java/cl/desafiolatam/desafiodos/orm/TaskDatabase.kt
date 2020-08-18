package cl.desafiolatam.desafiodos.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Task::class)], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDAO


    companion object{

        @Volatile
        private var INSTANCE : TaskDatabase? = null

        fun getInstance(context: Context):TaskDatabase{
            val instancia = INSTANCE
            if(instancia!= null){
                return instancia
            }
            synchronized(this){
                var nuevaInstancia = Room.databaseBuilder(context.applicationContext,
                TaskDatabase :: class.java, "tarea:data_base").build()
                INSTANCE = nuevaInstancia
                return nuevaInstancia
            }
        }
    }
}