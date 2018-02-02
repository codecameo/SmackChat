package tutorial.kotlin.udemy.kotlinchat

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initListeners()

    }

    private fun initListeners() {
        btn_add_channel.setOnClickListener(this)
        btn_nav_bar_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_nav_bar_login ->
                showLogin()
            R.id.btn_add_channel ->
                addChannel()
        }
    }

    private fun addChannel() {

    }

    private fun showLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
