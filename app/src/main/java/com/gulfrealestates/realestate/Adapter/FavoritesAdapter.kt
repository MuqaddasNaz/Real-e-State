package com.gulfrealestates.realestate.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gulfrealestates.realestate.Models.HomeItem
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R

class FavoritesAdapter(private val context: Context, private val favoriteItemList: ArrayList<Property>) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    fun addItem(item: Property) {
        favoriteItemList.add(item)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorites_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteItem = favoriteItemList[position]
        holder.textViewTitle.text = favoriteItem.title
        holder.tvSaleType.text = favoriteItem.propertyType
        holder.tvPrice.text = favoriteItem.totalPrice
        holder.tvBedCount.text = favoriteItem.bedrooms
        holder.tvRange.text = favoriteItem.areaSize
        holder.tvShowerCount.text = favoriteItem.bathrooms

        Glide.with(context).load(favoriteItem.imgUrl).into(holder.image)

    }


    override fun getItemCount(): Int {
        return favoriteItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvSaleType: TextView = itemView.findViewById(R.id.tv_sale_type)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvBedCount: TextView = itemView.findViewById(R.id.tv_bedCount)
        val tvRange: TextView = itemView.findViewById(R.id.tv_range)
        val tvShowerCount: TextView = itemView.findViewById(R.id.tv_showerCount)
        val image: ImageView = itemView.findViewById(R.id.iv_img)
    }
}
