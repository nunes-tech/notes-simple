package com.example.notessimple.data.db

import com.example.notessimple.data.model.Tarefa

interface ITarefaDAO {

    fun inserir( tarefa: Tarefa) : Boolean
    fun atualizar( tarefa: Tarefa ) : Boolean
    fun deletar( tarefa_id : Int ) : Boolean
    fun listar() : List<Tarefa>

}