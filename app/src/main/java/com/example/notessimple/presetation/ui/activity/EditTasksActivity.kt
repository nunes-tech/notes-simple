package com.example.notessimple.presetation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.notessimple.R
import com.example.notessimple.data.model.Task
import com.example.notessimple.databinding.ActivityEditTasksBinding
import com.example.notessimple.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTasksActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            val task = bundle.getString("task") ?: "Falha ao recuperar tarefa"
            binding.editTextEditTask.setText( task )
        }

        binding.btnEditTask.setOnClickListener {
            if ( id != null) {
                val taskDescription = binding.editTextEditTask.text.toString()
                if (taskDescription.isNotEmpty()) {
                    val taskEdited = Task(id!!, taskDescription, null)
                    if (tasksViewModel.updateTask( taskEdited )) {
                        finish()
                    }
                } else {
                    binding.layoutTextEditTask.error = "O campo não pode está vazio!"
                }

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