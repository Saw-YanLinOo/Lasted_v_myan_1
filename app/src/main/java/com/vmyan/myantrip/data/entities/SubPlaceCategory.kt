package com.vmyan.myantrip.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vmyan.myantrip.utils.Constants

@Entity(tableName = Constants.SUBPLACECATEGORY_TABLE_NAME)
data class SubPlaceCategory(
    @PrimaryKey
    @SerializedName("sub_cat_id")
    var sub_cat_id: Int,
    @SerializedName("sub_cat_name")
    var sub_cat_name: String

)