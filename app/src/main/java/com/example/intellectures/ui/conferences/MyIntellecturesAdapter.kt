package com.example.intellectures.ui.conferences

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.intellectures.R
import com.example.intellectures.model.Conference
import com.example.intellectures.model.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_conference.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import android.view.LayoutInflater
import android.view.ViewGroup



class MyIntellecturesAdapter(val presenter: ConferencesPresenter) :
    RecyclerView.Adapter<MyIntellecturesAdapter.MyViewHolder>() {

    private var data: List<Any> = ArrayList<Any>()
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(position, viewGroup, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val obj = data[position]
        when (obj) {
            is Conference -> holder.view.apply {
                city_name.text = obj.city
                duration.text = obj.dates
                holder.view.tag = data[position]
                holder.view.setOnClickListener { view ->
                    presenter.onConferenceSelected(obj)
                }
            }
            is Video -> holder.view.apply {
                if (obj.availability == Video.LEARN_MORE) {
                    location_confirm.visibility = View.VISIBLE
                    playable.setImageResource(R.drawable.play)
                    title.setTextColor(resources.getColor(android.R.color.darker_gray))
                    desc.setTextColor(resources.getColor(android.R.color.darker_gray))
                    availability.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                    holder.view.setOnClickListener { view ->
                        presenter.onVideoSelected(obj)
                    }
                } else {
                    location_confirm.visibility = View.GONE
                    playable.setImageResource(R.drawable.lock)
                    title.setTextColor(resources.getColor(R.color.material_grey_300))
                    desc.setTextColor(resources.getColor(R.color.material_grey_300))
                    availability.setTextColor(resources.getColor(R.color.material_grey_300))
                }
                Picasso.get().load(obj.img).into(thumb)
                title.text = obj.title
                desc.text = obj.desc
                availability.text = obj.availability
                holder.view.tag = data[position]
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        val obj = data[position]
        return when (obj) {
            is Conference -> R.layout.item_conference
            is Video -> R.layout.item_video
            else -> throw IllegalArgumentException("wrong item type")
        }
    }

    fun setData(data: List<Any>) {
        this.data = data
        notifyDataSetChanged()
    }
}