package com.example.notessimple.presetation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessimple.R
import com.example.notessimple.data.db.ITarefaDAO
import com.example.notessimple.data.model.Tarefa
import com.example.notessimple.databinding.ActivityListarTarefasBinding
import com.example.notessimple.presetation.ui.adapter.ListenerOnRecyclerView
import com.example.notessimple.presetation.ui.adapter.TarefasAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListarTarefasActivity : AppCompatActivity(), ListenerOnRecyclerView {

    @Inject lateinit var databaseDAO : ITarefaDAO

    private var exibirGrid = false

    private val binding by lazy {
        ActivityListarTarefasBinding.inflate( layoutInflater )
    }
    private val adapterTarefa by lazy {
        TarefasAdapter(this)
    }
    private var listaTarefas: List<Tarefa> = emptyList()

    override fun onStart() {
        super.onStart()
        listTasks()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )

        initToolbar()
        initMenu()
        setRecyclerViewTarefas()
        binding.fabAddTarefa.setOnClickListener { createTask() }
    }

    override fun itemClick(id: Int, tarefa: String) {
        updateTask(id, tarefa)
    }

    override fun itemLongClick(id: Int) {
        deleteTask(id)
    }
    private fun setRecyclerViewTarefas() {
        if (exibirGrid) {
            binding.rvTarefas.adapter = adapterTarefa
            binding.rvTarefas.layoutManager = GridLayoutManager(this, 2)
            exibirGrid = !exibirGrid
        } else {
            binding.rvTarefas.adapter = adapterTarefa
            binding.rvTarefas.layoutManager = LinearLayoutManager(this)
            exibirGrid = !exibirGrid
        }
    }

    /*
    As proximas 4 funções são para listar, criar, atualizar e deletar tarefas do banco de dados
    */

    private fun listTasks() {
        listaTarefas = databaseDAO.listar()
        adapterTarefa.setListaTarefas( listaTarefas )
        if(listaTarefas.isEmpty()) {
            binding.imageSemNotas.visibility = View.VISIBLE
        } else {
            binding.imageSemNotas.visibility = View.GONE
        }
    }

    private fun createTask() {
        val intent = Intent(this, AdicionarTarefasActivity::class.java)
        startActivity( intent )
    }

    private fun updateTask(id: Int, tarefa: String) {
        val intent = Intent(this, EditarTarefaActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("tarefa", tarefa)
        startActivity( intent )
    }

    private fun deleteTask(id: Int) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Deletar tarefa.")
            .setMessage("Tem certeza que deseja deletar essa tarefa?")
            .setPositiveButton("Sim!") { _, _ ->
                if (databaseDAO.deletar(id)) {
                    Toast.makeText(
                        this@ListarTarefasActivity,
                        "Tarefa removida",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    menuInflater.inflate(R.menu.menu_principal, menu)
                    val menuNovaTarefa = menu.findItem( R.id.menuNovaTarefa )
                    menuNovaTarefa.isVisible = true
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
            binding.includeToolbar.toolbar
        ).apply {
            title = getString( R.string.app_name )
        }
    }

}