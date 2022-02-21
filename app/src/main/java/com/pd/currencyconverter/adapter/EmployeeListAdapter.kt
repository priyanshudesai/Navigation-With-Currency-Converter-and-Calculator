package com.pd.currencyconverter.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pd.currencyconverter.R
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.ui.cardlist.CardDetailsActivity
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper

class EmployeeListAdapter(
    private var mContext: Context,
    private var mActivity: Activity,
    private var listEmployee: MutableList<EmployeeEntity>?,
    private var mCommunication: FragmentCommunication
) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_card_list, null)

        val viewHolder = ViewHolder(itemView)

        viewHolder.cardView.setOnClickListener {
            FirebaseAnalyticsHelper.logClickEvent(
                listEmployee?.get(viewHolder.adapterPosition)?.id.toString(),
                listEmployee?.get(viewHolder.adapterPosition)?.firstName + " " + listEmployee?.get(
                    viewHolder.adapterPosition
                )?.lastName,
                "List Item"
            )
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                mActivity,
                viewHolder.profileIconEmployee,
                ViewCompat.getTransitionName(viewHolder.profileIconEmployee)!!
            )
            mContext.startActivity(
                Intent(mContext, CardDetailsActivity::class.java)
                    .putExtra(
                        ConstantUtils.INTENT_PARSE_DATA,
                        listEmployee?.get(viewHolder.adapterPosition)
                    ),
                options.toBundle()
            )
        }

        viewHolder.deleteIconCard.setOnClickListener {
//            listEmployee = ArrayList<EmployeeEntity>()
            listEmployee?.removeAt(viewHolder.adapterPosition)
            notifyDataSetChanged()
            mCommunication.respond(listEmployee?.size.toString())
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empData: EmployeeEntity? = listEmployee?.get(position)

        holder.nameEmployee.text = empData?.firstName + " " + empData?.lastName
        holder.designationEmployee.text = empData?.designation
        holder.nameCompany.text = empData?.companyInfo?.name

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_placeholder)
        requestOptions.error(R.drawable.ic_placeholder)

        Glide.with(mContext)
            .setDefaultRequestOptions(requestOptions)
            .load(empData?.card?.front?.original).into(holder.profileIconEmployee)
    }

    override fun getItemCount(): Int {
        return listEmployee!!.size
    }

    fun filterList(filterList: MutableList<EmployeeEntity>) {
        listEmployee = filterList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameEmployee: TextView = itemView.findViewById(R.id.tv_name_cardHolder)
        var designationEmployee: TextView = itemView.findViewById(R.id.tv_designation_cardHolder)
        var nameCompany: TextView = itemView.findViewById(R.id.tv_companyName_cardHolder)
        var cardView: CardView = itemView.findViewById(R.id.cv_card_cardHolder)
        var profileIconEmployee: ImageView = itemView.findViewById(R.id.iv_icon_cardHolder)
        var deleteIconCard: ImageButton = itemView.findViewById(R.id.ib_delete_cardHolder)
    }

    interface FragmentCommunication {
        fun respond(size: String)
    }
}