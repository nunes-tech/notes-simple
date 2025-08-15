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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.google.android.material.internal.ViewUtils.showKeyboard
import com.nunes.notas.R
import com.nunes.notas.data.model.Task
import com.nunes.notas.databinding.ActivityEditTasksBinding
import com.nunes.notas.presetation.viewmodel.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTasksActivity : AppCompatActivity() {
    private var boldActive = false
    private var italicActive = false
    private var underlineActive = false

    private val binding by lazy {
        ActivityEditTasksBinding.inflate(layoutInflater)
    }

    private val tasksViewModel : TasksViewModel by viewModels()

    private var id: Int? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        initMenu()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getInt("id")
            if (id != null) {
                val task = tasksViewModel.findOne(id!!)
                if (task != null) {
                    binding.editTextEditTaskTitle.setText(task.title)
                    binding.editTextTextTaskNote.setText(getHtml(task.description!!))
                    binding.editTextEditTaskTitle.requestFocus()
                    showKeyboard(binding.editTextEditTaskTitle)
                } else {
                    finish()
                }
            }
        }
        binding.btnEditBold.setOnCheckedChangeListener { _, isCheched ->
            boldActive = isCheched
        }
        binding.btnEditItalic.setOnCheckedChangeListener { _, isCheched ->
            italicActive = isCheched
        }
        binding.btnEditSub.setOnCheckedChangeListener { _, isCheched ->
            underlineActive = isCheched
        }

        binding.btnEditTask.setOnClickListener {
            saveNote()
        }
        onChangedEditText()

    }

    private fun onChangedEditText() {
        binding.editTextTextTaskNote.addTextChangedListener(
            object : TextWatcher {
                var start = 0
                var end = 0

                override fun afterTextChanged(s: Editable?) {
                    if (end > start) {
                        applyStyleText(s, start, end)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    this.start = start
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    this.end = start + count
                }

            }
        )
    }

    private fun applyStyleText(s: Editable?, start: Int, end: Int) {
        if (s == null) return

        if(boldActive) {
            s.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if (italicActive) {
            s.setSpan(StyleSpan(Typeface.ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        if(underlineActive){
            s.setSpan(UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun saveNote() {
        if ( id != null) {
            val taskTitle = binding.editTextEditTaskTitle.text.toString()
            val taskDescription = binding.editTextTextTaskNote.text.toString()
            if (taskDescription.isNotEmpty() && taskTitle.isNotEmpty()) {
                val dateCurrentMillis = System.currentTimeMillis()
                val taskEdited = Task(id!!, taskTitle, saveToHtml(binding.editTextTextTaskNote.text), dateCurrentMillis)
                if (tasksViewModel.updateTask( taskEdited )) {
                    finish()
                }
            } else {
                //binding.layoutTextEditTask.error = "O campo não pode está vazio!"
            }

        }
    }

    private fun initToolbar() {
        setSupportActionBar(
            binding.includeEditToolbar.toolbar
        ).apply {
            title = "Editar tarefa"
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

    private fun saveToHtml(html: Editable): String {
        return Html.toHtml(html, Html.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
    }

    private fun getHtml(html: String): Spanned? {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    }

}