package com.costular.projectpartyqr.presentation.stats

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.costular.projectpartyqr.R
import com.costular.projectpartyqr.data.model.Stat
import kotlinx.android.synthetic.main.item_stat.view.*

class StatsAdapter : RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {

    private val items = mutableListOf<Stat>()

    fun submit(stats: List<Stat>) {
        this.items.clear()
        this.items.addAll(stats)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stat, parent, false)
        return StatsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class StatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(stat: Stat) {
            with(itemView) {
                imageIcon.setImageResource(stat.icon)
                imageIcon.setColorFilter(ContextCompat.getColor(context, R.color.icon))
                textKey.text = stat.key
                textValue.text = stat.value
            }
        }

    }
}