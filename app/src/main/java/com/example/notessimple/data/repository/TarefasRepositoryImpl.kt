package com.example.notessimple.data.repository

import com.example.notessimple.data.db.TarefasDAO
import com.example.notessimple.data.model.Tarefa
import javax.inject.Inject

class TarefasRepositoryImpl @Inject constructor(
    private val tarefasDAO: TarefasDAO
) : TarefasRepository {
    override fun inserir(tarefa: Tarefa): Boolean {
        return tarefasDAO.inserir( tarefa )
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        return tarefasDAO.atualizar( tarefa )
    }

    override fun deletar(tarefa_id: Int): Boolean {
        return tarefasDAO.deletar( tarefa_id )
    }

    override fun listar(): List<Tarefa> {
        return tarefasDAO.listar()
    }
}