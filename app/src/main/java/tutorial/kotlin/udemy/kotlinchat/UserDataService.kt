package tutorial.kotlin.udemy.kotlinchat

import android.graphics.Color
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
        AuthService.userAuthData.token = ""
        AuthService.userAuthData.user = ""
        AuthService.isLoggedIn = false
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