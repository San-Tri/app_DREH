package com.example.miprimeraapp

import android.os.Bundle
import android.view.View.OnCreateContextMenuListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment.SavedState

class ListaDispositivosActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_dispositivos)

        databaseHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewDispositivos)

        mostrarDispositivos()
    }

    private fun mostrarDispositivos() {
        val listaDispositivos = databaseHelper.obtenerTodosLosDispositivos()
        adapter = ArrayAdapter(this,  R.layout.item_lista, R.id.textViewItem, listaDispositivos)
        listView.adapter = adapter
    }
}