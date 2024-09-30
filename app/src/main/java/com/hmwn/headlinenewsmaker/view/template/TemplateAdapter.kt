package com.hmwn.headlinenewsmaker.view.template

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hmwn.headlinenewsmaker.data.model.TemplateDesignModel
import com.hmwn.headlinenewsmaker.databinding.ItemTemplateDesignBinding

class TemplateAdapter : RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {

    var listData: MutableList<TemplateDesignModel> = ArrayList()
    lateinit var callbackListener: AdapterListener

    fun insertAll(data: List<TemplateDesignModel>) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTemplateDesignBinding.inflate(
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

    inner class ViewHolder(val binding: ItemTemplateDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: TemplateDesignModel) {

            with(binding) {

                viewStub.layoutResource = item.template
                viewStub.inflate()

            }

            itemView.rootView.setOnClickListener {
                callbackListener.onClickSetting(item)
            }

        }

    }

    interface AdapterListener {
        fun onClickSetting(item: TemplateDesignModel)
    }

    fun setListener(listener: AdapterListener) {
        this.callbackListener = listener
    }

}