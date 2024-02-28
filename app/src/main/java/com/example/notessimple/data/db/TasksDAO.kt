package com.example.notessimple.data.db

import com.example.notessimple.data.model.Task

interface TasksDAO {

    fun insert(task: Task) : Boolean
    fun update(task: Task ) : Boolean
    fun delete(tarefa_id : Int ) : Boolean
    fun list() : List<Task>

}