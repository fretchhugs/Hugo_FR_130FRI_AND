package com.eldroid.bottomnavigation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

data class TodoItem(val id: Int, val title: String, val isCompleted: Boolean, val imageResId: Int)

class ListFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var taskTitleEditText: EditText
    private lateinit var addTaskButton: Button
    private val todos = mutableListOf<TodoItem>()
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        listView = view.findViewById(R.id.listView)
        taskTitleEditText = view.findViewById(R.id.addtsk)
        addTaskButton = view.findViewById(R.id.addbtn)

        todoAdapter = TodoAdapter(requireContext(), todos)
        listView.adapter = todoAdapter

        addTaskButton.setOnClickListener {
            val title = taskTitleEditText.text.toString()
            if (title.isNotBlank()) {
                addTask(title)
                taskTitleEditText.text.clear()
            }
        }

        return view
    }

    private fun addTask(title: String) {
        val newId = todos.maxOfOrNull { it.id }?.plus(1) ?: 1
        val newTask = TodoItem(newId, title, false, R.drawable.icon2)
        todos.add(newTask)
        todoAdapter.notifyDataSetChanged()
    }

    private inner class TodoAdapter(private val context: Context, private val todos: MutableList<TodoItem>) : BaseAdapter() {
        private val inflater: LayoutInflater = LayoutInflater.from(context)

        private val doubleTapDelay: Long = 300
        private var lastClickTime: Long = 0
        private var lastPosition: Int = -1

        override fun getCount(): Int = todos.size

        override fun getItem(position: Int): Any = todos[position]

        override fun getItemId(position: Int): Long = position.toLong()

        @SuppressLint("ClickableViewAccessibility")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: inflater.inflate(R.layout.item_todo, parent, false)
            val todoItem = getItem(position) as TodoItem

            val imageView = view.findViewById<ImageView>(R.id.todo_image)
            val checkBox = view.findViewById<CheckBox>(R.id.todo_checkbox)
            val titleTextView = view.findViewById<TextView>(R.id.todo_title)

            imageView.setImageResource(todoItem.imageResId)
            checkBox.isChecked = todoItem.isCompleted
            titleTextView.text = todoItem.title

            view.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val clickTime = System.currentTimeMillis()
                        if (clickTime - lastClickTime < doubleTapDelay && lastPosition == position) {
                            showEditOrDeleteDialog(position)
                        }
                        lastClickTime = clickTime
                        lastPosition = position
                    }
                }
                true
            }

            return view
        }

        private fun showEditOrDeleteDialog(position: Int) {
            getItem(position) as TodoItem
            AlertDialog.Builder(context)
                .setTitle("Choose an option")
                .setItems(arrayOf("Edit", "Delete")) { _, which ->
                    when (which) {
                        0 -> showEditDialog(position)
                        1 -> deleteTask(position)
                    }
                }
                .show()
        }

        private fun showEditDialog(position: Int) {
            val todoItem = getItem(position) as TodoItem
            val editText = EditText(context).apply {
                setText(todoItem.title)
            }

            AlertDialog.Builder(context)
                .setTitle("Edit Task")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newTitle = editText.text.toString()
                    if (newTitle.isNotBlank()) {
                        todos[position] = todoItem.copy(title = newTitle)
                        notifyDataSetChanged()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun deleteTask(position: Int) {
            todos.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
