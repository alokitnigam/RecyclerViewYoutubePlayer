package com.example.recyclerviewyoutubeplayer.Adapters

import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewyoutubeplayer.R
import com.example.recyclerviewyoutubeplayer.VideoModel
import com.example.recyclerviewyoutubeplayer.youtubeView.YouTubePlayerView
import com.example.recyclerviewyoutubeplayer.youtubeView.listener.YouTubeEventListener
import com.example.recyclerviewyoutubeplayer.youtubeView.models.YouTubePlayerType


class VideoPlayerViewHolder(internal var parent: View) : RecyclerView.ViewHolder(parent) {

    internal var title: TextView
    internal var fullscreen: ImageView
    internal var mute: ImageView
    internal var playerView: YouTubePlayerView
    internal var rv: RelativeLayout
    val API_KEY_YOUTUBE = "AIzaSyAVeTsyAjfpfBBbUQq4E7jooWwtV2D_tjE"


    init {
        title = parent.findViewById(R.id.title)
        playerView = parent.findViewById(R.id.youtube_view)
        fullscreen = parent.findViewById(R.id.fullscreen)
        mute = parent.findViewById(R.id.mute)
        rv = parent.findViewById(R.id.rv)

        mute.setOnClickListener{
            if (!playerView.toggleVolume())
                mute.setImageDrawable(ContextCompat.getDrawable( mute.context,R.drawable.ic_volume_off_grey_24dp))
            else
                mute.setImageDrawable(ContextCompat.getDrawable( mute.context,R.drawable.ic_volume_up_grey_24dp))

        }
    }

    fun onBind(video: VideoModel) {

        title.setText(video.title)
        var listner : YouTubeEventListener =object : YouTubeEventListener{
            override fun onInitializationFailure(error: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onReady() {
            }

            override fun onPlay(currentTime: Int) {
//                video.seekTime=currentTime
//                playerView.setSeekTime(video.seekTime)
            }

            override fun onPause(currentTime: Int) {
//                video.seekTime=currentTime
//                playerView.setSeekTime(video.seekTime)
             }

            override fun onStop(currentTime: Int, totalDuration: Int) {
//                video.seekTime=currentTime
//                playerView.setSeekTime(video.seekTime)
            }

            override fun onBuffering(currentTime: Int, isBuffering: Boolean) {
            }

            override fun onSeekTo(currentTime: Int, newPositionMillis: Int) {
            }


            override fun onNativeNotSupported() {
            }

            override fun onCued() {
            }

        }

        if (!video.video_id.isEmpty()){
            playerView.initPlayer(API_KEY_YOUTUBE,
                video.video_id,
                "https://cdn.rawgit.com/flipkart-incubator/inline-youtube-view/60bae1a1/youtube-android/youtube_iframe_player.html",
                YouTubePlayerType.AUTO,
                listner,
                itemView.context as AppCompatActivity,
                null,
                rv,
                fullscreen,
                video.seekTime,
                mute
            )
        }

    }

    fun performClick(muted : Boolean) {
        playerView.muted=muted
        playerView.post{
            playerView.click(muted)
            if (muted)
                mute.setImageDrawable(ContextCompat.getDrawable( mute.context,R.drawable.ic_volume_off_grey_24dp))
            else
                mute.setImageDrawable(ContextCompat.getDrawable( mute.context,R.drawable.ic_volume_up_grey_24dp))

        }


    }

}