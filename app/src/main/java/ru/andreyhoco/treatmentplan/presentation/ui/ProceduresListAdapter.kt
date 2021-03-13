package ru.andreyhoco.treatmentplan.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R

class ProceduresListAdapter(var items: MutableList<ProcedureListItem>):
    RecyclerView.Adapter<ProcedureItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureItemViewHolder {
        return if (viewType == ProcedureListItem.TYPE_GROUP) {
            val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_procedure_group, parent, false)

            ProcedureGroupViewHolder(itemView)
        } else {
            val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_procedure, parent, false)

            ProcedureViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: ProcedureItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].itemType
    }

    override fun getItemCount(): Int = items.size
}