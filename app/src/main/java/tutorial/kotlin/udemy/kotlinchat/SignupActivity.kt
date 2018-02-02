package tutorial.kotlin.udemy.kotlinchat

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

/**
 * Created by Md. Sifat-Ul Haque on 2/2/2018.
 */

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var random: Random
    lateinit var userAvatar: String
    lateinit var avatarColor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initVariable()
        initListeners()
    }

    private fun initVariable() {
        random = Random()
        userAvatar = "profiledefault"
        avatarColor = "[0.5, 0.5, 0.5, 1]"
    }

    private fun initListeners() {
        btn_create_account.setOnClickListener(this)
        btn_generate_color.setOnClickListener(this)
        iv_user_avatar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when (id) {
            R.id.btn_create_account ->
                createAccount()
            R.id.iv_user_avatar ->
                changeUserAvatar()
            R.id.btn_generate_color ->
                generateBackgroundColor()
        }
    }

    private fun generateBackgroundColor() {
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        iv_user_avatar.setBackgroundColor(Color.rgb(r, g, b))
        avatarColor = "[${r / 255.0f}, ${g / 255.0f}, ${b / 255.0f}, 1]"
        println(avatarColor)
    }

    private fun changeUserAvatar() {
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)
        if (color == 1) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        iv_user_avatar.setImageResource(resourceId)
    }

    private fun createAccount() {

    }

}
