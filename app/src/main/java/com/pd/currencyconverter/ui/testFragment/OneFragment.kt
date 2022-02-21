package com.pd.currencyconverter.ui.testFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.databinding.FragmentOneBinding


class OneFragment : Fragment() {

    private lateinit var _binding: FragmentOneBinding
    private var mListener: OnFragmentCommunicationListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOneBinding.inflate(inflater, container, false)
        val root: View = _binding.root

        mListener = context as OnFragmentCommunicationListener

        _binding.edTabOne.doOnTextChanged { text, _, _, _ ->
            mListener!!.onTextChange(text.toString())
        }

        return root
    }

    interface OnFragmentCommunicationListener {
        fun onTextChange(text: String)
    }
}