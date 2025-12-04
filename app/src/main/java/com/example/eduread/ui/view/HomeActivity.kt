package com.example.eduread.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eduread.MainActivity
import com.example.eduread.R
import com.example.eduread.ui.estatico.UsuarioStatic

class HomeActivity : AppCompatActivity() {
    private var userId: Int = -1//Agregué
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar datos del intent
        val nombre = intent.getStringExtra("nombre") ?: "Usuario"
        val edad = intent.getIntExtra("edad", 0)

        val textViewNombre: TextView = findViewById(R.id.textViewNombre)
        val textViewEdad: TextView = findViewById(R.id.textViewEdad)

        textViewNombre.text = UsuarioStatic.nombre_static
        textViewEdad.text = "Edad: ${UsuarioStatic.edad_static} años"


        userId = intent.getIntExtra("usuario_id", -1)//Agregué

        val btnCuentos: Button = findViewById(R.id.buttonCuentos)
        btnCuentos.setOnClickListener(){
            val intent: Intent = Intent(this, CuentosActivity:: class.java)//agregué cambiar de intent 2 a intent
            intent.putExtra("usuario_id", userId)//Agregué
            startActivity(intent)//intent2 ->intent
        }

        val btnChatBot: Button = findViewById(R.id.buttonChatBot)
        btnChatBot.setOnClickListener {
            val intent = Intent(this, ChatBotActivity::class.java)
            startActivity(intent)
        }

        val btnCerrarSeccion: Button = findViewById(R.id.buttonCerrarSesion)
        btnCerrarSeccion.setOnClickListener(){
            val intent3 : Intent = Intent(this,MainActivity:: class.java)
            startActivity((intent3))
        }


    }
}