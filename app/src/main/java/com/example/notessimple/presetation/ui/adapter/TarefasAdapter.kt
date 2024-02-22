package com.example.notessimple.presetation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notessimple.data.model.Tarefa
import com.example.notessimple.databinding.TarefasRecyclerBinding

class TarefasAdapter(
    private val listener: ListenerOnRecyclerView
) : RecyclerView.Adapter<TarefasAdapter.TarefasViewHolder>() {

    private var listaTarefas : List<Tarefa> = emptyList()

    inner class TarefasViewHolder(
        private val binding: TarefasRecyclerBinding
    ) : RecyclerView.ViewHolder( binding.root ){

        fun bind(position: Int) {
            binding.textTarefa.text = listaTarefas[position].descricao
            binding.textIdTarefa.text = listaTarefas[position].id_tarefa.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TarefasRecyclerBinding.inflate( layoutInflater, parent, false)

        binding.cardView.setOnLongClickListener {
            val idTarefa = binding.textIdTarefa.text.toString()
            listener.itemLongClick( idTarefa.toInt() )
            true
        }

        binding.cardView.setOnClickListener {
            val idTarefa = binding.textIdTarefa.text.toString()
            val tarefa = binding.textTarefa.text.toString()
            listener.itemClick( idTarefa.toInt(), tarefa )
        }

        return TarefasViewHolder( binding )
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

    override fun onBindViewHolder(holder: TarefasViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListaTarefas(lista : List<Tarefa>) {
        this.listaTarefas = lista
        notifyDataSetChanged()
    }
}