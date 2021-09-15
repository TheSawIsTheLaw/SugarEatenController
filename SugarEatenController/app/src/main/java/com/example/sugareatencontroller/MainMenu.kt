package com.example.sugareatencontroller

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.sugareatencontroller.databinding.FragmentMainMenuBinding
import java.sql.Time

class MainMenu : Fragment()
{
    lateinit var binding: FragmentMainMenuBinding
    lateinit var preferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    fun preparePrevDay()
    {
        binding.prevDayInfo.text =
            "${getString(R.string.previous_day)} ${preferences.getFloat("prevDayResult", 0.0f)}"
    }

    fun setNewDay()
    {
        val editor = preferences.edit()
        editor.putFloat("prevDayResult", preferences.getFloat("currentDayResult", 0.0f))
        editor.apply()
        Toast.makeText(requireContext(), "Wow, new day!", Toast.LENGTH_SHORT).show()
    }

    fun addToCurrentSugarList(sugarAmount: Float)
    {
        val gotSetOfAmounts = preferences.getStringSet("currentSugarAmounts", mutableSetOf())
        val gotSetOfTimes = preferences.getStringSet("currentTimesAmounts", mutableSetOf())

        gotSetOfAmounts.add()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentMainMenuBinding.inflate(layoutInflater)

        preferences = requireContext().getSharedPreferences("SugarInfo", Context.MODE_PRIVATE)

        preparePrevDay()

        binding.EditCurrentDayButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainMenu_to_changeRecord)
        }

        binding.newDayButton.setOnClickListener {
            setNewDay()
        }

        return binding.root
    }
}