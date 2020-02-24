package com.astashin.marvelcomics.ui.comics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.app
import com.astashin.marvelcomics.databinding.FragmentComicsListBinding
import com.astashin.marvelcomics.model.Comic
import javax.inject.Inject


class ComicsListFragment : Fragment() {

    companion object {

        private const val START_DATE = "start_date"
        private const val END_DATE = "end_date"

        fun buildArgs(startDate: Date, endDate: Date) = Bundle().apply {
            putSerializable(START_DATE, startDate)
            putSerializable(END_DATE, endDate)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ComicsViewModel
    private lateinit var binding: FragmentComicsListBinding
    private lateinit var adapter: ComicsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        app().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ComicsViewModel::class.java]
        val startDate = arguments?.getSerializable(START_DATE) as Date?
        val endDate = arguments?.getSerializable(END_DATE) as Date?
        viewModel.setStartAndEndDates(startDate, endDate)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_comics_list, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.toolbar.setNavigationOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun setupRecycler() {
        adapter = ComicsAdapter(viewModel.comicsList.value!!)
        val layoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        viewModel.comicsList.observe(this, Observer<List<Comic>> {
            adapter.notifyDataSetChanged()
        })
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + firstVisibleItemPosition) == totalItemCount)
                    viewModel.loadComics()
            }
        })
    }
}
