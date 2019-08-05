package com.example.recyclerviewyoutubeplayer

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.recyclerviewyoutubeplayer.Adapters.VideoPlayerRecyclerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList
import android.graphics.Point
import android.view.WindowManager
import com.example.recyclerviewyoutubeplayer.Adapters.VideoPlayerViewHolder


class MainActivity : AppCompatActivity() {
    private var screenDefaultHeight: Int=0
    private var videoSurfaceDefaultHeight: Int =0 
    var videoModels: ArrayList<VideoModel> = ArrayList()
    private val client = OkHttpClient()
    lateinit var adapter : VideoPlayerRecyclerAdapter;
    lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val point = Point()
        display.getSize(point)
        videoSurfaceDefaultHeight = point.x
        screenDefaultHeight = point.y


        run("https://my-json-server.typicode.com/Multibhashi/sample-api/video");
        layoutManager = LinearLayoutManager(this)
        recycler_view.setLayoutManager(layoutManager)

        adapter = VideoPlayerRecyclerAdapter(videoModels)
        recycler_view.setAdapter(adapter)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if(!recyclerView.canScrollVertically(1)){
                        playVideo(true);
                    }
                    else{
                        playVideo(false);
                    }

                }

            }
        })

    }

    private fun playVideo(isEndOfList: Boolean) {
        val targetPosition: Int
        if(!isEndOfList) {
            var startPosition = layoutManager.findFirstVisibleItemPosition()
            var endPosition = layoutManager.findLastVisibleItemPosition()

            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1;
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return;
            }

            // if there is more than 1 list-item on the screen
            if (startPosition != endPosition) {
                var startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition);
                var endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition);

                if( startPositionVideoHeight > endPositionVideoHeight)
                    targetPosition =startPosition
                else
                    targetPosition = endPosition;
            }
            else {
                targetPosition = startPosition;
            }
        }
        else{
            targetPosition = videoModels.size - 1;
        }

        (recycler_view.findViewHolderForAdapterPosition(targetPosition) as VideoPlayerViewHolder).performClick(true)
    }

    private fun getVisibleVideoSurfaceHeight(playPosition: Int): Int {
        val at = playPosition - layoutManager.findFirstVisibleItemPosition()

        val child = recycler_view.getChildAt(at) ?: return 0

        val location = IntArray(2)
        child!!.getLocationInWindow(location)

        return if (location[1] < 0) {
            location[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location[1]
        }
    }


    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){

                    var jsonArray = JSONArray(response.body()?.string());
                    System.out.println(jsonArray)
                    for(i in 0 until jsonArray.length()){
                        var jsonObject = jsonArray[i] as JSONObject;

                        var videoModel = VideoModel(jsonObject.getString("video_id"),jsonObject.getString("title"))
                        videoModels.add(videoModel);
                    }
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
