package com.vmyan.myantrip.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vmyan.myantrip.utils.Constants

@Entity(tableName = Constants.PROFILE_POST_TABLE_NAME)
data class ProfilePost(

    @PrimaryKey
    @SerializedName("post_id")
    val post_id: Int,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profile")
    val profile: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("post_image_1")
    val post_image_1: String,
    @SerializedName("post_image_2")
    val post_image_2: String,
    @SerializedName("post_image_3")
    val post_image_3: String,
    @SerializedName("post_image_4")
    val post_image_4: String,
    @SerializedName("post_image_5")
    val post_image_5: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("post_like")
    val post_like: String,
    @SerializedName("post_view")
    val post_view: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String

)