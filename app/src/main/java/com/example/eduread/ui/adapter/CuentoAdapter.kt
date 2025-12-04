package com.example.eduread.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eduread.R
import com.example.eduread.data.model.Cuento

class CardAdapter(
    private val cuentos: List<Cuento>,
    private val listener: OnCardClickListener
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val starsContainer: ViewGroup = itemView.findViewById(R.id.item_stars)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cuento = cuentos[position]
        val context: Context = holder.itemView.context
        val imageName = cuento.image
        val imageNameWithoutExtension = imageName.substringBeforeLast(".")
        val resourceName = "img${cuento.image}"
        val imageResId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId)
        } else {
            holder.imageView.setImageResource(R.drawable.img2)
        }
        holder.titleTextView.text = cuento.title
        val starsCount = cuento.estrella
        for (i in 0 until holder.starsContainer.childCount) {
            val starView = holder.starsContainer.getChildAt(i) as ImageView
            if (i < starsCount) {
                starView.setImageResource(R.drawable.star_icon)
            } else {
                starView.setImageResource(R.drawable.star_icon_gray)
            }
        }
        holder.itemView.setOnClickListener {
            /*Log.d("CardAdapter", "Clic en el cuento: ${card.title}")
            listener.onCardClick(card)*/
            /*val context = holder.itemView.context // Obtenemos el contexto desde el holder
            val intent = Intent(context, CuentosActivity::class.java).apply {
                putExtra("card_title", card.title)
                putExtra("card_text", card.text) // Pasar el texto del card
                putExtra("card_image", card.image01)
                putExtra("cuento_id", card.idLib)
            }
            context.startActivity(intent)*/
            listener.onCardClick(cuento)//PROHIBIDO BORRAR ESTO FUNCIONA--------------
        }

    }

    override fun getItemCount(): Int = cuentos.size
    //override fun getItemCount(): Int = cardList.size
}

interface OnCardClickListener {
    fun onCardClick(card: Cuento)
}
