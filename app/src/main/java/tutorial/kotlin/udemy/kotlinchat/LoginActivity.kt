package tutorial.kotlin.udemy.kotlinchat

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tutorial.kotlin.udemy.kotlinchat.network.ApiClient
import tutorial.kotlin.udemy.kotlinchat.network.models.AddUserRequestModel
import tutorial.kotlin.udemy.kotlinchat.network.models.UserLoginModel
import tutorial.kotlin.udemy.kotlinchat.network.models.UserRegistration

/**
 * Created by Md. Sifat-Ul Haque on 2/2/2018.
 */

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "LoginActivity"

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_signup ->
                createUser()
            R.id.btn_login ->
                userLogin()
        }
    }

    private fun userLogin() {
        enableProgress(true)
        val mail = et_signin_email.text.toString()
        val pass = et_signin_password.text.toString()
        if (mail.isNotEmpty() && pass.isNotEmpty()){
            val apiService = ApiClient.getApiService()
            apiService.loginUser(UserRegistration(mail, pass)).enqueue(object : Callback<UserLoginModel> {
                override fun onResponse(call: Call<UserLoginModel>?, response: Response<UserLoginModel>?) {
                    if (response!!.isSuccessful) {
                        ChatApp.sharedPref.isLoggedIn = true
                        val userAuthModel = response.body()!!
                        ChatApp.sharedPref.authToken = userAuthModel.token
                        ChatApp.sharedPref.userMail = userAuthModel.user
                        Log.d(TAG, response.body().toString())
                        apiService.findUser("Bearer ${userAuthModel.token}", userAuthModel.user).enqueue(object : Callback<AddUserRequestModel> {
                            override fun onResponse(call: Call<AddUserRequestModel>?, response: Response<AddUserRequestModel>?) {
                                Log.d(TAG, "Url "+call!!.request().url());
                                if (response!!.isSuccessful) {
                                    AuthService.userModel = response.body()!!
                                    val userIntent = Intent(BROADCAST_USER_DATA_CHANGED)
                                    LocalBroadcastManager.getInstance(this@LoginActivity).sendBroadcast(userIntent)
                                    Log.d(TAG, response.body().toString())
                                    enableProgress(false)
                                    finish()
                                } else {
                                    showErrorMessage()
                                }
                            }

                            override fun onFailure(call: Call<AddUserRequestModel>?, t: Throwable?) {
                                showErrorMessage()
                            }
                        })
                    } else {
                        Log.d(TAG, "Error Occurred ${response.errorBody()!!.string()}")
                        showErrorMessage()
                    }
                }

                override fun onFailure(call: Call<UserLoginModel>?, t: Throwable?) {
                    showErrorMessage()
                }
            })
        }else{
            Toast.makeText(this, "Some fields are empty", Toast.LENGTH_SHORT).show()
            enableProgress(false)
        }
    }

    private fun createUser() {
        startActivity(Intent(this, SignupActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_signup.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        enableProgress(false)
    }

    private fun enableProgress(enable: Boolean) {
        pb_sign_in.visibility = if (enable) View.VISIBLE else View.GONE
        btn_login.isEnabled = !enable
        btn_signup.isEnabled = !enable
    }

}
