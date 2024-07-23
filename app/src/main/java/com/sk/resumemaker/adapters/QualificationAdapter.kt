package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.QualificationClicked
import com.sk.resumemaker.entities.QualificationEntity
import com.sk.resumemaker.R

class QualificationAdapter(var qualificationEntity: ArrayList<QualificationEntity>, var qualificationClicked: QualificationClicked):RecyclerView.Adapter<QualificationAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var tvDeg : TextView = view.findViewById(R.id.tvDeg)
        var tvUni : TextView = view.findViewById(R.id.tvUni)
        var tvScore : TextView = view.findViewById(R.id.tvScore)
        var tvYear : TextView = view.findViewById(R.id.tvYear)
        var ivDelete : ImageView = view.findViewById(R.id.ivDelete)
        var ivEdit : ImageView = view.findViewById(R.id.ivEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_qualification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDeg.setText(qualificationEntity[position].course)
        holder.tvUni.setText(qualificationEntity[position].school)
        holder.tvScore.setText(qualificationEntity[position].grade)
        holder.tvYear.setText(qualificationEntity[position].year)
        holder.ivDelete.setOnClickListener {
            qualificationClicked.OnDeleteQualificationClicked(qualificationEntity[position], position)
        }
        holder.ivEdit.setOnClickListener {
            qualificationClicked.OnEditQualificationClicked(qualificationEntity[position], position)
        }
        
    }

    override fun getItemCount(): Int {
        return qualificationEntity.size
    }
}