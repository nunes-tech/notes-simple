package com.nunes.notas.presetation.ui.adapter

interface ListenerOnRecyclerView {
    fun itemClick(id: Int, tarefa: String)
    fun itemLongClick(id: Int)
}