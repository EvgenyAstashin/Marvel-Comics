package com.astashin.marvelcomics.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.databinding.FragmentCharactersListBinding
import com.astashin.marvelcomics.model.Character
import com.astashin.marvelcomics.model.Comic
import com.astashin.marvelcomics.network.Api
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharactersListFragment : Fragment(), CharacterListView {

    companion object {

        private const val COMIC = "comic"

        fun buildArgs(comic: Comic) = Bundle().apply {
            putSerializable(COMIC, comic)
        }
    }

    @Inject
    lateinit var api: Api

    private lateinit var viewModel: CharactersViewModel
    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        viewModel.api = api
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
        viewModel.attachView(this)
        setupToolbar()
        setupRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.detachView()
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
        val adapter = CharactersAdapter(viewModel.charactersList.value!!)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        viewModel.charactersList.observe(viewLifecycleOwner, {
            adapter.notifyDataSetChanged()
        })
    }
}
