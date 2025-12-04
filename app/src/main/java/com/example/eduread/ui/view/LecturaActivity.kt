package com.example.eduread.ui.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.eduread.R
import com.example.eduread.utils.ColorExtractor

class LecturaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            enableEdgeToEdge()
            setContentView(R.layout.activity_lectura)

            // Configurar Toolbar
            val toolbar = findViewById<Toolbar>(R.id.toolbar2)
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { finish() }

            // Obtener datos enviados desde CuentosActivity
            val cardTitle = intent.getStringExtra("card_title") ?: "Título no encontrado"
            val cardText = intent.getStringExtra("card_text") ?: "Texto no encontrado"
            val cardImageName = intent.getStringExtra("card_image") ?: "img0.jpg"

            // Configurar contenido
            findViewById<TextView>(R.id.detail_title).text = cardTitle
            findViewById<TextView>(R.id.detail_text).text = cardText

            // Configurar la imagen
            val imageView = findViewById<ImageView>(R.id.detail_image)
            val context = this
            val imageResId = context.resources.getIdentifier(cardImageName.substringBeforeLast("."), "drawable", context.packageName)
            if (imageResId != 0) {
                imageView.setImageResource(imageResId)
            } else {
                imageView.setImageResource(R.drawable.img2)
            }
            ColorExtractor.extractColors(imageView) { colors ->
                toolbar.setBackgroundColor(colors.darkVibrantColor)
                window.statusBarColor = colors.darkVibrantColor
                findViewById<View>(R.id.main).setBackgroundColor(colors.softDarkVibrantColor)
                findViewById<TextView>(R.id.detail_title).setTextColor(colors.dominantColor)
                val gradientView = findViewById<View>(R.id.gradient_overlay)
                val gradientDrawable = gradientView.background as GradientDrawable
                gradientDrawable.setColors(
                    intArrayOf(colors.dominantColor, Color.TRANSPARENT)
                )
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                    window.insetsController?.setSystemBarsAppearance(
                        0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    window.decorView.systemUiVisibility =
                        window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val btnPregunta: Button = findViewById(R.id.buttonPregunta)
        btnPregunta.setOnClickListener {
            val cuentoId = intent.getIntExtra("cuento_id", -1)
            val userId = intent.getIntExtra("usuario_id", -1)

            val intent = Intent(this, PreguntasActivity::class.java).apply {
                putExtra("cuento_id", cuentoId)
                putExtra("usuario_id", userId)
            }

            startActivity(intent)
        }
    }
}
