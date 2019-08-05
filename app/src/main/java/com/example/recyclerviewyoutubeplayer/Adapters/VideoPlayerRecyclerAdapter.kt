package com.example.recyclerviewyoutubeplayer.Adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recyclerviewyoutubeplayer.R
import com.example.recyclerviewyoutubeplayer.VideoModel


class VideoPlayerRecyclerAdapter(
    private val videoModels: ArrayList<VideoModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return VideoPlayerViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.videoitem, viewGroup, false)
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        (viewHolder as VideoPlayerViewHolder).onBind(videoModels[i])
        if (i==0)
        {
            (viewHolder as VideoPlayerViewHolder).performClick(false)
        }
    }


    override fun getItemCount(): Int {
        return videoModels.size
    }

}
