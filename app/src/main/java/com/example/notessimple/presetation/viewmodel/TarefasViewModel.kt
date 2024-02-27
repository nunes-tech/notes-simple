package com.example.notessimple.presetation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notessimple.data.model.Tarefa
import com.example.notessimple.data.repository.TarefasRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TarefasViewModel @Inject constructor(
    private val repository: TarefasRepository
) : ViewModel() {

    private val _listaTarefas = MutableLiveData<List<Tarefa>>()
    val listaTarefas:LiveData<List<Tarefa>> get() = _listaTarefas

    fun recuperarTarefas() {
        _listaTarefas.postValue(
            repository.listar()
        )
    }

    fun atualizarTarefa(tarefa: Tarefa): Boolean {
        return repository.atualizar(tarefa)
    }

    fun inserirTarefa(tarefa: Tarefa): Boolean {
        return repository.inserir(tarefa)
    }

    fun deletarTarefa(idTarefa: Int): Boolean {
        return repository.deletar(idTarefa)
    }

}