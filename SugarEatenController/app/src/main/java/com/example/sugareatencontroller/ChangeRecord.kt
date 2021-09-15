package com.example.sugareatencontroller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sugareatencontroller.databinding.FragmentChangeRecordBinding

class ChangeRecord : Fragment()
{
    lateinit var binding: FragmentChangeRecordBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentChangeRecordBinding.inflate(layoutInflater)

        return binding.root
    }
}