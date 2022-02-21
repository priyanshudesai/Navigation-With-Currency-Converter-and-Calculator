package com.pd.currencyconverter.ui.cardlist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pd.currencyconverter.R
import com.pd.currencyconverter.adapter.EmployeeListAdapter
import com.pd.currencyconverter.adapter.EmployeeListAdapter.FragmentCommunication
import com.pd.currencyconverter.api.Api
import com.pd.currencyconverter.api.ApiClient
import com.pd.currencyconverter.databinding.FragmentCardListBinding
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.dataclass.EmployeeListDataClass
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper
import com.pd.currencyconverter.utils.NetworkStatus
import com.pd.currencyconverter.utils.NetworkStatusHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardListFragment : Fragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!
    var listEmployee: MutableList<EmployeeEntity>? = null
    var listAdapter: EmployeeListAdapter? = null
    lateinit var cardListViewModel: CardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        cardListViewModel = ViewModelProvider(requireActivity()).get(CardListViewModel::class.java)
        cardListViewModel.getAllEmployeeObservers().observe(viewLifecycleOwner, Observer {

            listEmployee = it
            listAdapter = activity?.let { it1 ->
                EmployeeListAdapter(
                    it1,
                    requireActivity(),
                    listEmployee,
                    communication
                )
            }
            binding.rvCardFrag.adapter = listAdapter
            binding.rvCardFrag.adapter?.notifyDataSetChanged()

            if (listEmployee?.isNotEmpty() == true) {
                binding.tvTotalCardFrag.text =
                    listEmployee?.size.toString() + getString(R.string.cards_total)

                binding.pbCard.visibility = View.GONE
                binding.tvTotalCardFrag.visibility = View.VISIBLE
                binding.rvCardFrag.visibility = View.VISIBLE

            }
        })
        NetworkStatusHelper(requireContext()).registerNetworkCallBack().observe(requireActivity()) {
            when (it) {
                NetworkStatus.Available -> {
                    loadListEmployee()
                    Log.e("TAG", "Network Connection Established")
                }
                NetworkStatus.Unavailable -> Log.e("TAG", "No Internet")
            }
        }
        return root
    }

    var communication: FragmentCommunication = object : FragmentCommunication {
        override fun respond(size: String) {
            Log.e("TAG", "respond: $size")
            binding.tvTotalCardFrag.text =
                size + getString(R.string.cards_total)
        }
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
                        Log.e("TAG", "API CALL")
                        var newList: MutableList<EmployeeEntity> = response.body()!!.data
//                        Collections.reverse(listEmployee)
//                        listAdapter =
//                            activity?.let { EmployeeListAdapter(it, listEmployee) }
                        if (newList.size != listEmployee?.size) {
                            Log.e("TAG", "Not equal size")
                            listEmployee = newList
                            cardListViewModel.insertEmployeesInfo(listEmployee!!)

                            binding.pbCard.visibility = View.GONE

                            binding.tvTotalCardFrag.text =
                                listEmployee?.size.toString() + " Cards total"
                            binding.tvTotalCardFrag.visibility = View.VISIBLE
                            binding.rvCardFrag.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(
                            context,
                            response.body()?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        ConstantUtils.MSG_SOME_ERROR_OCCURRED,
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
            val filteredList: MutableList<EmployeeEntity> =
                listEmployee!!.filter { item ->
                    item.firstName.trim().lowercase().startsWith(text.lowercase()) ||
                            item.lastName.trim().lowercase().startsWith(text.lowercase()) ||
                            item.designation.trim().lowercase().startsWith(text.lowercase()) ||
                            item.companyInfo.name.trim().lowercase().startsWith(text.lowercase())
                } as MutableList<EmployeeEntity>

            if (filteredList.isEmpty()) {
                listAdapter?.filterList(mutableListOf())
                Toast.makeText(context, ConstantUtils.MSG_NO_DATA_FOUND, Toast.LENGTH_SHORT).show()
            } else {
                listAdapter?.filterList(filteredList)
            }
            binding.tvTotalCardFrag.text =
                filteredList.size.toString() + getString(R.string.cards_total)
        } else Toast.makeText(context, ConstantUtils.MSG_NO_DATA_FOUND, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("CardListScreen", "CardListFragment")
    }

//    fun dataToEntity(list : List<Data>): List<EmployeeEntity>{
//        val entityList : List<EmployeeEntity>
//        entityList = ArrayList()
//        list.forEachIndexed { index, data ->
//            entityList[index] = EmployeeEntity(
//                created_at = data.created_at,
//                designation = data.designation,
//                emails = data.emails,
//                first_name = data.first_name,
//                is_decisionmaker = data.is_decisionmaker,
//                last_name = data.last_name,
//                phones = data.phones,
//                status = data.status,
//                tags = data.tags,
//                type = data.type,
//                address = data.company_info.address,
//                campaign_email = data.company_info.campaign_email,
//                email = data.company_info.email,
//                name = data.company_info.name,
//                phone_number = data.company_info.phone_number,
//                website = data.company_info.website,
//                industry_name = data.company_info.industry_name,
//                original = data.card.front.original,
//                small = data.card.front.small,
//            )
//        }
//        return entityList
//    }
//
//    fun entityToData(entityList : List<EmployeeEntity>): List<Data>{
//        val list : List<Data>
//        list = ArrayList()
//        entityList.forEachIndexed { index, data ->
//            list[index] = Data(
//                created_at = data.created_at,
//                designation = data.designation,
//                emails = data.emails,
//                first_name = data.first_name,
//                is_decisionmaker = data.is_decisionmaker,
//                last_name = data.last_name,
//                phones = data.phones,
//                status = data.status,
//                tags = data.tags,
//                type = data.type,
//                card = Card(front = Front(original = data.original, small = data.small)),
//                company_info = CompanyInfo(address = data.address,campaign_email = data.campaign_email,email = data.email,industry_name = data.industry_name,name = data.name,phone_number = data.phone_number,website = data.website)
//            )
//        }
//        return list
//    }
}