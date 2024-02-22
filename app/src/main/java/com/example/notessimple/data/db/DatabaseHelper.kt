package com.example.notessimple.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    NOME_BANCO_DADOS,
    null,
    VERSAO_DB
) {

    companion object {
        const val NOME_BANCO_DADOS = "Tarefas.db"
        const val VERSAO_DB = 1
        const val TABELA_TAREFAS = "minhas_tarefas"
        const val CAMPO_ID = "id_tarefa"
        const val CAMPO_DESCRICAO = "descricao"
        const val CAMPO_DATA_CRIACAO = "data_criacao"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = "CREATE TABLE IF NOT EXISTS $TABELA_TAREFAS(" +
                " $CAMPO_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                " $CAMPO_DESCRICAO TEXT, " +
                " $CAMPO_DATA_CRIACAO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP);"

        try {
            db?.execSQL(sql)
            Log.i("db_debugger", "Sucesso ao criar tabela: $TABELA_TAREFAS")
        } catch (erro:Exception) {
            Log.i("db_debugger", "Falha ao criar tabela: $TABELA_TAREFAS")
            erro.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("db_debugger", "onUpgrade foi chamado")
    }

}