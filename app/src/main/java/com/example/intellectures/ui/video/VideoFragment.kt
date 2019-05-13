package com.example.intellectures.ui.video

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.intellectures.R
import com.example.intellectures.model.Constants
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory




class VideoFragment : Fragment(), VideoView, VideoRouter {

    companion object {
        private val BANDWIDTH_METER = DefaultBandwidthMeter()
        fun newInstance(conferenceId: Int, videoId: Int) = VideoFragment().apply {
            arguments = Bundle().apply {
                putInt(Constants.CONFERENCE_ID, conferenceId)
                putInt(Constants.VIDEO_ID, videoId)
            }
        }
    }
    @Inject
    lateinit var presenter: VideoPresenter

    private var conferenceId: Int = 0
    private var videoId: Int = 0
    private lateinit var viewModel: VideoViewModel
    private var player: ExoPlayer? = null
    private var url: String? = null
    private var playerView: SimpleExoPlayerView? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        conferenceId = arguments?.getInt(Constants.CONFERENCE_ID) ?: 0
        videoId = arguments?.getInt(Constants.VIDEO_ID) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.video_fragment, container, false)
        playerView = view.findViewById<SimpleExoPlayerView>(R.id.video_view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)
        viewModel.getVideo().observe(this, Observer { video ->
            url = video?.video
            initializePlayer()
        })
        presenter.attach(this, this, viewModel)
        presenter.reload(conferenceId, videoId)
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onPause() {
        releasePlayer()
        super.onPause()
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            playerView?.setPlayer(player)
            player!!.setPlayWhenReady(playWhenReady)
            player!!.seekTo(currentWindow, playbackPosition)
        }

        if (url != null) {
            val mediaSource = buildMediaSource(Uri.parse(url))
            player!!.prepare(mediaSource, true, false)
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {

        val userAgent = "exoplayer-codelab"

        return SsMediaSource.Factory(DefaultSsChunkSource.Factory(DefaultDataSourceFactory(context, userAgent, BANDWIDTH_METER)), DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player!!.getCurrentPosition()
            currentWindow = player!!.getCurrentWindowIndex()
            playWhenReady = player!!.getPlayWhenReady()
            player!!.release()
            player = null
        }
    }

}
