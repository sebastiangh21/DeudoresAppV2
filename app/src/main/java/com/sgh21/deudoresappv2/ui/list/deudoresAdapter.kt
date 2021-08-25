package com.sgh21.deudoresappv2.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgh21.deudoresappv2.R
import com.sgh21.deudoresappv2.data.entities.Deudor
import com.sgh21.deudoresappv2.databinding.CardViewDeudoresItemBinding

class deudoresAdapter(
    private val onItemClicked: (Deudor) -> Unit,
) : RecyclerView.Adapter<deudoresAdapter.ViewHolder>() {

    private var listdeudor: MutableList<Deudor> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_deudores_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listdeudor[position])
        holder.itemView.setOnClickListener { onItemClicked(listdeudor[position]) }
    }

    override fun getItemCount(): Int {
        return listdeudor.size
    }
    fun appendItems(newItems: MutableList<Deudor>){
        listdeudor.clear()
        listdeudor.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = CardViewDeudoresItemBinding.bind(view)
        fun bind(deudor: Deudor){
            with(binding){
                nameTextView.text = deudor.name
                amountTextView.text = deudor.amount.toString()
            }

        }
    }
}