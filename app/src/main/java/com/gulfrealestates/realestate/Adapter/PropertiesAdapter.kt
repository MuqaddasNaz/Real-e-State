package com.gulfrealestates.realestate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulfrealestates.realestate.Models.PropertyItem
import com.gulfrealestates.realestate.R

class PropertiesAdapter(private val propertyList: ArrayList<PropertyItem>) : RecyclerView.Adapter<PropertiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = propertyList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.tv_ModernApartment)
        private val textViewName: TextView = itemView.findViewById(R.id.tv_name1)
        private val textViewLocation: TextView = itemView.findViewById(R.id.tv_location)

        fun bind(item: PropertyItem) {
            textViewItem.text = item.modernApartment
            textViewName.text = item.name1
            textViewLocation.text = item.location
        }
    }
}
