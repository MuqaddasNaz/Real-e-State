package com.gulfrealestates.realestate.Adapter
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gulfrealestates.realestate.Activities.Main.AgenciesActivity
import com.gulfrealestates.realestate.Models.Register
import com.gulfrealestates.realestate.R

class AgencyAdapter(private val agencyList: List<Register>, agenciesActivity: AgenciesActivity) :
    RecyclerView.Adapter<AgencyAdapter.AgencyViewHolder>() {


    // Other adapter methods
    inner class AgencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAgencyName: TextView = itemView.findViewById(R.id.tv_agency_name)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvUserEmail: TextView = itemView.findViewById(R.id.tv_userEmail)
        val tvUserPhoneNumber: TextView = itemView.findViewById(R.id.tv_userPhoneNumber)
        val tvAddress: TextView = itemView.findViewById(R.id.tv_address)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.agency_row, parent, false)
        return AgencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AgencyViewHolder, position: Int) {
        val currentItem = agencyList[position]
        holder.tvAgencyName.text = currentItem.agencyName
        holder.tvUserName.text = currentItem.userName
        holder.tvUserEmail.text = currentItem.userEmail
        holder.tvUserPhoneNumber.text = currentItem.phoneNumber
        holder.tvAddress.text = currentItem.address

        // Set click listener for user email
        holder.tvUserEmail.setOnClickListener {
            val email = currentItem.userEmail
            // Perform action to open email client
            // For example:
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$email")
            holder.itemView.context.startActivity(emailIntent)
        }

        // Set click listener for user phone number
        holder.tvUserPhoneNumber.setOnClickListener {
            val phoneNumber = currentItem.phoneNumber
            // Perform action to initiate phone call
            // For example:
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:$phoneNumber")
            holder.itemView.context.startActivity(phoneIntent)
        }
    }

    override fun getItemCount(): Int {
        return agencyList.size
    }
}



