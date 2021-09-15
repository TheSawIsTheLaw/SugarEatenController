package com.example.sugareatencontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.sugareatencontroller.databinding.FragmentMainMenuBinding

class MainMenu : Fragment()
{
    lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentMainMenuBinding.inflate(layoutInflater)

        binding.EditCurrentDayButton.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainMenu_to_changeRecord)
        }

        return binding.root
    }
}