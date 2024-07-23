package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.R
import com.sk.resumemaker.SkillClicked
import com.sk.resumemaker.entities.SkillEntity

class SkillAdapter (var skillEntity: ArrayList<SkillEntity>,var skillClicked: SkillClicked): RecyclerView.Adapter<SkillAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view){
        var tvskillname: TextView = view.findViewById(R.id.tvskillname)
        var tvskilldesc: TextView = view.findViewById(R.id.tvskilldesc)
        /*var ivDelete : ImageView = view.findViewById(R.id.ivDelete)
        var ivEdit : ImageView = view.findViewById(R.id.ivEdit)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvskillname.setText(skillEntity[position].skillname)
        holder.tvskilldesc.setText(skillEntity[position].description)
        /*holder.ivDelete.setOnClickListener {
            skillClicked.OnDeleteSkillClicked(skillEntity[position], position)
        }
        holder.ivEdit.setOnClickListener {
            skillClicked.OnEditSkillClicked(skillEntity[position], position)
        }*/
    }

    override fun getItemCount(): Int {
        return skillEntity.size
    }

}
