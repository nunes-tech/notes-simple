package com.example.notessimple.data.repository

import com.example.notessimple.data.db.TasksDAO
import com.example.notessimple.data.model.Task
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDAO: TasksDAO
) : TasksRepository {
    override fun insert(task: Task): Boolean {
        return tasksDAO.insert( task )
    }

    override fun update(task: Task): Boolean {
        return tasksDAO.update( task )
    }

    override fun delete(tarefa_id: Int): Boolean {
        return tasksDAO.delete( tarefa_id )
    }

    override fun list(): List<Task> {
        return tasksDAO.list()
    }
}