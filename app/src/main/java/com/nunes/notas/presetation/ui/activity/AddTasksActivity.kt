package com.nunes.notas.presetation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.google.android.material.internal.ViewUtils.showKeyboard
import com.nunes.notas.R
import com.nunes.notas.data.model.Task
import com.nunes.notas.databinding.ActivityAddTasksBinding
import com.nunes.notas.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTasksActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAddTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.editTextNewTaskTitle.requestFocus()
        showKeyboard(binding.editTextNewTaskTitle)

        binding.btnAddTask.setOnClickListener {
            saveNote()
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
                    val menuSave = menu.findItem(R.id.save)
                    menuSave.icon?.setTint(getColor(R.color.white))
                    menuSave.isVisible = true
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId) {
                        android.R.id.home -> finish()
                        R.id.save -> saveNote()
                    }
                    return true
                }

            }
        )
    }

    private fun saveNote() {
        val taskTitle = binding.editTextNewTaskTitle.text.toString()
        val taskDescription = binding.editTextNewTaskNote.text.toString()
        if (taskDescription.isNotEmpty()) {
            val taskTitleValid = taskTitle.ifEmpty { taskDescription.substring(0, 25) }
            Toast.makeText(this, taskTitleValid, Toast.LENGTH_SHORT).show()
            val dateCurrentMillis = System.currentTimeMillis()
            val task = Task(-1, taskTitleValid, taskDescription, dateCurrentMillis)
            if (tasksViewModel.insertTask(task)) {
                Toast.makeText(this, "Sucesso ao salvar", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_SHORT).show()
            }
        } else {
            //binding.layoutTextAddTask.error = "Digite uma tarefa"
        }
    }

}