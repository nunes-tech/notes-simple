package com.example.notessimple.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    NAME_DB,
    null,
    VERSION_DB
) {

    companion object {
        const val NAME_DB = "Tasks.db"
        const val VERSION_DB = 1
        const val TABLE_TASKS = "my_tasks"
        const val FIELD_ID = "id_task"
        const val FIELD_DESC = "description"
        const val FIELD_DATE_CREATE = "date_create"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $TABLE_TASKS(" +
                " $FIELD_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " $FIELD_DESC TEXT, " +
                " $FIELD_DATE_CREATE LONG);"

        try {
            db?.execSQL(sql)
            Log.i("db_debugger", "Sucesso ao criar tabela: $TABLE_TASKS")
        } catch (erro:Exception) {
            Log.i("db_debugger", "Falha ao criar tabela: $TABLE_TASKS")
            erro.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("db_debugger", "onUpgrade foi chamado")
    }

}