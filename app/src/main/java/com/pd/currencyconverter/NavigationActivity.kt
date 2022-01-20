package com.pd.currencyconverter

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.pd.currencyconverter.databinding.ActivityNavigationBinding
import com.pd.currencyconverter.ui.calculator.CalculatorFragment
import com.pd.currencyconverter.ui.currencyconverter.CurrencyConverterFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var drawerLayout: DrawerLayout

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavigation.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        drawerLayout = binding.drawerLayout

        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_currencyConverter, R.id.nav_calculator
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
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

        //Initializing first fragment
        val fragmentClass: Class<*> = CurrencyConverterFragment::class.java
        val fragment: Fragment = fragmentClass.newInstance() as Fragment
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_navigation, fragment).commit()


        if (savedInstanceState == null) {
            binding.navView.menu.performIdentifierAction(R.id.nav_currencyConverter, 0)
            title = navView.menu.getItem(0).title
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = null
        val fragmentClass: Class<*> = when (menuItem.itemId) {
            R.id.nav_currencyConverter -> CurrencyConverterFragment::class.java
            R.id.nav_calculator -> CalculatorFragment::class.java
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


}