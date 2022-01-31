package com.pd.currencyconverter.ui.currencyconverter

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.databinding.FragmentCurrencyConverterBinding

class CurrencyConverterFragment : Fragment() {

    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!
    private lateinit var currencyCodes: Array<String>
    private lateinit var currentSelectedCode: String
    var amount = "0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        currentSelectedCode = getString(com.pd.currencyconverter.R.string.usd)
        currencyCodes = arrayOf(
            getString(com.pd.currencyconverter.R.string.usd),
            getString(com.pd.currencyconverter.R.string.aed),
            getString(com.pd.currencyconverter.R.string.eur),
            getString(com.pd.currencyconverter.R.string.gbp),
            getString(com.pd.currencyconverter.R.string.aud),
            getString(com.pd.currencyconverter.R.string.cad)
        )

        val aa: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), R.layout.simple_spinner_item, currencyCodes)
        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrencyValue.adapter = aa

        binding.spinnerCurrencyValue.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    currentSelectedCode = selectedItem
                    calculateData(binding.editTextValue.text.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }


        binding.editTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Log.e("TAG", "onTextChanged: $p0")
                amount = p0.toString()
                calculateData(amount)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun calculateData(amount: String) {
        if (amount.isNotEmpty()) {
            var currentPrice = 0.013433048
            when (binding.spinnerCurrencyValue.selectedItem.toString()) {
                getString(com.pd.currencyconverter.R.string.usd) -> currentPrice = 0.013433048
                getString(com.pd.currencyconverter.R.string.aed) -> currentPrice = 0.049359097
                getString(com.pd.currencyconverter.R.string.eur) -> currentPrice = 0.01184057
                getString(com.pd.currencyconverter.R.string.gbp) -> currentPrice = 0.0098717752
                getString(com.pd.currencyconverter.R.string.aud) -> currentPrice = 0.018659818
                getString(com.pd.currencyconverter.R.string.cad) -> currentPrice = 0.016772634
            }

            val calculatedPrice = (amount.toDouble()) * currentPrice
            binding.txtAnswer.text = calculatedPrice.toString()
        } else {
            binding.txtAnswer.text = ""
        }
    }
}