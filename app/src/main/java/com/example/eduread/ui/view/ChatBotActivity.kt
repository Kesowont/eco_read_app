package com.example.eduread.ui.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eduread.R
import com.example.eduread.data.repository.ChatBotRepository

class ChatBotActivity : AppCompatActivity() {

    private val repo = ChatBotRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat_bot)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom)
            insets
        }

        val layoutMensajes = findViewById<LinearLayout>(R.id.layoutMensajes)
        val etMensaje = findViewById<EditText>(R.id.etMensaje)
        val btnEnviar = findViewById<ImageButton>(R.id.btnEnviar)
        val scrollView = findViewById<android.widget.ScrollView>(R.id.scrollChat)

        btnEnviar.setOnClickListener {
            val mensaje = etMensaje.text.toString()

            if (mensaje.isNotBlank()) {

                // Agregar mensaje del usuario
                agregarMensaje(mensaje, esBot = false)

                // Enviar al repositorio
                repo.enviarMensaje(mensaje) { respuesta ->
                    runOnUiThread {
                        val textoBot = respuesta ?: "[Error desconocido]"
                        agregarMensaje(textoBot, esBot = true)
                    }
                }

                etMensaje.setText("")
            }
        }
    }

    /** Agrega un mensaje al layout usando items personalizados */
    private fun agregarMensaje(texto: String, esBot: Boolean) {
        val layoutMensajes = findViewById<LinearLayout>(R.id.layoutMensajes)
        val scrollView = findViewById<android.widget.ScrollView>(R.id.scrollChat)

        // Seleccionar diseño según tipo
        val view = layoutInflater.inflate(
            if (esBot) R.layout.item_msg_bot else R.layout.item_msg_user,
            null
        )

        // Colocar texto
        view.findViewById<TextView>(R.id.tvTexto).text = texto

        // Insertar en el layout principal
        layoutMensajes.addView(view)

        // Hacer scroll al final
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}
