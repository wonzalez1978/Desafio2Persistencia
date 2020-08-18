package cl.desafiolatam.desafiodos.orm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(val tarea: String){
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
}


