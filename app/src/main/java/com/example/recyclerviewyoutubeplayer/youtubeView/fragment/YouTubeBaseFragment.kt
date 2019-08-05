package com.example.recyclerviewyoutubeplayer.youtubeView.fragment


import android.view.View
import android.widget.ImageView
import com.example.recyclerviewyoutubeplayer.youtubeView.listener.YouTubeEventListener
import com.google.android.youtube.player.YouTubePlayer


interface YouTubeBaseFragment {

    fun setYouTubeEventListener(listener: YouTubeEventListener?)
    fun setFullScreenVisibility(v: View, fullscreen: ImageView?)
    fun release()
    fun getplayer(): YouTubePlayer?
}
