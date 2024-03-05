package com.example.notessimple.presetation.ui.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notessimple.data.model.Task
import com.example.notessimple.databinding.TasksRecyclerBinding
import formatDateCustom

class TasksAdapter(
    private val listener: ListenerOnRecyclerView
) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    private var listTasks : List<Task> = emptyList()

    inner class TasksViewHolder(
        private val binding: TasksRecyclerBinding
    ) : RecyclerView.ViewHolder( binding.root ){

        //Formatador de data para a nota
        private val formatDate = SimpleDateFormat("EEEE HH:mm a")
        //private val formatDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

        fun bind(position: Int) {
            binding.textTask.text = listTasks[position].descricao
            binding.textDate.text = listTasks[position].data_cricao.formatDateCustom()
            binding.textIdTask.text = listTasks[position].id_tarefa.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TasksRecyclerBinding.inflate( layoutInflater, parent, false)

        binding.cardView.setOnLongClickListener {
            val idTask = binding.textIdTask.text.toString()
            listener.itemLongClick( idTask.toInt() )
            true
        }

        binding.cardView.setOnClickListener {
            val idTask = binding.textIdTask.text.toString()
            val task = binding.textTask.text.toString()
            listener.itemClick( idTask.toInt(), task )
        }

        return TasksViewHolder( binding )
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListTasks(list : List<Task>) {
        this.listTasks = list.sortedByDescending {
            it.data_cricao
            //it.id_tarefa
        }
        notifyDataSetChanged()
    }

}