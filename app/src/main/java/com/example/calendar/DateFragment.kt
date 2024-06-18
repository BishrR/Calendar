package com.example.calendar

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calendar.databinding.FragmentDateBinding


class DateFragment : Fragment() {

    private lateinit var binding: FragmentDateBinding
    private lateinit var dateTextView: TextView
    private lateinit var dateTextViewVM: TextView
    private val viewModel: DateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleListeners()
        retrieveData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("date", dateTextView.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        dateTextView.text = savedInstanceState?.getString("date") ?: "Select Date"
    }

    private fun showDatePickerDialog(option: Option) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                when (option) {
                    is Option.NormalText -> {
                        dateTextView.text = selectedDate
                    }

                    is Option.VMText -> {
                        viewModel.updateDate(selectedDate)
//                        viewModel.saveDate("date", selectedDate)
                        dateTextViewVM.text = selectedDate
                    }
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun handleListeners() {
        dateTextView = binding.date
        dateTextViewVM = binding.dateVM
        binding.date.setOnClickListener {
            showDatePickerDialog(Option.NormalText)
        }
        binding.dateVM.setOnClickListener {
            showDatePickerDialog(Option.VMText)
        }
    }

    private fun retrieveData() {
        dateTextViewVM.text = viewModel.dateStateFlow.value
//        dateTextViewVM.text = viewModel.retrieveDate("date")
    }

}

sealed class Option {
    data object NormalText : Option()
    data object VMText : Option()
}