package com.example.sportsmates.home.events
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sportsmates.coach.GlideApp
import com.example.sportsmates.databinding.CoachListItemBinding
import com.example.sportsmates.ext.inflater

class EventAdapter(private val eventList:List<Event>?,private val context: FragmentActivity?):
    RecyclerView.Adapter<EventAdapter.ViewHolder>()
{
    var onItemClick: ((Event,ImageView) -> Unit)? = null
    inner class ViewHolder(private val binding: CoachListItemBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(eventItem: Event) {
            binding.partnerName.text = eventItem.name
            binding.partnerSport.text = eventItem.date
            binding.partnerAddress.text = eventItem.place
            binding.pricePerHour.text = eventItem.ticketPrice
            GlideApp.with(context!!)
                .load(eventItem.img)
                .into(binding.partnerImage)
            itemView.setOnClickListener { onItemClick?.invoke(eventItem,binding.partnerImage) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CoachListItemBinding.inflate(parent.context.inflater, parent, false))
    }

    override fun getItemCount(): Int =eventList!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        eventList?.get(position)?.let { event ->holder.bind(event)  }
    }

}