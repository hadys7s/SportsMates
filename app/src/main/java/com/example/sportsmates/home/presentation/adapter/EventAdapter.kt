package com.example.sportsmates.home.presentation.adapter
import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.coach.GlideApp
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater
import com.example.sportsmates.home.data.datamodels.EventDataItem

class EventAdapter(private val eventDataItemList:List<EventDataItem>?, private val context: Context):
    RecyclerView.Adapter<EventAdapter.ViewHolder>()
{
    var onItemClick: ((EventDataItem, ImageView) -> Unit)? = null
    inner class ViewHolder(private val binding: CoachListItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(eventDataItemItem: EventDataItem) {
            binding.partnerName.text = eventDataItemItem.name
            binding.partnerSport.text = eventDataItemItem.date
            binding.partnerAddress.text = eventDataItemItem.place
            binding.pricePerHour.text = eventDataItemItem.ticketPrice
            GlideApp.with(context)
                .load(eventDataItemItem.img)
                .into(binding.partnerImage)
            itemView.setOnClickListener { onItemClick?.invoke(eventDataItemItem,binding.partnerImage) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int =eventDataItemList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        eventDataItemList?.get(position)?.let { event ->holder.bind(event)  }
    }

}
