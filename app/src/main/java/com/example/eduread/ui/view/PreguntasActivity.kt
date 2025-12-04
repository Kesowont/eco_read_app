package com.example.eduread.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduread.MainActivity
import com.example.eduread.R
import com.example.eduread.data.model.Pregunta
import com.example.eduread.ui.adapter.PreguntasAdapter
import com.example.eduread.data.repository.CuentoRepository
import com.example.eduread.data.repository.PreguntasRepository

class PreguntasActivity : AppCompatActivity() {
    private val preguntasRepository = PreguntasRepository()
    private lateinit var preguntasAdapter: PreguntasAdapter

    private val respuestasSeleccionadas = mutableMapOf<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)

        // Agregué
        val toolbar = findViewById<Toolbar>(R.id.toolbarPreguntas)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            finish() // Retrocede a la actividad anterior (LecturaActivity)
        }

        // Agregué
        val helpButton: ImageButton = findViewById(R.id.help_icon)
        helpButton.setOnClickListener {
            Toast.makeText(this, "Aquí puedes responder las preguntas seleccionando Sí o No.", Toast.LENGTH_SHORT).show()
        }

        val cuentoId = intent.getIntExtra("cuento_id", -1)
        cargarPreguntas(cuentoId)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            guardarRespuestas(cuentoId)
        }
    }
    private fun cargarPreguntas(cuentoId: Int) {
        preguntasRepository.obtenerPreguntas(cuentoId) { preguntas ->
            if (preguntas != null) {
                Log.d("PreguntasActivity", "Preguntas obtenidas: $preguntas")
                inicializarRecyclerView(preguntas)
            } else {
                Toast.makeText(this, "No se pudieron cargar las preguntas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inicializarRecyclerView(preguntas: List<Pregunta>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPreguntas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        preguntasAdapter = PreguntasAdapter(preguntas) { idPregunta, respuesta ->
            respuestasSeleccionadas[idPregunta] = respuesta
            Log.d("PreguntasActivity", "Pregunta $idPregunta respuesta seleccionada: $respuesta")
        }
        recyclerView.adapter = preguntasAdapter
    }
    private fun guardarRespuestas(idCuento: Int) {
        if (respuestasSeleccionadas.size < preguntasAdapter.itemCount) {
            Toast.makeText(this, "Responde todas las preguntas antes de guardar.", Toast.LENGTH_SHORT).show()
            return
        }

        val arrayRespuestas = respuestasSeleccionadas.values.joinToString(",")
        val userId = intent.getIntExtra("usuario_id", -1)//Agregué
        Log.d("PreguntasActivity", "Recibido → cuento_id=$idCuento, usuario_id=$userId, respuestas=$arrayRespuestas")

        // Llamar al repositorio para enviar las respuestas
        val cuentoRepository = CuentoRepository()
        cuentoRepository.reponderPreguntasCuento(
            id_usuario = userId,//Modifiqué
            id_cuento = idCuento,
            array_respuestas = arrayRespuestas
        ) { response ->
            if (response?.status == 200) {
                Toast.makeText(this, "Respuestas enviadas con éxito.", Toast.LENGTH_SHORT).show()
                Log.d("PreguntasActivity", "Respuesta del servidor: ${response}")
                val intent10 : Intent = Intent(this, CuentosActivity:: class.java)
                startActivity((intent10))
            } else {
                Toast.makeText(this, "Error al enviar las respuestas. ${response?.status}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}