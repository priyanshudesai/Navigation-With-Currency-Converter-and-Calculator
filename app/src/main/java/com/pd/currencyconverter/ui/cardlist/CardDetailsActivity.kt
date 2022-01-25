package com.pd.currencyconverter.ui.cardlist

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pd.currencyconverter.R
import com.pd.currencyconverter.databinding.ActivityCardDetailsBinding
import com.pd.currencyconverter.dataclass.Data
import java.text.SimpleDateFormat
import java.util.*


class CardDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardDetailsBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Test Card"

        val data: Data = intent.getSerializableExtra("data") as Data

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_placeholder)
        requestOptions.error(R.drawable.ic_placeholder)

        Glide.with(applicationContext)
            .setDefaultRequestOptions(requestOptions)
            .load(data.card.front.original)
            .into(binding.ivProfileCardDetails)

        binding.tvStatusCardDetails.text = data.status?: "N/A"

        val currentDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val changedDateFormat = SimpleDateFormat("dd MMM yyyy")
        val date: Date = currentDateFormat.parse(data.created_at.subSequence(0,10).toString())
        val finalDate: String = changedDateFormat.format(date)

        binding.tvCreatedAtCardDetails.text = finalDate ?: "N/A"
        binding.cardContactInfoDetails.tvNameCardDetails.text = (data.first_name + " " + data.last_name) ?: "N/A"
        binding.cardContactInfoDetails.tvDesignationCardDetails.text = data.designation ?: "N/A"
        binding.cardContactInfoDetails.tvDecisionMakerCardDetails.text =
            if(data.is_decisionmaker)  "yes" else "no"
        val phoneList = data.phones
        for (phoneNumber in phoneList) {
            if (binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text.isEmpty()) {
                binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text =
                    phoneNumber.number
                binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text =
                    phoneNumber.type
            } else {
                binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text =
                    binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text.toString() + "\n" + phoneNumber.number
                binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text =
                    binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text.toString() + "\n" + phoneNumber.type
            }
        }
        if (phoneList.isEmpty()) {
            binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text = "N/A"
        }

        val emailList = data.emails
        for (emailId in emailList){
            if (binding.cardContactInfoDetails.tvEmailCardDetails.text.isEmpty()) {
                binding.cardContactInfoDetails.tvEmailCardDetails.text = emailId.email
                binding.cardContactInfoDetails.tvEmailTypeCardDetails.text = emailId.type
            } else {
                binding.cardContactInfoDetails.tvEmailCardDetails.text =
                    binding.cardContactInfoDetails.tvEmailCardDetails.text.toString() + "\n" + emailId.email
                binding.cardContactInfoDetails.tvEmailTypeCardDetails.text =
                    binding.cardContactInfoDetails.tvEmailTypeCardDetails.text.toString() + "\n" + emailId.type
            }
        }
        if (emailList.isEmpty()){
            binding.cardContactInfoDetails.tvEmailCardDetails.text = "N/A"

        }

        binding.cardCompanyInfoDetails.tvNameCompanyDetails.text =
            if(data.company_info.name == null || data.company_info.name.isEmpty()) "N/A" else data.company_info.name ?: "N/A"
        binding.cardCompanyInfoDetails.tvIndustryCompanyDetails.text =
            if(data.company_info.industry_name == null || data.company_info.industry_name.isEmpty()) "N/A" else data.company_info.industry_name ?: "N/A"
        binding.cardCompanyInfoDetails.tvTypeCompanyDetails.text =
            if(data.type == null || data.type.isEmpty()) "N/A" else data.type ?: "N/A"
        binding.cardCompanyInfoDetails.tvPhoneNumberCompanyDetails.text =
            if(data.company_info.phone_number == null || data.company_info.phone_number.isEmpty()) "N/A" else data.company_info.phone_number ?: "N/A"
        binding.cardCompanyInfoDetails.tvWebsiteCompanyDetails.text =
            if(data.company_info.website == null || data.company_info.website.isEmpty()) "N/A" else data.company_info.website ?: "N/A"
        binding.cardCompanyInfoDetails.tvAddressCompanyDetails.text =
            if(data.company_info.address == null || data.company_info.address.toString().isEmpty()) "N/A" else data.company_info.address as CharSequence? ?: "N/A"
        binding.cardCompanyInfoDetails.tvCampaignEmailCompanyDetails.text =
            if(data.company_info.campaign_email == null || data.company_info.campaign_email.isEmpty()) "N/A" else data.company_info.campaign_email ?: "N/A"


        val convertedTagsArray: List<String> = data.tags.split(",")
        if (convertedTagsArray.lastIndex != 0) {
            convertedTagsArray.map { text ->
                val lParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val tv = TextView(this)
                lParams.setMargins(8, 0, 0, 0)
                tv.setPadding(8,8,8,8)
                tv.layoutParams = lParams
                tv.text = text
                tv.background = getDrawable(R.drawable.circle_drawable_background_text)
                val typeface = ResourcesCompat.getFont(applicationContext, R.font.poppins_medium)
                tv.typeface = typeface
                tv.gravity = Gravity.CENTER
                tv.setTextColor(getColor(R.color.whiteTextColor))
                tv.textSize = 14F
                binding.cardCompanyInfoDetails.llTagsCompanyDetails.addView(tv)
            }
        }else{
            binding.cardCompanyInfoDetails.textView14.text = binding.cardCompanyInfoDetails.textView14.text.toString() + "\n NO TAGS"
        }
    }
}