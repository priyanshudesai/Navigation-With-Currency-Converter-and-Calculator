package com.pd.currencyconverter.ui.cardlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.pd.currencyconverter.R
import com.pd.currencyconverter.adapter.EmployeeListAdapter
import com.pd.currencyconverter.api.Api
import com.pd.currencyconverter.api.ApiClient
import com.pd.currencyconverter.databinding.FragmentCardListBinding
import com.pd.currencyconverter.dataclass.Data
import com.pd.currencyconverter.dataclass.EmployeeListDataClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*


class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    var listEmployee: List<Data>? = null
    var listAdapter: EmployeeListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadListEmployee()
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun loadListEmployee() {
        val api: Api? = ApiClient.client?.create(Api::class.java)
        api?.listEmployees()?.enqueue(object : Callback<EmployeeListDataClass?> {
            override fun onResponse(
                call: Call<EmployeeListDataClass?>?,
                response: Response<EmployeeListDataClass?>
            ) {
                try {
                    if (response.body()?.success == true) {
                        listEmployee = response.body()!!.data
//                        Collections.reverse(listEmployee)
                        listAdapter =
                            activity?.let { EmployeeListAdapter(it, listEmployee) }
                        binding.rvCardFrag.adapter = listAdapter
                        binding.tvTotalCardFrag.text = listEmployee?.size.toString() + " Cards total"

                        binding.pbCard.visibility = View.GONE
                        binding.tvTotalCardFrag.visibility = View.VISIBLE
                        binding.rvCardFrag.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(
                            context,
                            response.body()?.message.toString() + "",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Some error occurred. Please try again!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<EmployeeListDataClass?>?, t: Throwable) {
                Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filter(text: String) {
        if (listEmployee != null && listEmployee?.isNotEmpty() == true) {
            val filteredList: List<Data> =
                listEmployee!!.filter {
                        item ->
                    item.first_name.trim().lowercase().startsWith(text.lowercase()) ||
                    item.last_name.trim().lowercase().startsWith(text.lowercase())  ||
                    item.designation.trim().lowercase().startsWith(text.lowercase())  ||
                    item.company.name.trim().lowercase().startsWith(text.lowercase())
                }

            if (filteredList.isEmpty()) {
                listAdapter?.filterList(emptyList())
                Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
            }else {
                listAdapter?.filterList(filteredList)
            }
            binding.tvTotalCardFrag.text = filteredList.size.toString() + " Cards total"
        } else Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
    }
}