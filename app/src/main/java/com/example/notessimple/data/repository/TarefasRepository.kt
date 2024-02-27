package com.example.notessimple.data.repository

import com.example.notessimple.data.model.Tarefa

interface TarefasRepository {
    fun inserir( tarefa: Tarefa) : Boolean
    fun atualizar( tarefa: Tarefa) : Boolean
    fun deletar( tarefa_id : Int ) : Boolean
    fun listar() : List<Tarefa>
}