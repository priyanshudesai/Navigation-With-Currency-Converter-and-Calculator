package com.pd.currencyconverter.ui.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment(), TextWatcher {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btPlus.setOnClickListener {
            binding.tvShowOperator.text = "+"
            calculate("+")
        }
        binding.btMinus.setOnClickListener {
            binding.tvShowOperator.text = "-"
            calculate("-")
        }
        binding.btMultiplication.setOnClickListener {
            binding.tvShowOperator.text = "*"
            calculate("*")
        }
        binding.btDivision.setOnClickListener {
            binding.tvShowOperator.text = "/"
            calculate("/")
        }
        binding.btModulus.setOnClickListener {
            binding.tvShowOperator.text = "%"
            calculate("%")
        }

        binding.etFirstValue.addTextChangedListener(this)
        binding.etSecondValue.addTextChangedListener(this)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculate(operator: String) {
        if (binding.etFirstValue.text.isNotEmpty() && binding.etSecondValue.text.isNotEmpty()) {
            val firstValue = binding.etFirstValue.text.toString().toDouble()
            val secondValue = binding.etSecondValue.text.toString().toDouble()
            when (operator) {
                "+" -> binding.tvAnswer.text = (firstValue + secondValue).toString()
                "-" -> binding.tvAnswer.text = (firstValue - secondValue).toString()
                "*" -> binding.tvAnswer.text = (firstValue * secondValue).toString()
                "/" -> binding.tvAnswer.text = (firstValue / secondValue).toString()
                "%" -> binding.tvAnswer.text = (firstValue % secondValue).toString()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Log.e("text change", "onTextChanged: $p0")
        calculate(binding.tvShowOperator.text.toString())
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}