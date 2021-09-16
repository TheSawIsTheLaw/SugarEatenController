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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.sugareatencontroller.databinding.FragmentMainMenuBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.Instant
import java.util.*

class MainMenu : Fragment()
{
    lateinit var binding: FragmentMainMenuBinding
    lateinit var preferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    fun preparePrevDayLabel()
    {
        binding.prevDayInfo.text =
            "${getString(R.string.previous_day)} ${preferences.getFloat("prevDayResult", 0.0f)}"
    }

    @SuppressLint("SetTextI18n")
    fun prepareCurrentAmountOfEatenLabel()
    {
        val curAmount = preferences.getFloat("currentAmountOfEaten", 0.0f)
        binding.todaysResultLabel.text = "${getString(R.string.current_amount_of_eaten)} $curAmount"
    }

    fun setNewDay()
    {
        val editor = preferences.edit()
        editor.putFloat("prevDayResult", preferences.getFloat("currentAmountOfEaten", 0.0f))
        editor.putFloat("currentAmountOfEaten", 0.0f)
        editor.putString("valuesList", "")
        editor.putString("timesList", "")
        editor.apply()

        prepareCurrentAmountOfEatenLabel()
        preparePrevDayLabel()

        Toast.makeText(requireContext(), "Wow, new day!", Toast.LENGTH_SHORT).show()
    }

    fun updateCurrentAmountOfEatenPref(sugarList: MutableList<Float>)
    {
        var eaten = 0.0f
        sugarList.forEach { eaten += it }

        val editor = preferences.edit()
        editor.putFloat("currentAmountOfEaten", eaten)
        editor.apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addToCurrentSugarList(sugarAmount: Float)
    {
        var valuesListJson = preferences.getString("valuesList", "")
        var timesListJson = preferences.getString("timesList", "")
        var valuesList: MutableList<Float> = mutableListOf()
        var timesList: MutableList<Instant> = mutableListOf()
        if (valuesListJson != "")
        {
            valuesList = Gson().fromJson(valuesListJson, valuesList.javaClass)
            timesList = Gson().fromJson(timesListJson, timesList.javaClass)
        }

        valuesList.add(0, sugarAmount)
        timesList.add(0, Instant.now())

        valuesListJson = Gson().toJson(valuesList)
        timesListJson = Gson().toJson(timesList)
        val editor = preferences.edit()
        editor.putString("valuesList", valuesListJson)
        editor.putString("timesList", timesListJson)
        editor.apply()

        updateCurrentAmountOfEatenPref(valuesList)
        prepareCurrentAmountOfEatenLabel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewRecord()
    {
        val newRec = binding.newRecordValueInput.text.toString().toFloatOrNull()
        if (newRec != null)
        {
            addToCurrentSugarList(newRec)
            Toast.makeText(requireContext(), "Successfully added", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(requireContext(), "Invalid input, please, try again", Toast.LENGTH_LONG)
                .show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentMainMenuBinding.inflate(layoutInflater)

        preferences = requireContext().getSharedPreferences("SugarInfo", Context.MODE_PRIVATE)

        preparePrevDayLabel()
        prepareCurrentAmountOfEatenLabel()

        binding.EditCurrentDayButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainMenu_to_changeRecord)
        }

        binding.newDayButton.setOnClickListener {
            setNewDay()
        }

        binding.addNewRecordButton.setOnClickListener {
            addNewRecord()
        }

        return binding.root
    }
}