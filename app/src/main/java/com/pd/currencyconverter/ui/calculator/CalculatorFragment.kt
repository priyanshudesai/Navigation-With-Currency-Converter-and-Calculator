package com.pd.currencyconverter.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.R
import com.pd.currencyconverter.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private lateinit var firstText : TextView
    private lateinit var operatorText : TextView
    private lateinit var secondText : TextView
    private lateinit var answerText : TextView
    private var operatorSet = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //result screen
        firstText = binding.resultLayoutCalculator.tvFirstCalculator
        operatorText = binding.resultLayoutCalculator.tvOperatorCalculator
        secondText = binding.resultLayoutCalculator.tvSecondCalculator
        answerText = binding.resultLayoutCalculator.tvAnswerCalculator

        //Operands
        binding.btnDot.setOnClickListener {
            if (secondText.text.isNotEmpty() && !secondText.text.contains(getString(R.string.dot))){
                setTextData(getString(R.string.dot))
            } else if (firstText.text.isNotEmpty() && !firstText.text.contains(getString(R.string.dot)) && !operatorSet){
                setTextData(getString(R.string.dot))
            } else if (firstText.text.isEmpty()){
                firstText.text = "0."
            } else if (operatorSet && secondText.text.isEmpty()){
                secondText.text = "0."
            }
        }
        binding.btnZero.setOnClickListener {
            setTextData(getString(R.string._0))
        }
        binding.btnOne.setOnClickListener {
            setTextData(getString(R.string._1))
        }
        binding.btnTwo.setOnClickListener {
            setTextData(getString(R.string._2))
        }
        binding.btnThree.setOnClickListener {
            setTextData(getString(R.string._3))
        }
        binding.btnFour.setOnClickListener {
            setTextData(getString(R.string._4))
        }
        binding.btnFive.setOnClickListener {
            setTextData(getString(R.string._5))
        }
        binding.btnSix.setOnClickListener {
            setTextData(getString(R.string._6))
        }
        binding.btnSeven.setOnClickListener {
            setTextData(getString(R.string._7))
        }
        binding.btnEight.setOnClickListener {
            setTextData(getString(R.string._8))
        }
        binding.btnNine.setOnClickListener {
            setTextData(getString(R.string._9))
        }

        //Operators
        binding.btnPlus.setOnClickListener {
            setOperatorData(getString(R.string.plus))
        }
        binding.btnMinus.setOnClickListener {
            setOperatorData(getString(R.string.minus))
        }
        binding.btnMultiple.setOnClickListener {
            setOperatorData(getString(R.string.multiply))
        }
        binding.btnDivide.setOnClickListener {
            setOperatorData(getString(R.string.divide))
        }
        binding.btnModulus.setOnClickListener {
            setOperatorData(getString(R.string.modulus))
        }

        //Equals
        binding.btnEqual.setOnClickListener {
            if (answerText.text.isNotEmpty()){
                firstText.text = answerText.text
                operatorSet = false
                operatorText.text = ""
                secondText.text = ""
                answerText.text = ""
            } else equalToCalculate()
        }
        
        //Clear functions
        binding.btnClear.setOnClickListener {
            operatorSet = false
            operatorText.text = ""
            firstText.text = ""
            secondText.text = ""
            answerText.text = ""
        }
        binding.btnBack.setOnClickListener {
            if (secondText.text.isNotEmpty()){
                secondText.text = secondText.text.toString().dropLast(1)
                answerText.text = ""
            } else if (firstText.text.isNotEmpty()){
                operatorSet = false
                operatorText.text = ""
                firstText.text = firstText.text.toString().dropLast(1)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTextData(operand : String) {
        if (!operatorSet) {
            firstText.text = firstText.text.toString().plus(operand)
        } else {
            secondText.text = secondText.text.toString().plus(operand)
            equalToCalculate()
        }
    }

    private fun setOperatorData(operator: String){
        if (firstText.text.isNotEmpty()) {
            if (answerText.text.isNotEmpty()){
                firstText.text = answerText.text
                operatorText.text = operator
                secondText.text = ""
                answerText.text = ""
            } else if (secondText.text.isNotEmpty()) {
                equalToCalculate()
            } else {
                operatorSet = true
                operatorText.text = operator
            }
        }
    }

    private fun equalToCalculate(){
        if (firstText.text.isNotEmpty() && secondText.text.isNotEmpty()){
            val firstNumber = firstText.text.toString().toDouble()
            val secondNumber = secondText.text.toString().toDouble()
            val operator = operatorText.text.toString()
            var result = 0.0
            when (operator) {
                getString(R.string.plus) -> {
                    result = firstNumber + secondNumber
                }
                getString(R.string.minus) -> {
                    result = firstNumber - secondNumber
                }
                getString(R.string.multiply) -> {
                    result = firstNumber * secondNumber
                }
                getString(R.string.divide) -> {
                    result = firstNumber / secondNumber
                }
                getString(R.string.modulus) -> {
                    result = firstNumber % secondNumber
                }
            }
            if (result.toString().endsWith(".0")){
                result.toString().subSequence(0,result.toString().length-2)
            }
            answerText.text = result.toString()
        }
    }
}