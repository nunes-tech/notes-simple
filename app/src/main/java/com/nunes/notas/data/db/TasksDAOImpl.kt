package com.nunes.notas.data.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.nunes.notas.data.model.Task

class TasksDAOImpl(context: Context) : TasksDAO {

    private val dataBaseHelper = DBHelper(context)
    private val writer = dataBaseHelper.writableDatabase
    private val read = dataBaseHelper.readableDatabase

    override fun insert(task: Task): Boolean {
        try {
            val values = ContentValues()
            values.put(DBHelper.FIELD_DESC, task.description)
            values.put(DBHelper.FIELD_TITLE, task.title)
            values.put(DBHelper.FIELD_DATE_CREATE, task.dateCreate)
            writer.insert(DBHelper.TABLE_TASKS, null, values)

            return true

        } catch (error: Exception) {
            error.printStackTrace()
            return false
        }
    }

    override fun update(task: Task): Boolean {
        try {
            val values = ContentValues()
            values.put(DBHelper.FIELD_TITLE, task.title)
            values.put(DBHelper.FIELD_DESC, task.description)
            values.put(DBHelper.FIELD_DATE_CREATE, task.dateCreate)
            val args = arrayOf( task.idTask.toString() )
            writer.update(DBHelper.TABLE_TASKS, values, "${DBHelper.FIELD_ID} = ?", args)
            Log.i("db_debugger", "Item atualizado com sucesso")
            return true

        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao atualizar")
            return false
        }

    }

    override fun delete(task_id: Int): Boolean {
        val args = arrayOf( task_id.toString() )
        try {
            writer.delete(DBHelper.TABLE_TASKS, "${DBHelper.FIELD_ID} = ?", args)
            Log.i("db_debugger", "Sucesso ao deletar")
            return true
        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao deletar")
            return false
        }
    }

    override fun list(): List<Task> {
        val listTasks = mutableListOf<Task>()
        try {
            val sql = "SELECT * FROM ${DBHelper.TABLE_TASKS};"

            val cursor = read.rawQuery(sql, null)

            while (cursor.moveToNext()) {
                val idColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_ID )
                val titleColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_TITLE )
                val descricaoColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_DESC )
                val dataColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_DATE_CREATE )

                val id = cursor.getInt( idColumnIndex )
                val title = cursor.getString( titleColumnIndex )
                val descricao = cursor.getString( descricaoColumnIndex )
                val data = cursor.getLong( dataColumnIndex )
                val task = Task( id, title, descricao, data)
                listTasks.add( task )
            }
            cursor.close()
            Log.i("db_debugger", "Lista recuperada")
            return listTasks

        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao recuperar Lista")
            return listTasks
        }
    }

    override fun findOne(id: Int): Task? {
        try {
            val sql = "SELECT * FROM ${DBHelper.TABLE_TASKS} WHERE ${DBHelper.FIELD_ID} = $id;"
            val cursor = read.rawQuery(sql, null)
            cursor.moveToFirst()
            val idColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_ID )
            val titleColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_TITLE )
            val descricaoColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_DESC )
            val dataColumnIndex = cursor.getColumnIndex( DBHelper.FIELD_DATE_CREATE )
            val id = cursor.getInt( idColumnIndex )
            val title = cursor.getString( titleColumnIndex )
            val descricao = cursor.getString( descricaoColumnIndex )
            val data = cursor.getLong( dataColumnIndex )
            val task = Task( id, title, descricao, data)
            cursor.close()
            return task
        } catch (error: Exception) {
            error.printStackTrace()
            return null
        }
    }

}