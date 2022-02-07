package com.pd.currencyconverter.ui.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pd.currencyconverter.databinding.FragmentCurrencyConverterBinding
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper

class CurrencyConverterFragment : Fragment() {

    private var _binding: FragmentCurrencyConverterBinding? = null
    private val binding get() = _binding!!
//    private lateinit var currencyCodes: Array<String>
//    private lateinit var currentSelectedCode: String
//    var amount = "0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        currentSelectedCode = getString(com.pd.currencyconverter.R.string.usd)
//        currencyCodes = arrayOf(
//            getString(com.pd.currencyconverter.R.string.usd),
//            getString(com.pd.currencyconverter.R.string.aed),
//            getString(com.pd.currencyconverter.R.string.eur),
//            getString(com.pd.currencyconverter.R.string.gbp),
//            getString(com.pd.currencyconverter.R.string.aud),
//            getString(com.pd.currencyconverter.R.string.cad)
//        )

//        val aa: ArrayAdapter<*> =
//            ArrayAdapter<Any?>(requireActivity(), R.layout.simple_spinner_item, currencyCodes)
//        aa.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
//        binding.spinnerCurrencyValue.adapter = aa
//
//        binding.spinnerCurrencyValue.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View,
//                    position: Int,
//                    id: Long
//                ) {
//                    val selectedItem = parent.getItemAtPosition(position).toString()
//                    currentSelectedCode = selectedItem
//                    calculateData(binding.editTextValue.text.toString())
//                }
//
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }


        binding.editTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateData(p0.toString())
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
            val currentPriceUSD = 0.013369025
            val currentPriceAED = 0.049097743
            val currentPriceAUD = 0.018700047

            val calculatedPriceUSD = (amount.toDouble()) * currentPriceUSD
            val calculatedPriceAED = (amount.toDouble()) * currentPriceAED
            val calculatedPriceAUD = (amount.toDouble()) * currentPriceAUD
            binding.tvPriceCurrencyConverterUSD.text = String.format("%.2f", calculatedPriceUSD)
            binding.tvPriceCurrencyConverterAED.text = String.format("%.2f", calculatedPriceAED)
            binding.tvPriceCurrencyConverterAUD.text = String.format("%.2f", calculatedPriceAUD)
        } else {
            binding.tvPriceCurrencyConverterUSD.text = "0.0"
            binding.tvPriceCurrencyConverterAED.text = "0.0"
            binding.tvPriceCurrencyConverterAUD.text = "0.0"
        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("CurrencyConverterScreen", "CurrencyConverterFragment")
    }
}