package com.astashin.marvelcomics.characters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.characters.viewmodel.ICharactersViewModel
import com.astashin.marvelcomics.core.view.BaseFragment
import com.astashin.marvelcomics.databinding.FragmentCharactersListBinding
import com.astashin.marvelcomics.model.Comic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment : BaseFragment<ICharactersViewModel>() {

    companion object {

        private const val COMIC = "comic"

        fun buildArgs(comic: Comic) = Bundle().apply {
            putSerializable(COMIC, comic)
        }
    }

    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val comic = arguments?.getSerializable(COMIC) as Comic?
        viewModel.setComic(comic)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_characters_list, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecycler()

        viewModel.errorEvent.observe(viewLifecycleOwner, {
            showError(it)
        })
    }

    private fun showError(error: String?) {
        Toast.makeText(context, error?: getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        binding.toolbar.setNavigationOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun setupRecycler() {
        val adapter = CharactersAdapter(viewModel.charactersList.value!!)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        viewModel.charactersList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
    }
}
