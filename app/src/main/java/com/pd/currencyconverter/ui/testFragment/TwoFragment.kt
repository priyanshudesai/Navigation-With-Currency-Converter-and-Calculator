package com.pd.currencyconverter.ui.testFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {
    private lateinit var _binding: FragmentTwoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
        return _binding.root
    }

    fun onTextChange(text: String) {
        _binding.tvTabTwo.text = text
    }
}