package tutorial.kotlin.udemy.kotlinchat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Md. Sifat-Ul Haque on 2/2/2018.
 */

class LoginActivity : AppCompatActivity(), View.OnClickListener {
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
}
