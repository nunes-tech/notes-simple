package com.nunes.notas.presetation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nunes.notas.data.model.Task
import com.nunes.notas.data.repository.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TasksRepository
) : ViewModel() {

    private val _listTasks = MutableLiveData<List<Task>>()
    val listTasks:LiveData<List<Task>> get() = _listTasks

    fun getAllTasks() {
        _listTasks.postValue(
            repository.list()
        )
    }

    fun updateTask(task: Task): Boolean {
        return repository.update(task)
    }

    fun insertTask(task: Task): Boolean {
        return repository.insert(task)
    }

    fun deleteTask(idTask: Int): Boolean {
        return repository.delete(idTask)
    }

    fun findOne(id: Int) = repository.findOne(id)

}