package tutorial.kotlin.udemy.kotlinchat

import tutorial.kotlin.udemy.kotlinchat.network.models.AddUserRequestModel
import tutorial.kotlin.udemy.kotlinchat.network.models.UserLoginModel

/**
 * Created by Oshan on 5/2/18.
 */
object AuthService {
    var isLoggedIn = false
    lateinit var userAuthData : UserLoginModel
    lateinit var userModel: AddUserRequestModel
}