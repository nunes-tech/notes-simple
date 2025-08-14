package com.nunes.notas.data.repository

import com.nunes.notas.data.db.TasksDAO
import com.nunes.notas.data.model.Task
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TasksRepositoryImplTest {

    @Mock
    private lateinit var tasksDAO: TasksDAO
    private lateinit var repositoryImpl: TasksRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repositoryImpl = TasksRepositoryImpl(tasksDAO)
    }

    @Test
    fun insert_deveInserirUmaTask_retornaTrue() {
        val task = Task(1, "teste", 100555888L)

        Mockito.`when`(
            tasksDAO.insert( task )
        ).thenReturn( true )

        val retorno = repositoryImpl.insert(task)

        assertThat( retorno ).isTrue()
    }

    @Test
    fun update_deveAtualizarUmaTask_retornaTrue() {
        val task = Task(1, "teste alterado", 100555888L)

        Mockito.`when`(
            tasksDAO.update( task )
        ).thenReturn( true )

        val retorno = repositoryImpl.update( task )

        assertThat( retorno ).isTrue()
    }

    @Test
    fun delete_deveDeletarUmaTask_retornaTrue() {
        val taskId = 1

        Mockito.`when`(
            tasksDAO.delete( taskId )
        ).thenReturn(true )

        val retorno = repositoryImpl.delete( taskId )

        assertThat( retorno ).isTrue()
    }

    @Test
    fun list_recuperaUmaListaDeTasks_retornaListaPreenchida() {

        Mockito.`when`( tasksDAO.list() ).thenReturn(
            listOf(
                Task(0, "teste 1", 100001),
                Task(1, "teste 2", 100002),
                Task(2, "teste 3", 100003)
            )
        )

        val list = repositoryImpl.list()

        assertThat(list).isNotEmpty()
    }
}