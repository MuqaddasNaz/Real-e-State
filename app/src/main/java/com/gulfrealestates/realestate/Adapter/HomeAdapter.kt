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
import com.gulfrealestates.realestate.Models.AddProperty
import com.gulfrealestates.realestate.Models.Property
import com.gulfrealestates.realestate.Utills.SharedPref
import com.example.realestates.Utills.Constants
import com.gulfrealestates.realestate.Activities.Main.PropertyDetailActivity
import com.gulfrealestates.realestate.R

class HomeAdapter(var context: Context, var propertyList: MutableList<Property>, private val listener: HomeAdapterListener) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    interface HomeAdapterListener {
        fun onLikeButtonClick(view: View, position: Int)
        fun HomeAdapter1(context: MutableList<AddProperty>): HomeAdapter1
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
        holder.bind(item,context)
        holder.likeButton.setOnClickListener {
            listener.onLikeButtonClick(holder.itemView, position)
        }
    }

    fun submitList(filteredList: MutableList<Property>) {
        // Filtered list ko adapter ke saith submit karna
        this.propertyList = filteredList as ArrayList<Property>
        notifyDataSetChanged() // Notify adapter about the data change
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivProperty: ImageView = itemView.findViewById(R.id.iv_img)
        val likeButton: ImageButton = itemView.findViewById(R.id.btn_like)
        val tvSale: TextView = itemView.findViewById(R.id.tv_sale)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvBedCount: TextView = itemView.findViewById(R.id.tv_bed)
        val tvShowerCount: TextView = itemView.findViewById(R.id.tv_shower)
        val ivImg: ImageView = itemView.findViewById(R.id.iv_img)
        val btnReview: ImageButton = itemView.findViewById(R.id.btn_review)

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

            // Bind item data to views
//            if (item.isLiked) {
//                likeButton.setImageResource(R.drawable.ic_like2)
//            } else {
//                likeButton.setImageResource(R.drawable.ic_like1)
//            }

            tvPrice.text = item.totalPrice
            tvBedCount.text = item.bedrooms
            tvShowerCount.text = item.bathrooms

            tvBedCount.text = item.bedrooms

            Glide.with(context).load(item.imgUrl).into(ivProperty)

            ivImg.setOnClickListener {

                context.startActivity(Intent(context, PropertyDetailActivity::class.java)
                    .putExtra(Constants.propertDetail,item))

            }

            btnReview.setOnClickListener {

                context.startActivity(Intent(context, PropertyDetailActivity::class.java)
                    .putExtra(Constants.propertDetail,item))

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
