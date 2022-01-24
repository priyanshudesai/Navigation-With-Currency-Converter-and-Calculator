package com.pd.currencyconverter

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.pd.currencyconverter.dataclass.Data
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat

import com.bumptech.glide.request.RequestOptions
import com.pd.currencyconverter.databinding.ActivityCardDetailsBinding


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

        binding.tvStatusCardDetails.text = data.status
        binding.tvCreatedAtCardDetails.text = data.created_at.subSequence(0,10)
        binding.cardContactInfoDetails.tvNameCardDetails.text = data.first_name + " " + data.last_name
        binding.cardContactInfoDetails.tvDesignationCardDetails.text = data.designation
        binding.cardContactInfoDetails.tvDecisionMakerCardDetails.text =
            if(data.is_decisionmaker)  "yes" else "no"
        val phoneList = data.phones
        for (phoneNumber in phoneList){
            if(binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text.isEmpty()) {
                binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text = phoneNumber.number
                binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text = phoneNumber.type
            }else{
                binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text =
                    binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text.toString() + "\n" + phoneNumber.number
                binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text =
                    binding.cardContactInfoDetails.tvPhoneNumberTypeCardDetails.text.toString() + "\n" + phoneNumber.type
            }
        }

        val emailList = data.emails
        for (emailId in emailList){
            if(binding.cardContactInfoDetails.tvEmailCardDetails.text.isEmpty()) {
                binding.cardContactInfoDetails.tvEmailCardDetails.text = emailId.email
                binding.cardContactInfoDetails.tvEmailTypeCardDetails.text = emailId.type
            }else{
                binding.cardContactInfoDetails.tvEmailCardDetails.text =
                    binding.cardContactInfoDetails.tvEmailCardDetails.text.toString() + "\n" + emailId.email
                binding.cardContactInfoDetails.tvEmailTypeCardDetails.text =
                    binding.cardContactInfoDetails.tvEmailTypeCardDetails.text.toString() + "\n" + emailId.type
            }
        }

        binding.cardCompanyInfoDetails.tvNameCompanyDetails.text = data.company_info.name
        binding.cardCompanyInfoDetails.tvIndustryCompanyDetails.text = data.company_info.industry_name
        binding.cardCompanyInfoDetails.tvTypeCompanyDetails.text = data.type
        binding.cardCompanyInfoDetails.tvPhoneNumberCompanyDetails.text = data.company_info.phone_number
        binding.cardCompanyInfoDetails.tvWebsiteCompanyDetails.text = data.company_info.website
        binding.cardCompanyInfoDetails.tvAddressCompanyDetails.text = data.company_info.address as CharSequence?
        binding.cardCompanyInfoDetails.tvCampaignEmailCompanyDetails.text = data.company_info.campaign_email


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