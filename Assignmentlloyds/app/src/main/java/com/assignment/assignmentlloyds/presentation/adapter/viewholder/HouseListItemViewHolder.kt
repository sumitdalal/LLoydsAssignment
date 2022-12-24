package com.assignment.assignmentlloyds.presentation.adapter.viewholder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.assignmentlloyds.R
import com.assignment.assignmentlloyds.databinding.ItemHouseBinding
import com.assignment.assignmentlloyds.domain.model.HouseListInfo


class HouseListItemViewHolder(
    private val binding: ItemHouseBinding,
    private val onItemClick:(HouseListInfo)->Unit?
) : RecyclerView.ViewHolder(binding.root) {

    private var HouseListInfo: HouseListInfo? = null

    fun bind(user: HouseListInfo?) {
        user?.let {
            updateViews(it)
        }
    }

    private fun updateViews(HouseListInfo: HouseListInfo) {
        this.HouseListInfo = HouseListInfo
        binding.tvHouse.text = HouseListInfo.houseName
        binding.root.setOnClickListener {
           onItemClick(HouseListInfo)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onItemClick:(HouseListInfo)->Unit?
        ): HouseListItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_house, parent, false)
            val binding = ItemHouseBinding.bind(view)
            return HouseListItemViewHolder(binding, onItemClick)
        }
    }
}