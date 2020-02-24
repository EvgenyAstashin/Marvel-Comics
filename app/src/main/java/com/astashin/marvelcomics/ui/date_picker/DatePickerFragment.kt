package com.astashin.marvelcomics.ui.date_picker

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.astashin.marvelcomics.Date
import com.astashin.marvelcomics.R
import com.astashin.marvelcomics.app
import com.astashin.marvelcomics.databinding.FragmentDatePickerBinding
import javax.inject.Inject

class DatePickerFragment : Fragment(), DatePickerView {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: DatePickerViewModel

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

    override fun selectDate(date: Date, result: (Date) -> Unit) {
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            result.invoke(Date(year, month, dayOfMonth))
        }
        val dialog = DatePickerDialog(context!!, dateListener, date.year, date.month, date.day)
        dialog.show()
    }
}
