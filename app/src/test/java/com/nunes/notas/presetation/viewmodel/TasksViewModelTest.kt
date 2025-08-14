package com.nunes.notas.presetation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nunes.notas.data.model.Task
import com.nunes.notas.data.repository.TasksRepository
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TasksViewModelTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TasksRepository
    private lateinit var tasksViewModel: TasksViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        tasksViewModel = TasksViewModel( repository )
    }

    @Test
    fun getAllTasks_testaLiveData_atualizaLiveData() {

        Mockito.`when`( repository.list() ).thenReturn(
            listOf(
                Task(0, "teste 1", 100001),
                Task(1, "teste 2", 100002),
                Task(2, "teste 3", 100003)
            )
        )

        tasksViewModel.getAllTasks()
        val liveData = tasksViewModel.listTasks.getOrAwaitValue()

        assertThat( liveData ).isNotEmpty()
    }
}