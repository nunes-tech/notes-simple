package com.example.notessimple.presetation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessimple.R
import com.example.notessimple.databinding.ActivityListTasksBinding
import com.example.notessimple.presetation.ui.adapter.ListenerOnRecyclerView
import com.example.notessimple.presetation.ui.adapter.TasksAdapter
import com.example.notessimple.presetation.viewmodel.TasksViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListTasksActivity : AppCompatActivity(), ListenerOnRecyclerView {

    private val tasksViewModel : TasksViewModel by viewModels()

    private var showGrid = false

    private val binding by lazy {
        ActivityListTasksBinding.inflate( layoutInflater )
    }
    private val adapterTasks by lazy {
        TasksAdapter(this)
    }

    override fun onStart() {
        super.onStart()
        listTasks()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )

        initObserve()
        initToolbar()
        initMenu()
        setRecyclerViewTarefas()
        binding.fabAddTask.setOnClickListener { createTask() }
    }

    override fun itemClick(id: Int, tarefa: String) {
        updateTask(id, tarefa)
    }

    override fun itemLongClick(id: Int) {
        deleteTask(id)
    }

    private fun initObserve() {
        tasksViewModel.listTasks.observe(this) { listTasks ->
            adapterTasks.setListTasks( listTasks )
            if(listTasks.isEmpty()) {
                binding.imageNoNotes.visibility = View.VISIBLE
            } else {
                binding.imageNoNotes.visibility = View.GONE
            }
        }
    }

    private fun setRecyclerViewTarefas() {
        if (showGrid) {
            binding.rvTasks.adapter = adapterTasks
            binding.rvTasks.layoutManager = GridLayoutManager(this, 2)
            showGrid = !showGrid
        } else {
            binding.rvTasks.adapter = adapterTasks
            binding.rvTasks.layoutManager = LinearLayoutManager(this)
            showGrid = !showGrid
        }
    }

    /*
    As proximas 4 funções são para listar, criar, atualizar e deletar tarefas do banco de dados
    */

    private fun listTasks() {
        tasksViewModel.getAllTasks()
    }

    private fun createTask() {
        val intent = Intent(this, AddTasksActivity::class.java)
        startActivity( intent )
    }

    private fun updateTask(id: Int, task: String) {
        val intent = Intent(this, EditTasksActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("task", task)
        startActivity( intent )
    }

    private fun deleteTask(id: Int) {

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Deletar tarefa.")
            .setMessage("Tem certeza que deseja deletar essa tarefa?")
            .setPositiveButton("Sim!") { _, _ ->
                if (tasksViewModel.deleteTask(id)) {

                    val snackbar = Snackbar.make(
                        binding.root,
                        "Tarefa deletada!",
                        Snackbar.LENGTH_LONG)

                    snackbar.show()
                    listTasks()
                }
            }
            .setNegativeButton("Não", null)
        alertDialog.show()
    }

    //Inicializar o menu nessa activity
    private fun initMenu() {
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    val menuNewTask = menu.findItem( R.id.menuNovaTarefa )
                    menuNewTask.isVisible = true
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId) {
                        R.id.menuConfig -> setRecyclerViewTarefas()
                        R.id.menuNovaTarefa -> createTask()
                    }
                    return true
                }

            }
        )
    }

    //Inicializa minha toolbar customizada
    private fun initToolbar() {
        setSupportActionBar(
            binding.includeListToolbar.toolbar
        ).apply {
            title = getString( R.string.app_name )
        }
    }

}