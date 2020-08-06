package com.vmyan.myantrip.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.vmyan.myantrip.utils.Constants

@Entity(tableName = Constants.WORD_TABLE_NAME)
data class Word(

    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("eng")
    val eng: String,
    @SerializedName("myn")
    val myn: String,
    @SerializedName("eng_file")
    val eng_file: String,
    @SerializedName("myn_file")
    val myn_file: String
)
