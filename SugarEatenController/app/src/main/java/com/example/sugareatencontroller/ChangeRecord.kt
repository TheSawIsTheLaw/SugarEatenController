package com.example.sugareatencontroller

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import com.example.sugareatencontroller.databinding.FragmentChangeRecordBinding
import com.google.gson.Gson
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ChangeRecord : Fragment()
{
    lateinit var binding: FragmentChangeRecordBinding
    lateinit var preferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    fun fullTheListOfRecords()
    {
        val sugarRecordsList =
            Gson().fromJson(preferences.getString("valuesList", ""), mutableListOf<Float>().javaClass)
        val timeRecordsList =
            Gson().fromJson(preferences.getString("timesList", ""), mutableListOf<String>().javaClass)

        if (sugarRecordsList == null || timeRecordsList == null)
            return

        for (i in sugarRecordsList.indices)
        {
            val newButton = RadioButton(requireContext())
            newButton.text =
                "${getString(R.string.amount)}: ${sugarRecordsList[i]}; ${getString(R.string.at_time)}: ${timeRecordsList[i]}"
            binding.radioGroup.addView(newButton)
        }

        binding.radioGroup.check(1)
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

        binding.changeRecordButton

        return binding.root
    }
}