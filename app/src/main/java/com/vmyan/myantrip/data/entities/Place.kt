package com.vmyan.myantrip.data.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.vmyan.myantrip.utils.Constants

@Entity(tableName = Constants.PLACE_TABLE_NAME)
data class Place(

    @PrimaryKey
    @SerializedName("place_id")
    var place_id: Int,
    @SerializedName("cat_id")
    var cat_id: Int,
    @SerializedName("place_sub_cat1")
    var place_sub_cat1: String,
    @SerializedName("place_sub_cat2")
    var place_sub_cat2: String,
    @SerializedName("place_sub_cat3")
    var place_sub_cat3: String,
    @SerializedName("place_name")
    var place_name: String,
    @SerializedName("review_id")
    var review_id: Int,
    @SerializedName("place_country")
    var place_country: String,
    @SerializedName("place_state")
    var place_state: String,
    @SerializedName("place_cityOrTownship")
    var place_cityOrTownship: String,
    @SerializedName("place_address")
    var place_address: String,
    @SerializedName("place_latitude")
    var place_latitude: String,
    @SerializedName("place_longitude")
    var place_longitude: String,
    @SerializedName("place_info")
    var place_info: String,
    @SerializedName("place_history")
    var place_history: String,
    @SerializedName("place_buildDate")
    var place_buildDate: String,
    @SerializedName("place_founder")
    var place_founder: String,
    @SerializedName("place_mainImg")
    var place_mainImg: String,
    @SerializedName("place_gallery1")
    var place_gallery1: String,
    @SerializedName("place_gallery2")
    var place_gallery2: String,
    @SerializedName("place_gallery3")
    var place_gallery3: String,
    @SerializedName("place_gallery4")
    var place_gallery4: String,
    @SerializedName("place_gallery5")
    var place_gallery5: String,
    @SerializedName("place_gallery6")
    var place_gallery6: String,
    @SerializedName("place_gallery7")
    var place_gallery7: String,
    @SerializedName("place_gallery8")
    var place_gallery8: String,
    @SerializedName("place_gallery9")
    var place_gallery9: String,
    @SerializedName("place_gallery10")
    var place_gallery10: String,
    @SerializedName("place_sliderImg1")
    var place_sliderImg1: String,
    @SerializedName("place_sliderImg2")
    var place_sliderImg2: String,
    @SerializedName("place_sliderImg3")
    var place_sliderImg3: String,
    @SerializedName("isRecommended")
    var isRecommended: String,
    @SerializedName("cat_name")
    var cat_name: String

)