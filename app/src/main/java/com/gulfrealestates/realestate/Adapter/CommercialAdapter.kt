package com.gulfrealestates.realestate.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.realestates.Utills.Constants
import com.gulfrealestates.realestate.Activities.Main.PropertyDetailActivity
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.R
import com.gulfrealestates.realestate.Utills.SharedPref

class CommercialAdapter(
    var context: Context,
    var propertyList: MutableList<Property>,
    private val listener: CommercialAdapterListener
) : RecyclerView.Adapter<CommercialAdapter.ViewHolder>() {

    interface CommercialAdapterListener {
        fun onLikeButtonClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = propertyList[position]
        holder.bind(item, context)
        holder.likeButton.setOnClickListener {
            listener.onLikeButtonClick(holder.itemView, position)
        }
    }

    fun submitList(filteredList: MutableList<Property>) {
        propertyList = filteredList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivProperty: ImageView = itemView.findViewById(R.id.iv_img)
        val likeButton: ImageButton = itemView.findViewById(R.id.btn_like)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvBedCount: TextView = itemView.findViewById(R.id.tv_bed)
        val tvShowerCount: TextView = itemView.findViewById(R.id.tv_shower)

        init {
            likeButton.setOnClickListener(this)
        }

        fun bind(item: Property, context: Context) {

            var sh = SharedPref(context)

            val favList = sh.getFavoritesList()

            val findItem = favList?.find { it.id == item.id }

            if (findItem!=null){

                likeButton.setImageResource(R.drawable.ic_like2)

            }else{

                likeButton.setImageResource(R.drawable.ic_like1)

            }


            tvPrice.text = item.totalPrice
            tvBedCount.text = item.bedrooms
            tvShowerCount.text = item.bathrooms

            Glide.with(context).load(item.imgUrl).into(ivProperty)

            itemView.setOnClickListener {
                context.startActivity(Intent(context, PropertyDetailActivity::class.java).apply {
                    putExtra(Constants.propertDetail, item)
                })
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onLikeButtonClick(view, position)
            }
        }
    }
}
