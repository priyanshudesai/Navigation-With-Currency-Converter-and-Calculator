package com.pd.currencyconverter.ui.cardlist

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.chip.Chip
import com.pd.currencyconverter.R
import com.pd.currencyconverter.databinding.ActivityCardDetailsBinding
import com.pd.currencyconverter.dataclass.EmployeeEntity
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper
import com.pd.currencyconverter.utils.LocaleHelper
import java.text.SimpleDateFormat
import java.util.*


class CardDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardDetailsBinding
    private var currentTheme = ConstantUtils.NORMAL
    lateinit var bitmapImage: Bitmap
//    private var currentLanguage: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        currentTheme = PreferenceManager.getDefaultSharedPreferences(this@CardDetailsActivity)
            .getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        setTheme(currentTheme)
//        currentLanguage = PreferenceManager.getDefaultSharedPreferences(this@CardDetailsActivity).getString(
//            LocaleHelper.SELECTED_LANGUAGE, "en")
//        currentLanguage?.let { LocaleHelper.setLocale(this, it) }
        LocaleHelper.setLocale(this)
        super.onCreate(savedInstanceState)

        binding = ActivityCardDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = ConstantUtils.CARD_DETAILS_TITLE

        val data: EmployeeEntity =
            intent.getSerializableExtra(ConstantUtils.INTENT_PARSE_DATA) as EmployeeEntity

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.ic_placeholder)
        requestOptions.error(R.drawable.ic_placeholder)

        Glide.with(applicationContext)
            .setDefaultRequestOptions(requestOptions)
            .load(data.card.front.original)
            .into(binding.ivProfileCardDetails)

        bitmapImage =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_placeholder)
        Glide.with(applicationContext)
            .asBitmap()
            .load(data.card.front.original)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapImage = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    bitmapImage = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.ic_placeholder
                    )
                }
            })

        binding.tvStatusCardDetails.text = data.status

        val currentDateFormat = SimpleDateFormat(ConstantUtils.CURRENT_DATE_FORMAT)
        val changedDateFormat = SimpleDateFormat(ConstantUtils.CHANGED_DATE_FORMAT)
        val date: Date = currentDateFormat.parse(data.created_at.subSequence(0, 10).toString())
        val finalDate: String = changedDateFormat.format(date)

        binding.tvCreatedAtCardDetails.text = finalDate
        binding.cardContactInfoDetails.tvNameCardDetails.text =
            (data.firstName + " " + data.lastName)
        binding.cardContactInfoDetails.tvDesignationCardDetails.text = data.designation
        binding.cardContactInfoDetails.tvDecisionMakerCardDetails.text =
            if (data.isDecisionMaker) getString(R.string.yes) else getString(R.string.no)
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
            binding.cardContactInfoDetails.tvPhoneNumberCardDetails.text = getString(R.string.NA)
        }

        val emailList = data.emails
        for (emailId in emailList) {
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
        if (emailList.isEmpty()) {
            binding.cardContactInfoDetails.tvEmailCardDetails.text = getString(R.string.NA)
        }

        binding.cardCompanyInfoDetails.tvNameCompanyDetails.text =
            if (data.companyInfo.name == null || data.companyInfo.name.isEmpty()) getString(R.string.NA) else data.companyInfo.name
        binding.cardCompanyInfoDetails.tvIndustryCompanyDetails.text =
            if (data.companyInfo.industryName == null || data.companyInfo.industryName.isEmpty()) getString(
                R.string.NA
            ) else data.companyInfo.industryName
        binding.cardCompanyInfoDetails.tvTypeCompanyDetails.text =
            if (data.type == null || data.type.isEmpty()) getString(R.string.NA) else data.type
        binding.cardCompanyInfoDetails.tvPhoneNumberCompanyDetails.text =
            if (data.companyInfo.phoneNumber == null || data.companyInfo.phoneNumber.isEmpty()) getString(
                R.string.NA
            ) else data.companyInfo.phoneNumber
        binding.cardCompanyInfoDetails.tvWebsiteCompanyDetails.text =
            if (data.companyInfo.website == null || data.companyInfo.website.isEmpty()) getString(
                R.string.NA
            ) else data.companyInfo.website
        binding.cardCompanyInfoDetails.tvAddressCompanyDetails.text =
            if (data.companyInfo.address == null || data.companyInfo.address.toString()
                    .isEmpty()
            ) getString(R.string.NA) else data.companyInfo.address as CharSequence?
                ?: getString(R.string.NA)
        binding.cardCompanyInfoDetails.tvCampaignEmailCompanyDetails.text =
            if (data.companyInfo.campaignEmail == null || data.companyInfo.campaignEmail.isEmpty()) getString(
                R.string.NA
            ) else data.companyInfo.campaignEmail


        val convertedTagsArray: List<String> = data.tags.split(",")
        if (convertedTagsArray.lastIndex != 0) {
            convertedTagsArray.map { text ->
                val chip = Chip(this)
                chip.text = text
                chip.chipBackgroundColor = getColorStateList(R.color.circle_color_background_text)
                val typeface = ResourcesCompat.getFont(applicationContext, R.font.black_ops_one)
                chip.typeface = typeface
                chip.setTextColor(getColor(R.color.whiteColor))
                chip.textSize = 14F
                binding.cardCompanyInfoDetails.llTagsCompanyDetails.addView(chip)
            }
        } else {
            binding.cardCompanyInfoDetails.textView14.text =
                binding.cardCompanyInfoDetails.textView14.text.toString() + "\n" + getString(R.string.no_tags)
        }

        binding.ivProfileCardDetails.setOnClickListener {
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                binding.ivProfileCardDetails,
                ViewCompat.getTransitionName(binding.ivProfileCardDetails)!!
            )
            ConstantUtils.bitmap = bitmapImage
            FirebaseAnalyticsHelper.logClickEvent(
                data.id.toString(),
                data.firstName + " " + data.lastName,
                "View Image"
            )
            startActivity(
                Intent(this@CardDetailsActivity, CardImageViewActivity::class.java),
                options.toBundle()
            )
        }

        binding.btnBackCardDetails.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("CardDetailsScreen", "CardDetailsActivity")
    }

}