package com.example.miprimeraapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarDispositivoActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var editTextTipo: EditText
    private lateinit var editTextMarca: EditText
    private lateinit var editTextModelo: EditText
    private lateinit var editTextNombre: EditText
    private lateinit var editTextCargo: EditText
    private lateinit var editTextArea: EditText
    private lateinit var editTextTipoMantenimiento: EditText
    private lateinit var editTextEstado: EditText
    private lateinit var btnGuardar: Button

    private var codigo: String = "" // Código del dispositivo que se editará

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_dispositivo)

        databaseHelper = DatabaseHelper(this)

        // Referenciar los EditText
        editTextTipo = findViewById(R.id.editTextTipo)
        editTextMarca = findViewById(R.id.editTextMarca)
        editTextModelo = findViewById(R.id.editTextModelo)
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextCargo = findViewById(R.id.editTextCargo)
        editTextArea = findViewById(R.id.editTextArea)
        editTextTipoMantenimiento = findViewById(R.id.editTextTipoMantenimiento)
        editTextEstado = findViewById(R.id.editTextEstado)
        btnGuardar = findViewById(R.id.btnGuardar)

        // Obtener el código del dispositivo desde el Intent
        codigo = intent.getStringExtra("codigo") ?: ""

        if (codigo.isNotEmpty()) {
            cargarDatosDispositivo(codigo)
        } else {
            Toast.makeText(this, "Código recibido: $codigo", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad si no hay código válido
        }

        btnGuardar.setOnClickListener {
            actualizarDispositivo()
        }
    }

    private fun cargarDatosDispositivo(codigo: String) {
        val dispositivo = databaseHelper.obtenerDispositivoPorCodigo(codigo)
        if (dispositivo != null) {
            editTextTipo.setText(dispositivo.tipo)
            editTextMarca.setText(dispositivo.marca)
            editTextModelo.setText(dispositivo.modelo)
            editTextNombre.setText(dispositivo.nombreEncargado)
            editTextCargo.setText(dispositivo.cargo)
            editTextArea.setText(dispositivo.area)
            editTextTipoMantenimiento.setText(dispositivo.tipoMantenimiento)
            editTextEstado.setText(dispositivo.estadoMantenimiento)
        } else {
            Toast.makeText(this, "Dispositivo no encontrado", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad si el dispositivo no existe
        }
    }

    private fun actualizarDispositivo() {
        val nuevoTipo = editTextTipo.text.toString().trim()
        val nuevaMarca = editTextMarca.text.toString().trim()
        val nuevoModelo = editTextModelo.text.toString().trim()
        val nuevoNombre = editTextNombre.text.toString().trim()
        val nuevoCargo = editTextCargo.text.toString().trim()
        val nuevaArea = editTextArea.text.toString().trim()
        val nuevoTipoMantenimiento = editTextTipoMantenimiento.text.toString().trim()
        val nuevoEstado = editTextEstado.text.toString().trim()

        if (nuevoTipo.isEmpty() || nuevaMarca.isEmpty() || nuevoModelo.isEmpty() ||
            nuevoNombre.isEmpty() || nuevoCargo.isEmpty() || nuevaArea.isEmpty() ||
            nuevoTipoMantenimiento.isEmpty() || nuevoEstado.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val resultado = databaseHelper.editarDispositivo(
            codigo, nuevoTipo, nuevaMarca, nuevoModelo,
            nuevoNombre, nuevoCargo, nuevaArea,
            nuevoTipoMantenimiento, nuevoEstado
        )

        if (resultado) {
            Toast.makeText(this, "Dispositivo actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y vuelve a la lista
        } else {
            Toast.makeText(this, "Error al actualizar el dispositivo", Toast.LENGTH_SHORT).show()
        }
    }
}
