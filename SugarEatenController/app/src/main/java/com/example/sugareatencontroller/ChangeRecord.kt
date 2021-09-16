package com.example.sugareatencontroller

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sugareatencontroller.databinding.FragmentChangeRecordBinding
import com.google.gson.Gson

class ChangeRecord : Fragment()
{
    lateinit var binding: FragmentChangeRecordBinding
    lateinit var preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun fullTheListOfRecords()
    {
        binding.radioGroup.removeAllViews()

        val sugarRecordsList =
            Gson().fromJson(
                preferences.getString("valuesList", ""),
                mutableListOf<Float>().javaClass
            )
        val timeRecordsList =
            Gson().fromJson(
                preferences.getString("timesList", ""),
                mutableListOf<String>().javaClass
            )

        if (sugarRecordsList == null || timeRecordsList == null)
            return

        for (i in sugarRecordsList.indices)
        {
            val newButton = RadioButton(requireContext())
            newButton.text =
                "${getString(R.string.amount)}: ${sugarRecordsList[i]}; ${getString(R.string.at_time)}: ${timeRecordsList[i]}"
            newButton.id = i + 1 // To save the tradition
            binding.radioGroup.addView(newButton)
        }

        binding.radioGroup.check(1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeChosenRecord()
    {
        val newValueInRecord = binding.newValueForRecordInput.text.toString().toFloatOrNull()

        // Yep, there is no need to check float using 1e-5
        if (newValueInRecord == null || newValueInRecord < 0)
        {
            Toast.makeText(requireContext(), "Invalid value", Toast.LENGTH_SHORT).show()
            return
        }

        val editIndex = binding.radioGroup.checkedRadioButtonId - 1

        val listOfValues = Gson().fromJson(
            preferences.getString("valuesList", ""),
            mutableListOf<Float>().javaClass
        )

        listOfValues[editIndex] = newValueInRecord
        val editor = preferences.edit()
        editor.putString("valuesList", Gson().toJson(listOfValues))
        editor.apply()

        fullTheListOfRecords()
        Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentChangeRecordBinding.inflate(layoutInflater)

        preferences = requireContext().getSharedPreferences("SugarInfo", Context.MODE_PRIVATE)

        fullTheListOfRecords()

        binding.changeRecordButton.setOnClickListener {
            changeChosenRecord()
        }

        return binding.root
    }
}