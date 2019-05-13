package com.example.intellectures.ui.conferences

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intellectures.R
import com.example.intellectures.ui.video.VideoFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ConferencesFragment : Fragment(), ConferencesView, ConferencesRouter {

    @Inject
    lateinit var presenter: ConferencesPresenter

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyIntellecturesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = ConferencesFragment()
    }

    private lateinit var viewModel: ConferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.my_intellectures_fragment, container, false)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = MyIntellecturesAdapter(presenter)

        recyclerView = view.findViewById<RecyclerView>(R.id.my_intellecture_list).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConferencesViewModel::class.java)
        viewModel.getItems().observe(this, Observer { items -> viewAdapter.setData(items!!) })
        presenter.attach(this, this, viewModel)
        presenter.reload()
    }

    override fun toVideo(conferenceId: Int, videoId: Int) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.main_container, VideoFragment.newInstance(conferenceId, videoId))
            ?.addToBackStack(null)
            ?.commit()
    }
}
