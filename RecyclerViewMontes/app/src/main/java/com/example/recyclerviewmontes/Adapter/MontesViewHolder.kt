package com.example.recyclerviewmontes.Adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewmontes.Montes
import com.example.recyclerviewmontes.databinding.ItemMontesBinding

class MontesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemMontesBinding.bind(view)

    fun render(
        montesModel: Montes,
        onClickListener: (Montes) -> Unit,
        onClickDelete: (Int) -> Unit
    ) {
        binding.tvNombreMonte.text = montesModel.nombre
        binding.tvContinente.text = montesModel.continente
        binding.tvAltura.text = montesModel.altura.toString()

        Glide.with(binding.ivImagen.context).load(montesModel.foto).into(binding.ivImagen)

        itemView.setOnClickListener { onClickListener(montesModel) }
        binding.btnBorrar.setOnClickListener { onClickDelete(adapterPosition) }

        // Listener del botón "Ver mas"
        binding.btnVerMas.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(montesModel.urlinfo)
            if (itemView.context.packageManager.resolveActivity(intent, 0) != null) {
                itemView.context.startActivity(intent) // Abre el navegador
            }
        }
    }
}
