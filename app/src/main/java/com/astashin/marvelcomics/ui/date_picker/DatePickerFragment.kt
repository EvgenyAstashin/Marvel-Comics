package com.astashin.marvelcomics.ui.date_picker

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.app
import com.astashin.marvelcomics.databinding.FragmentDatePickerBinding
import com.astashin.marvelcomics.ui.comics.ComicsListFragment
import javax.inject.Inject

class DatePickerFragment : Fragment(), DatePickerView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DatePickerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        app().appComponent.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DatePickerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDatePickerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_date_picker, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.detachView()
    }

    override fun selectDate(date: Date, minDate: Date, maxDate: Date, result: (Date) -> Unit) {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            result.invoke(Date(year, month, dayOfMonth))
        }
        val dialog = DatePickerDialog(context!!, dateListener, date.year, date.month, date.day)
        dialog.datePicker.maxDate = maxDate.toLong()
        dialog.datePicker.minDate = minDate.toLong()
        dialog.show()
    }

    override fun openComicsList(startDate: Date, endDate: Date) {
        NavHostFragment.findNavController(this).navigate(
            R.id.action_datePickerFragment_to_comicsListFragment,
            ComicsListFragment.buildArgs(startDate, endDate)
        )
    }
}
