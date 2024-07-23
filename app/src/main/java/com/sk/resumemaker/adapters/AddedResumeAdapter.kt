package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.R
import com.sk.resumemaker.ResumeListClicked
import com.sk.resumemaker.SkillClicked
import com.sk.resumemaker.entities.DisplayResumes
import com.sk.resumemaker.entities.SkillEntity

class AddedResumeAdapter (var displayResumes: ArrayList<DisplayResumes>, var skillClicked: ResumeListClicked): RecyclerView.Adapter<AddedResumeAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view){
        var tvName: TextView = view.findViewById(R.id.tvName)
        var tvphone: TextView = view.findViewById(R.id.tvphone)
        var tvAddress: TextView = view.findViewById(R.id.tvAddress)
        var ivDelete : ImageView = view.findViewById(R.id.ivDelete)
        var ivPrint : ImageView = view.findViewById(R.id.ivPrint)
        var ivView : ImageView = view.findViewById(R.id.ivView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_added_resume,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var resume = displayResumes[position]
        holder.tvName.setText(resume.personalInfoEntity?.firstName+" "+resume.personalInfoEntity?.lastName)
        holder.tvAddress.setText(resume.personalInfoEntity?.address)
        holder.tvphone.setText(resume.personalInfoEntity?.phone)
        holder.ivDelete.setOnClickListener {
            skillClicked.OnDeleteClicked(resume, position)
        }
        holder.ivView.setOnClickListener {
            skillClicked.OnViewClicked(resume, position)
        }
        holder.ivPrint.setOnClickListener {
            skillClicked.OnPrintClicked(resume, position)
        }
    }

    override fun getItemCount(): Int {
        return displayResumes.size
    }

}