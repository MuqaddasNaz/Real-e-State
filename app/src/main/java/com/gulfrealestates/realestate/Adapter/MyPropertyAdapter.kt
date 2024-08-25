package com.gulfrealestates.realestate.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Fragments.Favorites.MyPropertiesFragment
import com.gulfrealestates.realestate.Models.PropertiesItem
import com.gulfrealestates.realestate.R

class MyPropertyAdapter(
    private val propertiesList: List<PropertiesItem>,
    private val myPropertiesFragment: MyPropertiesFragment
) : RecyclerView.Adapter<MyPropertyAdapter.PropertyViewHolder>() {

    inner class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAllType: TextView = itemView.findViewById(R.id.tv_all_type)
        val tvAllStatus: TextView = itemView.findViewById(R.id.tv_all_status)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city)
        val tvArea: TextView = itemView.findViewById(R.id.tv_area)
        val tvBedrooms: TextView = itemView.findViewById(R.id.tv_bedrooms)
        val tvBathrooms: TextView = itemView.findViewById(R.id.tv_bathrooms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.save_property_row, parent, false)
        return PropertyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val currentProperty = propertiesList[position]
        holder.tvAllStatus.text = currentProperty.allStatus
        holder.tvAllType.text = currentProperty.allType
        holder.tvCity.text = currentProperty.city
        holder.tvArea.text = currentProperty.area
        holder.tvBedrooms.text = currentProperty.bedRooms
        holder.tvBathrooms.text = currentProperty.bathRooms
    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

}
