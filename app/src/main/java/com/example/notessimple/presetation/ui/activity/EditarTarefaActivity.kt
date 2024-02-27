package com.example.notessimple.presetation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.notessimple.R
import com.example.notessimple.data.model.Tarefa
import com.example.notessimple.databinding.ActivityEditarTarefaBinding
import com.example.notessimple.presetation.viewmodel.TarefasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditarTarefaBinding.inflate(layoutInflater)
    }

    private val tarefasViewModel : TarefasViewModel by viewModels()

    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarToolbar()
        inicializarMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            val tarefa = bundle.getString("tarefa") ?: "Falha ao recuperar tarefa"
            binding.editTextEditarTarefa.setText( tarefa )
        }

        binding.btnEditarTarefa.setOnClickListener {
            if ( id != null) {
                val tarefaDescricao = binding.editTextEditarTarefa.text.toString()
                if (tarefaDescricao.isNotEmpty()) {
                    val tarefaEditada = Tarefa(id!!, tarefaDescricao, null)
                    if (tarefasViewModel.atualizarTarefa( tarefaEditada )) {
                        finish()
                    }
                } else {
                    binding.layoutTextEditarTarefa.error = "O campo não pode está vazio!"
                }

            }
        }

    }
    private fun inicializarToolbar() {
        setSupportActionBar(
            binding.includeEditarToolbar.toolbar
        ).apply {
            title = "Editar tarefa"
        }
    }

    private fun inicializarMenu() {
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_principal, menu)
                    val menuOpcaoOcultar = menu.findItem( R.id.menuConfig )
                    menuOpcaoOcultar.isVisible = false
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