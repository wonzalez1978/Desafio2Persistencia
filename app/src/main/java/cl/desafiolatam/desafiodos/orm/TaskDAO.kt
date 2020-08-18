package cl.desafiolatam.desafiodos.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDAO {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Insert
    fun insertTask(tarea: Task)
}