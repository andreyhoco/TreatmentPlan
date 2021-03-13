package ru.andreyhoco.treatmentplan.presentation.ui

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R
import java.lang.String.format
import java.text.DateFormat

abstract class ProcedureItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: ProcedureListItem, procedureItemClickListener: ProcedureItemClickListener)
}

class ProcedureGroupViewHolder(itemView: View): ProcedureItemViewHolder(itemView) {
    private val tvGroupHeader: TextView = itemView.findViewById(R.id.tv_group_header)

    override fun bind(item: ProcedureListItem, procedureItemClickListener: ProcedureItemClickListener) {
        tvGroupHeader.text =
            if (item is ProcedureGroupItem) {
                "${format("hh:mm", item.procedureTimeGroup.startTime)} - ${format("hh:mm", item.procedureTimeGroup.endTime)}"
            } else {
                ""
            }
    }
}

class ProcedureViewHolder(itemView: View): ProcedureItemViewHolder(itemView) {
    // TODO: fill the icon of procedure
    private val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
    private val cbDone: CheckBox = itemView.findViewById(R.id.cb_done)
    private val tvPerson: TextView = itemView.findViewById(R.id.tv_person)
    private val tvProcedure: TextView = itemView.findViewById(R.id.tv_procedure)

    override fun bind(item: ProcedureListItem, procedureItemClickListener: ProcedureItemClickListener) {
        if (item is ProcedureItem) {
            cbDone.isChecked = item.timeOfIntake.isDone
            tvPerson.text = "${format("hh:mm", item.timeOfIntake.timeOfTakes)}: ${item.procedure.person.name}"
            tvProcedure.text = "${item.procedure.title}"

            itemView.setOnClickListener {
                procedureItemClickListener.onItemClick(item.procedure)
            }
            cbDone.setOnClickListener {
                procedureItemClickListener.onItemCheckBoxClick(item.procedure, item.timeOfIntake)
            }
        } else {
            cbDone.isChecked = false
            tvPerson.text = ""
            tvProcedure.text = ""
        }
    }
}
