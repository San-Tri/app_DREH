package com.example.miprimeraapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // guardando los id de los componentes en variables
        val btnNuevo = findViewById<Button>(R.id.btn_nuevo)
        val btnPendiente = findViewById<Button>(R.id.btn_pendiente)
        val btnReporte = findViewById<Button>(R.id.btn_generar_reporte)

        btnNuevo.setOnClickListener{
            val intent = Intent(this, DispositivoActivity::class.java) // //para ir a la pantalla de registrar mantenimiento
            startActivity(intent)
        }
    }
}
