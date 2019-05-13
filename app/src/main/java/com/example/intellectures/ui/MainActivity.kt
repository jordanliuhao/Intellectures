package com.example.intellectures.ui

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import com.example.intellectures.R
import com.example.intellectures.ui.conferences.ConferencesFragment
import com.example.intellectures.ui.view.EndDrawerToggle
import dagger.android.AndroidInjection
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import android.support.v4.app.Fragment
import android.view.View
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import android.support.v4.view.ViewCompat.setFitsSystemWindows
import com.example.intellectures.ui.gps.GpsFragment
import com.example.intellectures.ui.video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView, HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var drawerToggle: EndDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initNavigationDrawer()
        initMain()
        presenter.attach(this)
    }

    private fun initNavigationDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.appbar)

        drawerToggle = EndDrawerToggle(this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);

        navigation.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            when(id) {
                R.id.geo_location -> toGeoLocation()
                else -> { }
            }
            true
        }
    }

    private fun initMain() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_container, ConferencesFragment.newInstance())
            .commit()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
    }

    private fun hideSystemUI() {
        setFitsSystemWindows(findViewById(R.id.main_container), true)
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
        supportActionBar?.hide()
    }

    private fun showSystemUI() {
        setFitsSystemWindows(findViewById(R.id.main_container), false)
        window.decorView.systemUiVisibility = (
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        supportActionBar?.show()
    }

    fun toGeoLocation() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GpsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }


}
