package com.example.recyclerviewmontes.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recyclerviewmontes.Montes
import com.example.recyclerviewmontes.R

class MontesAdapter(
    private val montesList: MutableList<Montes>,
    private val onClickListener: (Montes) -> Unit,
    private val onClickDelete: (Int) -> Unit
) : androidx.recyclerview.widget.RecyclerView.Adapter<MontesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MontesViewHolder(layoutInflater.inflate(R.layout.item_montes, parent, false))
    }

    override fun onBindViewHolder(holder: MontesViewHolder, position: Int) {
        val item = montesList[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    override fun getItemCount(): Int = montesList.size

    fun filterByName(newList: MutableList<Montes>) {
        montesList.clear()
        montesList.addAll(newList)
        notifyDataSetChanged()
    }

}
