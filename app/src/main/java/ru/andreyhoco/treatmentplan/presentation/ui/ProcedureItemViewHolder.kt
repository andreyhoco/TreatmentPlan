package ru.andreyhoco.treatmentplan.presentation.ui

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R

abstract class ProcedureItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ProcedureListItem)
}

class ProcedureGroupViewHolder(itemView: View): ProcedureItemViewHolder(itemView) {
    private val tvGroupHeader: ImageView = itemView.findViewById(R.id.tv_group_header)

    override fun bind(item: ProcedureListItem) {

    }
}

class ProcedureViewHolder(itemView: View): ProcedureItemViewHolder(itemView) {
    private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
    private val cbDone: CheckBox = itemView.findViewById(R.id.cb_done)
    private val tvPerson: ImageView = itemView.findViewById(R.id.tv_person)
    private val tvProcedure: ImageView = itemView.findViewById(R.id.tv_procedure)

    override fun bind(item: ProcedureListItem) {

    }
}
