package ru.andreyhoco.treatmentplan.presentation.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake
import java.lang.String

class TimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val ibDelete: ImageButton = itemView.findViewById(R.id.ib_delete)
    private val tvTime: TextView = itemView.findViewById(R.id.tv_time)

    fun bind(timeOfIntake: TimeOfIntake, timeItemClickListener: TimeItemClickListener) {
        tvTime.text = "${String.format("hh:mm", timeOfIntake.timeOfTakes)}"

        ibDelete.setOnClickListener { timeItemClickListener.onDeleteItemClick(timeOfIntake) }
    }
}