package com.pd.currencyconverter.ui.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.R
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
            binding.tvShowOperator.text = getString(R.string.plus)
            calculate(getString(R.string.plus))
        }
        binding.btMinus.setOnClickListener {
            binding.tvShowOperator.text = getString(R.string.minus)
            calculate(getString(R.string.minus))
        }
        binding.btMultiplication.setOnClickListener {
            binding.tvShowOperator.text = getString(R.string.multiply)
            calculate(getString(R.string.multiply))
        }
        binding.btDivision.setOnClickListener {
            binding.tvShowOperator.text = getString(R.string.divide)
            calculate(getString(R.string.divide))
        }
        binding.btModulus.setOnClickListener {
            binding.tvShowOperator.text = getString(R.string.modulus)
            calculate(getString(R.string.modulus))
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
                getString(R.string.plus) -> binding.tvAnswer.text =
                    (firstValue + secondValue).toString()
                getString(R.string.minus) -> binding.tvAnswer.text =
                    (firstValue - secondValue).toString()
                getString(R.string.multiply) -> binding.tvAnswer.text =
                    (firstValue * secondValue).toString()
                getString(R.string.divide) -> binding.tvAnswer.text =
                    (firstValue / secondValue).toString()
                getString(R.string.modulus) -> binding.tvAnswer.text =
                    (firstValue % secondValue).toString()
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        calculate(binding.tvShowOperator.text.toString())
    }

    override fun afterTextChanged(p0: Editable?) {}
}