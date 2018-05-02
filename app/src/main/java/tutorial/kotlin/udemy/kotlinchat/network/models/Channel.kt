package tutorial.kotlin.udemy.kotlinchat.network.models

/**
 * Created by Oshan on 5/2/18.
 */
data class Channel(var name: String, var description: String, var _id: String) {
    override fun toString(): String {
        return "#$name"
    }
}