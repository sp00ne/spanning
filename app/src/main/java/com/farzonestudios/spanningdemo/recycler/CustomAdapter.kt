package com.farzonestudios.spanningdemo.recycler

import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farzonestudios.spanningdemo.R

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val dataSet = mutableListOf<SpannableString>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)

        init {
            // Needed for ClickableSpan to work
            textView.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_text, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position]
    }

    fun updateData(newData: List<SpannableString>) {
        dataSet.clear()
        dataSet.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size
}