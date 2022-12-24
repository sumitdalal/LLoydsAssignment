package com.assignment.assignmentlloyds.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.presentation.adapter.viewholder.HouseListItemViewHolder

class HouseListAdapter(private val onItemClick: (HouseListInfo) -> Unit?) :
    RecyclerView.Adapter<HouseListItemViewHolder>() {
    private val houseList = arrayListOf<HouseListInfo>()

    override fun onBindViewHolder(holder: HouseListItemViewHolder, position: Int) {
        holder.bind(houseList.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseListItemViewHolder {
        return HouseListItemViewHolder.create(parent, onItemClick)
    }

    override fun getItemCount(): Int = houseList.size

    fun updateItemList(list: List<HouseListInfo>){
        houseList.clear()
        houseList.addAll(list)
        notifyDataSetChanged()
    }

}