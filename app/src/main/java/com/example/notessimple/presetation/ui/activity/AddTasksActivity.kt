package com.example.notessimple.presetation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.notessimple.R
import com.example.notessimple.data.model.Task
import com.example.notessimple.databinding.ActivityAddTasksBinding
import com.example.notessimple.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTasksActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAddTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAddTask.setOnClickListener {

            val taskDescription = binding.editTextNewTask.text.toString()
            if (taskDescription.isNotEmpty()) {
                val dateCurrentMillis = System.currentTimeMillis()
                val task = Task(-1, taskDescription, dateCurrentMillis)
                if (tasksViewModel.insertTask(task)) {
                    Toast.makeText(this, "Sucesso ao salvar", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.layoutTextAddTask.error = "Digite uma tarefa"
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(
            binding.includeAddToolbar.toolbar
        ).apply {
            title = "Adicionar nova tarefa"
        }
    }
    private fun initMenu() {
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    val menuItemHide = menu.findItem( R.id.menuConfig )
                    menuItemHide.isVisible = false
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId) {
                        android.R.id.home -> finish()
                    }
                    return true
                }

            }
        )
    }
}