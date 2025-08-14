package com.nunes.notas.presetation.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.nunes.notas.R
import com.nunes.notas.presetation.ui.adapter.TasksAdapter
import org.junit.Rule
import org.junit.Test

class ListTasksActivityTest {

    /*
    * Testes da Interface, que cobre a criação, listagem, edição e deleção dos itens do RecyclerView
    * Logo se os testes passarem então o banco de dados com SQLite está OK também
    */

    @get: Rule
    val listTasksActivityScenario = ActivityScenarioRule(ListTasksActivity::class.java)

    @Test
    fun testCreateTask() {

        //Clica no botão adicionar nota - ListActivity
        onView( withId( R.id.fabAddTask ) ).perform(
            click()
        )

        //Digita a nota no editText - Activity Add
        onView( withId( R.id.editTextNewTaskTitle ) ).perform(
            replaceText("Nota teste")
        )

        //clica no botão e insere a nota
        onView( withId( R.id.btnAddTask ) ).perform(
            click()
        )

    }

    @Test
    fun testClickOnItemFromRecyclerView() {

        //Clica no item do RecyclerView
        onView( withId(R.id.rvTasks) ).perform(
            actionOnItemAtPosition<TasksAdapter.TasksViewHolder>(0, click())
        )
    }

    @Test
    fun testEditTask() {
        testClickOnItemFromRecyclerView()

        onView(withId( R.id.editTextEditTaskTitle ) ).perform(
            typeText(" editado")
        )
        onView( withId( R.id.btnEditTask ) ).perform(
            click()
        )
    }

    @Test
    fun testDeleteTask() {
        onView( withId(R.id.rvTasks) ).perform(
            actionOnItemAtPosition<TasksAdapter.TasksViewHolder>(0, longClick())
        ) // Isso faz o alertDialog seja exibido para que o usuario confirme a exclusão

        //Clica no botão positivo do alertDialog que tem o texto "Sim!"
        onView( withText("Sim!") ).perform(
            click()
        )
    }

}