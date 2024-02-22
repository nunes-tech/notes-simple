package com.example.notessimple.presetation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.notessimple.R
import com.example.notessimple.data.db.TarefasDAO
import com.example.notessimple.data.model.Tarefa
import com.example.notessimple.databinding.ActivityAdicionarTarefasBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdicionarTarefasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefasBinding.inflate(layoutInflater)
    }
    @Inject lateinit var databaseDAO : TarefasDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inicializarToolbar()
        inicializarMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAdicionarTarefa.setOnClickListener {
            val texto = binding.editTextNovaTarefa.text.toString()
            if (texto.isNotEmpty()) {
                val tarefa = Tarefa(-1, texto, null)
                if (databaseDAO.inserir(tarefa)) {
                    Toast.makeText(this, "Sucesso ao salvar", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.layoutTextTarefa.error = "Digite uma tarefa"
            }
        }
    }

    private fun inicializarToolbar() {
        setSupportActionBar(
            binding.includeAdicionarToolbar.toolbar
        ).apply {
            title = "Adicionar nova tarefa"
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