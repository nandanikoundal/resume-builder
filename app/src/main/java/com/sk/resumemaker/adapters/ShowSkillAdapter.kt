package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.R
import com.sk.resumemaker.SkillClicked
import com.sk.resumemaker.entities.SkillEntity

class ShowSkillAdapter (var skillEntity: ArrayList<SkillEntity>): RecyclerView.Adapter<ShowSkillAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvskillname: TextView = view.findViewById(R.id.tvskillname)
        var tvskilldesc: TextView = view.findViewById(R.id.tvskilldesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvskillname.setText(skillEntity[position].skillname)
        holder.tvskilldesc.setText(skillEntity[position].description)
    }

    override fun getItemCount(): Int {
        return skillEntity.size
    }
}
