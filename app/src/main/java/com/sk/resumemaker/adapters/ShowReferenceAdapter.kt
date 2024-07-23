package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.R
import com.sk.resumemaker.ReferenceClicked
import com.sk.resumemaker.entities.ReferenceEntity

class ShowReferenceAdapter (var referenceEntity: ArrayList<ReferenceEntity>): RecyclerView.Adapter<ReferenceAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var tvRefname: TextView = view.findViewById(R.id.tvRefname)
        var tvJob: TextView = view.findViewById(R.id.tvJob)
        var tvCompName: TextView = view.findViewById(R.id.tvCompName)
        var tvEmail: TextView = view.findViewById(R.id.tvEmail)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenceAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_reference,parent,false)
        return ReferenceAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReferenceAdapter.ViewHolder, position: Int) {
        holder.tvRefname.setText(referenceEntity[position].referee)
        holder.tvJob.setText(referenceEntity[position].job)
        holder.tvCompName.setText(referenceEntity[position].company)
        holder.tvEmail.setText(referenceEntity[position].email)
    }
    override fun getItemCount(): Int {
        return referenceEntity.size

    }

}