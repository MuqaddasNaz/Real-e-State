package com.gulfrealestates.realestate.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulfrealestates.realestate.Models.AddProperty
import com.gulfrealestates.realestate.R


class HomeAdapter1(private val context: Context, private val addPropertyItemList: MutableList<AddProperty>) : RecyclerView.Adapter<HomeAdapter1.ViewHolder>() {

    fun addItem(item: AddProperty) {
        addPropertyItemList.add(item)
        notifyItemInserted(addPropertyItemList.size - 1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_row1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteItem = addPropertyItemList[position]
        holder.textViewTitle.text = favoriteItem.saleType
        holder.tvSaleType.text = favoriteItem.saleType
        holder.tvBedCount.text = favoriteItem.bedCount
        holder.tvRange.text = favoriteItem.range
        holder.tvShowerCount.text = favoriteItem.showerCount
        holder.tvContactNumber.text = favoriteItem.contactNumber
        holder.image.setImageResource(favoriteItem.imageResource)
    }


    override fun getItemCount(): Int {
        return addPropertyItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvSaleType: TextView = itemView.findViewById(R.id.tv_sale_type)
        val tvBedCount: TextView = itemView.findViewById(R.id.tv_bedCount)
        val tvRange: TextView = itemView.findViewById(R.id.tv_range)
        val tvShowerCount: TextView = itemView.findViewById(R.id.tv_showerCount)
        val tvContactNumber: TextView = itemView.findViewById(R.id.tv_showerCount)
        val image: ImageView = itemView.findViewById(R.id.iv_img)
    }

    companion object {
        fun createAdapter(context: Context, addPropertyList: MutableList<AddProperty>): HomeAdapter1 {
            return HomeAdapter1(context, addPropertyList)
        }
    }

}

