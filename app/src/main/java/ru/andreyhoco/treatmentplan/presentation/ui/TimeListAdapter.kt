package ru.andreyhoco.treatmentplan.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

class TimeListAdapter(
    var items: MutableList<TimeOfIntake>,
    private val timeItemClickListener: TimeItemClickListener
): RecyclerView.Adapter<TimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        return TimeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_time, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(items[position], timeItemClickListener)
    }

    override fun getItemCount(): Int = items.size
}