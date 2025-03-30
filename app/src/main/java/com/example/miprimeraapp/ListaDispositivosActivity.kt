package com.example.miprimeraapp

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.Menu
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment.SavedState

class ListaDispositivosActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listaDispositivos: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_dispositivos)

        databaseHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewDispositivos)

        // Registrar el menú contextual
        registerForContextMenu(listView)

        mostrarDispositivos()
    }

    private fun mostrarDispositivos() {
        listaDispositivos = databaseHelper.obtenerTodosLosDispositivos().toMutableList()
        adapter = ArrayAdapter(this,  android.R.layout.simple_list_item_1, listaDispositivos)
        listView.adapter = adapter
    }

    // Crear el menú contextual
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contextual, menu)
    }

    // Manejar opciones del menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val dispositivoSeleccionado = listaDispositivos[info.position]

        return when (item.itemId) {
            R.id.editar -> {
                editarDispositivo(dispositivoSeleccionado)
                true
            }
            R.id.eliminar -> {
                eliminarDispositivo(dispositivoSeleccionado)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    //Función para editar un dispositivo
    private fun editarDispositivo(dispositivo: String) {
        val intent = Intent(this, EditarDispositivoActivity::class.java)
        val codigo = extraerCodigo(dispositivo) // Extrae el código correcto
        intent.putExtra("codigo", codigo) // Enviar el código correcto
        startActivity(intent)
    }

    // Función para eliminar un dispositivo
    private fun eliminarDispositivo(dispositivo: String) {
        val codigo = extraerCodigo(dispositivo) // Extrae el código correcto

        AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("¿Estás seguro que deseas eliminar este dispositivo?")
            .setPositiveButton("Sí") { _, _ ->
                databaseHelper.eliminarDispositivo(codigo) // Ahora usa solo el código
                mostrarDispositivos() // Refrescar la lista
            }
            .setNegativeButton("No", null)
            .show()
    }

    // Función para extraer solo el código del texto del dispositivo
    private fun extraerCodigo(texto: String): String {
        return texto.substringAfter("Código: ").trim()
    }
}