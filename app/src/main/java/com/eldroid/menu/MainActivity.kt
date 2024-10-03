package com.eldroid.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Menu"
        supportActionBar?.subtitle = "Action Bar"

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.first_f -> {
                loadFragment(AnotherFragment())
                true
            }
            R.id.dialog_f -> {
                MyDialogFragment().show(supportFragmentManager, "FragmentDialog")
                true
            }
            R.id.menu_f -> {
                true
            }
            R.id.menu_exit_confirm -> {
                finish()
                true
            }
            R.id.menu_exit_cancel -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}