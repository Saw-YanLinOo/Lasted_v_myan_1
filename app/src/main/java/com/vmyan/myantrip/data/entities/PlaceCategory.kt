package com.vmyan.myantrip.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vmyan.myantrip.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.PLACECATEGORY_TABLE_NAME)
data class PlaceCategory(
    @PrimaryKey
    @SerializedName("cat_id")
    var cat_id: Int,
    @SerializedName("cat_name")
    var cat_name: String,
    @SerializedName("cat_img_url")
    var cat_img_url: String

)