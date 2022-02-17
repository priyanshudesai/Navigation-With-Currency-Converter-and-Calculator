package com.pd.currencyconverter

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.pd.currencyconverter.databinding.ActivityNavigationBinding
import com.pd.currencyconverter.services.MyBroadcastReceiver
import com.pd.currencyconverter.services.TimerService
import com.pd.currencyconverter.ui.alarm.AlarmFragment
import com.pd.currencyconverter.ui.calculator.CalculatorFragment
import com.pd.currencyconverter.ui.cardlist.CardListFragment
import com.pd.currencyconverter.ui.currencyconverter.CurrencyConverterFragment
import com.pd.currencyconverter.ui.download.DownloadFragment
import com.pd.currencyconverter.ui.settings.SettingsFragment
import com.pd.currencyconverter.ui.timer.TimerFragment
import com.pd.currencyconverter.utils.ConstantUtils
import com.pd.currencyconverter.utils.FirebaseAnalyticsHelper
import com.pd.currencyconverter.utils.LocaleHelper


class NavigationActivity : AppCompatActivity() {

    //    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var drawerLayout: DrawerLayout
    private var drawerToggle: ActionBarDrawerToggle? = null
    private var currentTheme = ConstantUtils.NORMAL

    companion object {
        var time = 0.0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        currentTheme = PreferenceManager.getDefaultSharedPreferences(this@NavigationActivity)
            .getInt(ConstantUtils.KEY_THEME, ConstantUtils.NORMAL)
        setTheme(currentTheme)
//        currentLanguage = PreferenceManager.getDefaultSharedPreferences(this@NavigationActivity).getString(LocaleHelper.SELECTED_LANGUAGE, "en").toString()
        LocaleHelper.setLocale(this@NavigationActivity)

        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_currencyConverter, R.id.nav_calculator, R.id.nav_alarm, R.id.nav_settings
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarNavigation.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        drawerToggle!!.isDrawerIndicatorEnabled = true
        drawerToggle!!.syncState()
        drawerLayout.addDrawerListener(drawerToggle!!)

//        //Initializing first fragment
//        val fragmentClass: Class<*> = CurrencyConverterFragment::class.java
//        val fragment: Fragment = fragmentClass.newInstance() as Fragment
//        val fragmentManager: FragmentManager = supportFragmentManager
//        fragmentManager.beginTransaction()
//            .replace(R.id.nav_host_fragment_content_navigation, fragment).commit()

        if (savedInstanceState == null) {
            binding.navView.menu.performIdentifierAction(R.id.nav_currencyConverter, 0)
//            title = navView.menu.getItem(0).title
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.e("TAG", token.toString())
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(ConstantUtils.KEY_TOKEN, token.toString()).apply()
        })

        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = null
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        val fragmentClass: Class<*> = when (menuItem.itemId) {
            R.id.nav_currencyConverter -> CurrencyConverterFragment::class.java
            R.id.nav_calculator -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                CalculatorFragment::class.java
            }
            R.id.nav_cardHolder -> CardListFragment::class.java
            R.id.nav_alarm -> AlarmFragment::class.java
            R.id.nav_timer -> TimerFragment::class.java
            R.id.nav_download -> DownloadFragment::class.java
            R.id.nav_settings -> SettingsFragment::class.java
            else -> CurrencyConverterFragment::class.java
        }
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_navigation, fragment).commit()
        }
        menuItem.isChecked = true
        title = menuItem.title
        drawerLayout.closeDrawers()
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalyticsHelper.logScreenEvent("NavigationScreen", "NavigationActivity")
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
//            binding.tvCountdownTimer.text = TimerService.getTimeStringFromDouble(NavigationActivity.time)
//            binding.btnStartTimer.text = "Stop"
//            timerStarted = true
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }

    override fun onDestroy() {
        val check = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
        if (check) {
            val timer = TimerService()
            if (!isMyServiceRunning(timer.javaClass)) {
                val broadcastIntent = Intent()
                broadcastIntent.action = "restartservice"
                broadcastIntent.setClass(this, MyBroadcastReceiver::class.java)
                broadcastIntent.putExtra("time", NavigationActivity.time)
                sendBroadcast(broadcastIntent)
                Log.e("Timer Service", "Frag onDestroy: Broadcast")
            }
            Log.e("Timer Service", "Frag onDestroy: Already service Broadcast")
        } else {
            Log.e("Timer Service", "Frag onDestroy: Check OFF")
        }
        super.onDestroy()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        val check = PreferenceManager.getDefaultSharedPreferences(applicationContext)
//            .getBoolean(ConstantUtils.KEY_TIMER_CHECK, false)
//        if (check) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                applicationContext.startForegroundService(
//                    Intent(
//                        applicationContext,
//                        TimerService::class.java
//                    )
//                )
//            } else {
//                applicationContext.startService(
//                    Intent(
//                        applicationContext,
//                        TimerService::class.java
//                    )
//                )
//            }
//        }
//    }

}