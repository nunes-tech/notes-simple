package com.nunes.notas.data.repository

import com.nunes.notas.data.model.Task

interface TasksRepository {
    fun insert(task: Task) : Boolean
    fun update(task: Task) : Boolean
    fun delete(tarefa_id : Int ) : Boolean
    fun list() : List<Task>
    fun findOne(id: Int) : Task?
}