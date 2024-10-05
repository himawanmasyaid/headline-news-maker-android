package com.hmwn.headlinenewsmaker.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmwn.headlinenewsmaker.common.gone
import com.hmwn.headlinenewsmaker.common.visible
import com.hmwn.headlinenewsmaker.data.local.entity.HeadlineNewsEntity
import com.hmwn.headlinenewsmaker.data.model.TemplateDesignModel
import com.hmwn.headlinenewsmaker.data.model.getTemplateDesignLayout
import com.hmwn.headlinenewsmaker.databinding.ItemHeadlineHistoryBinding
import com.hmwn.headlinenewsmaker.view.template.TemplateAdapter

class HeadlineAdapter : RecyclerView.Adapter<HeadlineAdapter.ViewHolder>() {

    var listData: MutableList<HeadlineNewsEntity> = ArrayList()

    fun insertAll(data: List<HeadlineNewsEntity>) {
        data.forEach {
            listData.add(it)
            notifyItemInserted(listData.size - 1)
        }
    }

    fun clear() {
        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

    lateinit var callbackListener: AdapterListener

    interface AdapterListener {
        fun onClickItem(item: HeadlineNewsEntity)
    }

    fun setListener(listener: AdapterListener) {
        this.callbackListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHeadlineHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.bindTo(item)
    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(val binding: ItemHeadlineHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(data: HeadlineNewsEntity) {

            with(binding) {

                tvHeadline.text = data.headline
                tvDescription.text = data.description

                itemView.rootView.setOnClickListener {
                    callbackListener.onClickItem(data)
                }

            }
        }
    }

}