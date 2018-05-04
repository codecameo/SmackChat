package tutorial.kotlin.udemy.kotlinchat.services

import android.graphics.Color
import tutorial.kotlin.udemy.kotlinchat.ChatApp
import java.util.*

/**
 * Created by Oshan on 5/2/18.
 */
object UserDataService {

    fun logout() {
        AuthService.userModel.avatarColor = ""
        AuthService.userModel.avatarName = ""
        AuthService.userModel.email = ""
        AuthService.userModel.name = ""
        ChatApp.sharedPref.authToken = ""
        ChatApp.sharedPref.userMail = ""
        ChatApp.sharedPref.isLoggedIn = false
        MessageService.channels.clear()
        MessageService.messages.clear()
    }

    fun returnAvatarColor(components: String) : Int {
        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")

        var r = 0
        var g = 0
        var b = 0

        val scanner = Scanner(strippedColor)
        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }

        return Color.rgb(r,g,b)
    }
}