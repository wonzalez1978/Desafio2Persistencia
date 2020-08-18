package cl.desafiolatam.desafiodos

import android.content.DialogInterface
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import cl.desafiolatam.desafiodos.orm.Task
import cl.desafiolatam.desafiodos.orm.TaskDatabase
import cl.desafiolatam.desafiodos.task.OnItemClickListener
import cl.desafiolatam.desafiodos.task.TaskListAdapter
import cl.desafiolatam.desafiodos.task.TaskUIDataHolder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_task.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnItemClickListener {

      lateinit var taskDatabase: TaskDatabase
    override fun onItemClick(taskItem: TaskUIDataHolder) {
        val dialogView = layoutInflater.inflate(R.layout.add_task, null)
        val taskText = dialogView.task_input
        taskText.setText(taskItem.text)
        val dialogBuilder = AlertDialog
            .Builder(this)
            .setTitle("Editar una Tarea")
            .setView(dialogView)
            .setNegativeButton("Cerrar") {
                    dialog: DialogInterface, _: Int -> dialog.dismiss()}
            .setPositiveButton("Editar") {
                    _: DialogInterface, _: Int ->
                //generar código para editar/actualizar la tarea
            }
        dialogBuilder.create().show()
    }

    private lateinit var list: RecyclerView
    private lateinit var adapter: TaskListAdapter
    // crear las variables para utilizar la base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setUpViews()
        //inicializar lo necesario para usar la base de datos
      //  taskDatabase = Room.databaseBuilder(this, TaskDatabase::class.java, "task_database").build()
        taskDatabase = TaskDatabase.getInstance(this)

    }

    override fun onResume() {
        super.onResume()
        AsyncTask.execute {
            val newItems = mutableListOf<TaskUIDataHolder>()
            runOnUiThread {
                adapter.updateData(newItems)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when {
            item.itemId == R.id.add -> addTask()
            item.itemId == R.id.remove_all -> removeAll()
        }
        return true
    }

    private fun setUpViews() {
        list = task_list
        list.layoutManager = LinearLayoutManager(this)
        adapter = TaskListAdapter( mutableListOf(), this, this)
        list.adapter = adapter
    }

    private fun updateEntity(taskItem: TaskUIDataHolder, newText: String) {
        //completar método para actualizar una tarea en la base de datos
    }

    private fun addTask() {
        val dialogView = layoutInflater.inflate(R.layout.add_task, null)
        val taskText = dialogView.task_input
        val dialogBuilder = AlertDialog
            .Builder(this)
            .setTitle("Agrega una Tarea")
            .setView(dialogView)
            .setNegativeButton("Cerrar") {
                    dialog: DialogInterface, _: Int -> dialog.dismiss()}
            .setPositiveButton("Agregar") {
                    dialog: DialogInterface, _: Int ->
                if (taskText.text?.isNotEmpty()!!) {
                    //Completar para agregar una tarea a la base de datos
                }
            }
        dialogBuilder.create().show()
    }

    private fun removeAll() {
        val dialog = AlertDialog
            .Builder(this)
            .setTitle("Borrar Todo")
            .setMessage("¿Desea Borrar todas las tareas?")
            .setNegativeButton("Cerrar") {
                    dialog: DialogInterface, _: Int -> dialog.dismiss()}
            .setPositiveButton("Aceptar") { dialog: DialogInterface, _: Int ->
                //Código para eliminar las tareas de la base de datos
            }
        dialog.show()
    }
    private fun createEntity(text:String) {
        //completar este método para retornar un Entity
        var tareaNueva = Task(text)
        CoroutineScope(Dispatchers.Main).launch {
            taskDatabase.getTaskDao().insertTask(tareaNueva) }
    }

    private fun createEntityListFromDatabase(): MutableList<TaskUIDataHolder> {
        var dataList = mutableListOf<TaskUIDataHolder>()
        //completar método para crear una lista de datos compatibles con el adaptador, mire lo que
        //retorna el método. Este método debe recibir un parámetro también.

        dataList = taskDatabase.getTaskDao().getAll() as MutableList<TaskUIDataHolder>
        return dataList
    }
}
