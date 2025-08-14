package com.nunes.notas.presetation.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.google.android.material.internal.ViewUtils.showKeyboard
import com.nunes.notas.R
import com.nunes.notas.data.model.Task
import com.nunes.notas.databinding.ActivityEditTasksBinding
import com.nunes.notas.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTasksActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    private var id: Int? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            if (id != null) {
                val task = tasksViewModel.findOne(id!!)
                if (task != null) {
                    binding.editTextEditTaskTitle.setText(task.title)
                    binding.editTextTextTaskNote.setText(task.description)
                    binding.editTextEditTaskTitle.requestFocus()
                    showKeyboard(binding.editTextEditTaskTitle)
                } else {
                    finish()
                }
            }
        }

        binding.btnEditTask.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        if ( id != null) {
            val taskTitle = binding.editTextEditTaskTitle.text.toString()
            val taskDescription = binding.editTextTextTaskNote.text.toString()
            if (taskDescription.isNotEmpty() && taskTitle.isNotEmpty()) {
                val dateCurrentMillis = System.currentTimeMillis()
                val taskEdited = Task(id!!, taskTitle, taskDescription, dateCurrentMillis)
                if (tasksViewModel.updateTask( taskEdited )) {
                    finish()
                }
            } else {
                //binding.layoutTextEditTask.error = "O campo não pode está vazio!"
            }

        }
    }

    private fun initToolbar() {
        setSupportActionBar(
            binding.includeEditToolbar.toolbar
        ).apply {
            title = "Editar tarefa"
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

}