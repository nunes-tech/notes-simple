package com.nunes.notas.presetation.ui.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.google.android.material.internal.ViewUtils.showKeyboard
import com.nunes.notas.R
import com.nunes.notas.data.model.Task
import com.nunes.notas.databinding.ActivityAddTasksBinding
import com.nunes.notas.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTasksActivity : AppCompatActivity() {
    private var boldActive = false
    private var italicActive = false
    private var underlineActive = false

    private val binding by lazy {
        ActivityAddTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.editTextNewTaskTitle.requestFocus()
        showKeyboard(binding.editTextNewTaskTitle)

        binding.btnAddBold.setOnCheckedChangeListener { _, isChecked ->
            boldActive = isChecked
        }

        binding.btnAddItalic.setOnCheckedChangeListener { _, isChecked ->
            italicActive = isChecked
        }

        binding.btnAddSub.setOnCheckedChangeListener { _, isChecked ->
            underlineActive = isChecked
        }

        binding.btnAddTask.setOnClickListener {
            saveNote()
        }
        onChangedText()
    }

    private fun initToolbar() {
        setSupportActionBar(
            binding.includeAddToolbar.toolbar
        ).apply {
            title = "Adicionar nova tarefa"
        }
    }
    private fun initMenu() {
        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    val menuItemHide = menu.findItem( R.id.menuConfig )
                    menuItemHide.isVisible = false
                    val menuSave = menu.findItem(R.id.save)
                    menuSave.icon?.setTint(getColor(R.color.white))
                    menuSave.isVisible = true
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId) {
                        android.R.id.home -> finish()
                        R.id.save -> saveNote()
                    }
                    return true
                }

            }
        )
    }

    private fun saveNote() {
        val taskTitle = binding.editTextNewTaskTitle.text.toString()
        val taskDescription = binding.editTextNewTaskNote.text.toString()
        if (taskDescription.isNotEmpty()) {
            val taskTitleValid = taskTitle.ifEmpty { taskDescription.substring(0, 40) }
            Toast.makeText(this, taskTitleValid, Toast.LENGTH_SHORT).show()
            val dateCurrentMillis = System.currentTimeMillis()
            val task = Task(
                -1,
                taskTitleValid,
                saveToHtml(binding.editTextNewTaskNote.text),
                dateCurrentMillis
            )
            if (tasksViewModel.insertTask(task)) {
                Toast.makeText(this, "Sucesso ao salvar", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Falha ao salvar", Toast.LENGTH_SHORT).show()
            }
        } else {
            //binding.layoutTextAddTask.error = "Digite uma tarefa"
        }
    }

    private fun onChangedText() {
        binding.editTextNewTaskNote.addTextChangedListener(object : TextWatcher {
            private var start = 0
            private var end = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                this.start = start
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                end = start + count
            }

            override fun afterTextChanged(s: Editable?) {
                if (end > start) { // Foi inserido texto
                    applyDynamicStyle(s, start, end)
                }
            }
        })
    }

    private fun applyDynamicStyle(editable: Editable?, start: Int, end: Int) {
        if (editable == null) return

        if (boldActive) {
            editable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (italicActive) {
            editable.setSpan(StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (underlineActive) {
            editable.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun saveToHtml(html: Editable): String {
        return Html.toHtml(html, Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
    }

    private fun getHtml(html: String): Spanned {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    }

}