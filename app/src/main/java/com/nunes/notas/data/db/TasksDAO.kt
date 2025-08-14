package com.nunes.notas.data.db

import com.nunes.notas.data.model.Task

interface TasksDAO {

    fun insert(task: Task) : Boolean
    fun update(task: Task ) : Boolean
    fun delete(tarefa_id : Int ) : Boolean
    fun list() : List<Task>
    fun findOne(id: Int) : Task?

}