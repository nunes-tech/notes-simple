package com.example.notessimple.presetation.ui.adapter

interface ListenerOnRecyclerView {
    fun itemClick(id: Int, tarefa: String)
    fun itemLongClick(id: Int)
}