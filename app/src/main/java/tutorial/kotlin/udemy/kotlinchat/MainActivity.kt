package tutorial.kotlin.udemy.kotlinchat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_channel_dialog.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tutorial.kotlin.udemy.kotlinchat.network.ApiClient
import tutorial.kotlin.udemy.kotlinchat.network.models.Channel
import tutorial.kotlin.udemy.kotlinchat.services.MessageService

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val socket = IO.socket(BASE_DATA_URL)
    private val onNewChannel = Emitter.Listener { args ->
        runOnUiThread {
            val newChannel = Channel(args[0] as String, args[1] as String, args[2] as String)
            MessageService.channels.add(newChannel)
            channelAdapter.notifyDataSetChanged()
        }
    }
    private lateinit var channelAdapter: ArrayAdapter<Channel>

    private val userDataChangeReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            tv_nav_username.text = AuthService.userModel.name
            tv_nav_user_mail.text = AuthService.userModel.email
            btn_nav_bar_login.text = "Logout"
            val resId = resources.getIdentifier(AuthService.userModel.avatarName, "drawable", packageName);
            iv_nav_user_image.setImageResource(resId)
            iv_nav_user_image.setBackgroundColor(UserDataService.returnAvatarColor(AuthService.userModel.avatarColor))
            getAllChannels()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setAdapter()

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initListeners()
        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver, IntentFilter(BROADCAST_USER_DATA_CHANGED))
        socket.connect()
        socket.on("channelCreated", onNewChannel)

    }

    private fun setAdapter() {
        channelAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MessageService.channels)
        list_channel.adapter = channelAdapter
    }

    private fun getAllChannels() {
        if (AuthService.isLoggedIn) {
            val apiClient = ApiClient.getApiService()
            apiClient.getAllChannels("Bearer ${AuthService.userAuthData.token}").enqueue(object : Callback<ArrayList<Channel>> {
                override fun onResponse(call: Call<ArrayList<Channel>>?, response: Response<ArrayList<Channel>>?) {
                    if (response!!.isSuccessful) {
                        MessageService.channels.clear()
                        MessageService.channels.addAll(response.body()!!)
                        channelAdapter.notifyDataSetChanged()
                    } else {
                        showErrorMessage()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Channel>>?, t: Throwable?) {
                    showErrorMessage()
                }
            })
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userDataChangeReceiver)
        socket.disconnect()
    }

    private fun initListeners() {
        btn_add_channel.setOnClickListener(this)
        btn_nav_bar_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_nav_bar_login ->
                if(AuthService.isLoggedIn) showLogout() else showLogin()
            R.id.btn_add_channel ->
                addChannel()
        }
    }

    private fun showLogout() {
        UserDataService.logout()
        tv_nav_user_mail.text = ""
        tv_nav_username.text = "Login"
        iv_nav_user_image.setImageResource(R.drawable.profiledefault)
        iv_nav_user_image.setBackgroundColor(Color.TRANSPARENT)
        btn_nav_bar_login.text = "Login"
    }

    private fun addChannel() {
        if (!AuthService.isLoggedIn) return
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null)
        builder.setView(dialogView)
                .setPositiveButton("Add"){ dialog, which ->
                    val channelName = dialogView.addChannelNameTxt.text.toString()
                    val channelDes = dialogView.addChannelDescTxt.text.toString()
                    socket.emit("newChannel", channelName, channelDes)
                }
                .setNegativeButton("Cancel"){ dialog, which ->

                }
                .show()
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
