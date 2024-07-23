package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.R
import com.sk.resumemaker.ReferenceClicked
import com.sk.resumemaker.entities.ReferenceEntity

class ReferenceAdapter(var referenceEntity: ArrayList<ReferenceEntity>,var referenceClicked: ReferenceClicked):RecyclerView.Adapter<ReferenceAdapter.ViewHolder>() {
    class ViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        var tvRefname: TextView = view.findViewById(R.id.tvRefname)
        var tvJob: TextView = view.findViewById(R.id.tvJob)
        var tvCompName: TextView = view.findViewById(R.id.tvCompName)
        var tvEmail: TextView = view.findViewById(R.id.tvEmail)
        /*var ivDelete : ImageView = view.findViewById(R.id.ivDelete)
        var ivEdit : ImageView = view.findViewById(R.id.ivEdit)*/
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_reference,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.tvRefname.setText(referenceEntity[position].referee)
        holder.tvJob.setText(referenceEntity[position].job)
        holder.tvCompName.setText(referenceEntity[position].company)
        holder.tvEmail.setText(referenceEntity[position].email)
        /*holder.ivDelete.setOnClickListener {
            referenceClicked.OnDeleteReferenceClicked(referenceEntity[position], position)
        }
        holder.ivEdit.setOnClickListener {
            referenceClicked.OnEditReferenceClicked(referenceEntity[position], position)
        }*/
    }

    override fun getItemCount(): Int {
        return referenceEntity.size

    }
}