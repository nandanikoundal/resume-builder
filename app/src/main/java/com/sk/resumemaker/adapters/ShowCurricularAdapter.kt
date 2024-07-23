package com.sk.resumemaker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sk.resumemaker.CurricularClicked
import com.sk.resumemaker.R
import com.sk.resumemaker.entities.CurricularEntity

class ShowCurricularAdapter (var curricularEntity: ArrayList<CurricularEntity>): RecyclerView.Adapter<ShowCurricularAdapter.ViewHolder>() {
    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var tvtitlecurricular: TextView = view.findViewById(R.id.tvtitlecurricular)
        var tvcontentcurricular: TextView = view.findViewById(R.id.tvcontentcurricular)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_curricular,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvtitlecurricular.setText(curricularEntity[position].title)
        holder.tvcontentcurricular.setText(curricularEntity[position].content)
    }

    override fun getItemCount(): Int {
        return curricularEntity.size
    }

}