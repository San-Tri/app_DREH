package com.example.miprimeraapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DispositivoActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper //  declarar la variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispositivo)

        // inicializar la variable
        databaseHelper = DatabaseHelper(this)

        val etTipoDispositivo = findViewById<EditText>(R.id.et_tipo_dispositivo)
        val etMarca = findViewById<EditText>(R.id.et_marca)
        val etModelo = findViewById<EditText>(R.id.et_modelo)
        val etCodigo = findViewById<EditText>(R.id.et_codigo)
        val etNombre = findViewById<EditText>(R.id.nombre)
        val etCargo = findViewById<EditText>(R.id.cargo)
        val etArea = findViewById<EditText>(R.id.area)
        val spinner_tipo = findViewById<Spinner>(R.id.spinner_tipo)
        val spinner_estado = findViewById<Spinner>(R.id.spinner_estado)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar)


    // Opciones para el Spinner
        val opciones_tipo = arrayOf("Preventivo", "Correctivo")
        val opciones_estado = arrayOf("Pendiente", "En mantenimiento", "Concluido")

    // Adaptador personalizado con el nuevo diseño
        val adapter_tipo = ArrayAdapter(this, R.layout.item_spinner, opciones_tipo)
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapter_estado = ArrayAdapter(this, R.layout.item_spinner, opciones_estado)
        adapter_estado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    // Asignar el adaptador al Spinner
        spinner_tipo.adapter = adapter_tipo
        spinner_estado.adapter = adapter_estado


    // guardar los datos en SQLite cuando el usuario presione el botón.
       btnGuardar.setOnClickListener{
           val tipo = etTipoDispositivo.text.toString()
           val marca = etMarca.text.toString()
           val modelo = etModelo.text.toString()
           val codigo = etCodigo.text.toString()
           val nombre = etNombre.text.toString()
           val cargo = etCargo.text.toString()
           val area = etArea.text.toString()
           val tipoMantenimiento = spinner_tipo.selectedItem.toString()
           val estado = spinner_estado.selectedItem.toString()

           if (tipo.isNotEmpty() && marca.isNotEmpty() && modelo.isNotEmpty() && codigo.isNotEmpty()) {
               val resultado = databaseHelper.insertarDispositivo(
                   tipo, marca, modelo, codigo, nombre, cargo, area, tipoMantenimiento, estado
               )

               if (resultado != -1L) {
                   Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
               } else {
                   Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
               }
           } else {
               Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
           }
       }

    }

    private fun limpiarCampos(vararg campos: Any) {
        for (campo in campos) {
            when (campo){
                is EditText -> campo.text.clear()
                is Spinner -> campo.setSelection(0)
            }
        }
    }
}