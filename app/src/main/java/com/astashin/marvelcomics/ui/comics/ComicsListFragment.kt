package com.astashin.marvelcomics.ui.comics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.databinding.FragmentComicsListBinding
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.network.Api
import com.astashin.marvelcomics.characters.view.CharactersListFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComicsListFragment : Fragment(), ComicsListView, ComicsAdapter.OnComicClickListener {

    companion object {

        private const val START_DATE = "start_date"
        private const val END_DATE = "end_date"

        fun buildArgs(startDate: Date, endDate: Date) = Bundle().apply {
            putSerializable(START_DATE, startDate)
            putSerializable(END_DATE, endDate)
        }
    }

    @Inject
    lateinit var api: Api

    private lateinit var viewModel: ComicsViewModel
    private lateinit var binding: FragmentComicsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)
        viewModel.api = api
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
        viewModel.attachView(this)
        setupRecycler()
        setupToolbar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.detachView()
    }

    override fun onComicClicked(comic: Comic) {
        NavHostFragment.findNavController(this).navigate(
            R.id.action_comicsListFragment_to_charactersListFragment,
            CharactersListFragment.buildArgs(comic)
        )
    }

    override fun showError(error: String?) {
        Toast.makeText(context, error?: getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.toolbar.setNavigationOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun setupRecycler() {
        val adapter = ComicsAdapter(viewModel.comicsList.value!!, this)
        val layoutManager = LinearLayoutManager(context)
        binding.recycler.layoutManager = layoutManager
        binding.recycler.adapter = adapter
        viewModel.comicsList.observe(viewLifecycleOwner, {
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
