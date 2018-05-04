package tutorial.kotlin.udemy.kotlinchat.network.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Oshan on 5/3/18.
 */
data class Message(@SerializedName("messageBody") val message: String,
                   @SerializedName("userName") val username: String,
                   @SerializedName("channelId") val channelId: String,
                   @SerializedName("userId") val userId: String,
                   @SerializedName("userAvatar") val avatar: String,
                   @SerializedName("userAvatarColor") val avatarColor: String,
                   @SerializedName("_id") val id: String,
                   @SerializedName("timeStamp") val timestamp: String)