package com.pd.currencyconverter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pd.currencyconverter.R
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.ui.cardlist.CardDetailsActivity
import com.pd.currencyconverter.utils.ConstantUtils

class EmployeeListAdapter(
    private var mContext: Context,
    private var listEmployee: List<EmployeeEntity>?
) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_card_list, null)

        val viewHolder = ViewHolder(itemView)

        viewHolder.cardView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, CardDetailsActivity::class.java)
                    .putExtra(ConstantUtils.INTENT_PARSE_DATA, listEmployee?.get(viewHolder.adapterPosition))
            )
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empData: EmployeeEntity? = listEmployee?.get(position)

        holder.nameEmployee.text = empData?.first_name + " " + empData?.last_name
        holder.designationEmployee.text = empData?.designation
        holder.nameCompany.text = empData?.company_info?.name

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_placeholder)
        requestOptions.error(R.drawable.ic_placeholder)

        Glide.with(mContext)
            .setDefaultRequestOptions(requestOptions)
            .load(empData?.card?.front?.small).into(holder.profileIconEmployee)
    }

    override fun getItemCount(): Int {
        return listEmployee!!.size
    }

    fun filterList(filterList: List<EmployeeEntity>?) {
        listEmployee = filterList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameEmployee: TextView = itemView.findViewById(R.id.tv_name_cardHolder)
        var designationEmployee: TextView = itemView.findViewById(R.id.tv_designation_cardHolder)
        var nameCompany: TextView = itemView.findViewById(R.id.tv_companyName_cardHolder)
        var cardView: CardView = itemView.findViewById(R.id.cv_card_cardHolder)
        var profileIconEmployee: ImageView = itemView.findViewById(R.id.iv_icon_cardHolder)
    }
}