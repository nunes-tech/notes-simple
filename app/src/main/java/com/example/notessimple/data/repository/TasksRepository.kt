package com.example.notessimple.data.repository

import com.example.notessimple.data.model.Task

interface TasksRepository {
    fun insert(task: Task) : Boolean
    fun update(task: Task) : Boolean
    fun delete(tarefa_id : Int ) : Boolean
    fun list() : List<Task>
}