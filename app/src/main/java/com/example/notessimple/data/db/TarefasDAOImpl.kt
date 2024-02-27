package com.example.notessimple.data.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.notessimple.data.model.Tarefa

class TarefasDAOImpl(context: Context) : TarefasDAO {

    private val dataBaseHelper = DatabaseHelper(context)
    private val escreva = dataBaseHelper.writableDatabase
    private val leitura = dataBaseHelper.readableDatabase

    override fun inserir(tarefa: Tarefa): Boolean {
        try {
            val valores = ContentValues()
            valores.put(DatabaseHelper.CAMPO_DESCRICAO, tarefa.descricao)
            escreva.insert(DatabaseHelper.TABELA_TAREFAS, null, valores)

            return true

        } catch (erro: Exception) {
            erro.printStackTrace()
            return false
        }
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        try {
            val valores = ContentValues()
            valores.put(DatabaseHelper.CAMPO_DESCRICAO, tarefa.descricao)
            val args = arrayOf( tarefa.id_tarefa.toString() )
            escreva.update(DatabaseHelper.TABELA_TAREFAS, valores, "${DatabaseHelper.CAMPO_ID} = ?", args)
            Log.i("db_debugger", "Item atualizado com sucesso")
            return true

        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao atualizar")
            return false
        }
    }

    override fun deletar(tarefa_id: Int): Boolean {
        val args = arrayOf( tarefa_id.toString() )
        try {
            escreva.delete(DatabaseHelper.TABELA_TAREFAS, "${DatabaseHelper.CAMPO_ID} = ?", args)
            Log.i("db_debugger", "Sucesso ao deletar")
            return true
        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao deletar")
            return false
        }
    }

    override fun listar(): List<Tarefa> {
        val listaTarefas = mutableListOf<Tarefa>()
        try {
            val sql = "SELECT * FROM ${DatabaseHelper.TABELA_TAREFAS};"

            val cursor = leitura.rawQuery(sql, null)

            while (cursor.moveToNext()) {
                val idColumnIndex = cursor.getColumnIndex( DatabaseHelper.CAMPO_ID )
                val descricaoColumnIndex = cursor.getColumnIndex( DatabaseHelper.CAMPO_DESCRICAO )
                val dataColumnIndex = cursor.getColumnIndex( DatabaseHelper.CAMPO_DATA_CRIACAO )

                val id = cursor.getInt( idColumnIndex )
                val descricao = cursor.getString( descricaoColumnIndex )
                val data = cursor.getString( dataColumnIndex )
                val tarefa = Tarefa( id, descricao, data)
                listaTarefas.add( tarefa )
            }
            Log.i("db_debugger", "Lista recuperada")
            return listaTarefas

        } catch (erro:Exception) {
            erro.printStackTrace()
            Log.i("db_debugger", "Falha ao recuperar Lista")
            return listaTarefas
        }
    }

}