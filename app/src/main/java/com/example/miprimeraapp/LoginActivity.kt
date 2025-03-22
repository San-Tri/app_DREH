package com.example.miprimeraapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class LoginActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // guardando los id de los componentes en variables
        val dni = findViewById<EditText>(R.id.et_dni)
        val contraseña = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val usuario = dni.text.toString()
            val pass = contraseña.text.toString()

            if (usuario.isNotEmpty() && pass.isNotEmpty()) {
                // Lógica de autenticación falta agregar
                Toast.makeText(this,"Iniciando sesión...", Toast.LENGTH_SHORT).show()
                // Si la autentificacion es exitosa, pasamos a la siguiente pantalla
                val intent = Intent(this, RegistroActivity::class.java) // //para ir a la pantalla de registro
                startActivity(intent)
            } else {
                Toast.makeText(this, "Ingrese sus datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
