package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.ExperienceClicked
import com.sk.resumemaker.QualificationClicked
import com.sk.resumemaker.entities.ExperienceEntity
import com.sk.resumemaker.R

class ExperienceAdapter(var experienceEntity: ArrayList<ExperienceEntity>, var experienceClicked: ExperienceClicked):RecyclerView.Adapter<ExperienceAdapter.ViewHolder>(){
    class ViewHolder(var view: View):RecyclerView.ViewHolder(view){
        var tv2jobtitle : TextView = view.findViewById(R.id.tv2jobtitle)
        var tv2companyname : TextView = view.findViewById(R.id.tv2companyname)
        var tv2countryname : TextView = view.findViewById(R.id.tv2countryname)
        var tv2details : TextView = view.findViewById(R.id.tv2details)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_experience,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv2jobtitle.setText(experienceEntity[position].jobtitle)
        holder.tv2companyname.setText(experienceEntity[position].companyname)
        holder.tv2countryname.setText(experienceEntity[position].countryname)
        holder.tv2details.setText(experienceEntity[position].details)
        /*holder.ivDelete.setOnClickListener {
            experienceClicked.OnDeleteExperienceClicked(experienceEntity[position], position)
        }
        holder.ivEdit.setOnClickListener {
            experienceClicked.OnEditExperienceClicked(experienceEntity[position], position)
        }*/

    }

    override fun getItemCount(): Int {
        return experienceEntity.size

    }



}