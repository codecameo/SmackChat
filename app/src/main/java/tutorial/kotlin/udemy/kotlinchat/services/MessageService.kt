package tutorial.kotlin.udemy.kotlinchat.services

import tutorial.kotlin.udemy.kotlinchat.network.models.Channel
import tutorial.kotlin.udemy.kotlinchat.network.models.Message

/**
 * Created by Oshan on 5/2/18.
 */
object MessageService {
    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()
}