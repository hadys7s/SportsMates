package com.example.sportsmates.chatbot.model


import com.google.gson.annotations.SerializedName


data class Nutro(
    @SerializedName("foods") var foods: List<Foods>
)

data class Metadata(

    @SerializedName("is_raw_food") var isRawFood: Boolean

)

data class FullNutrients(
    @SerializedName("attr_id") var attrId: Int,
    @SerializedName("value") var value: Double
)

data class Tags(
    @SerializedName("item") var item: String,
    @SerializedName("measure") var measure: String,
    @SerializedName("quantity") var quantity: String,
    @SerializedName("tag_id") var tagId: Int
)

data class AltMeasures(
    @SerializedName("serving_weight") var servingWeight: Double,
    @SerializedName("measure") var measure: String,
    @SerializedName("seq") var seq: Int,
    @SerializedName("qty") var qty: Double
)

data class Photo(
    @SerializedName("thumb") var thumb: String,
    @SerializedName("highres") var highres: String
)

data class Foods(
    @SerializedName("food_name") var foodName: String,
    @SerializedName("brand_name") var brandName: String,
    @SerializedName("serving_qty") var servingQty: Int,
    @SerializedName("serving_unit") var servingUnit: String,
    @SerializedName("serving_weight_grams") var servingWeightGrams: Double,
    @SerializedName("nf_calories") var nfCalories: Double,
    @SerializedName("nf_total_fat") var nfTotalFat: Double,
    @SerializedName("nf_saturated_fat") var nfSaturatedFat: Double,
    @SerializedName("nf_cholesterol") var nfCholesterol: Double,
    @SerializedName("nf_sodium") var nfSodium: Double,
    @SerializedName("nf_total_carbohydrate") var nfTotalCarbohydrate: Double,
    @SerializedName("nf_dietary_fiber") var nfDietaryFiber: Double,
    @SerializedName("nf_sugars") var nfSugars: Double,
    @SerializedName("nf_protein") var nfProtein: Double,
    @SerializedName("nf_potassium") var nfPotassium: Double,
    @SerializedName("nf_p") var nfP: Double,
    @SerializedName("full_nutrients") var fullNutrients: List<FullNutrients>,
    @SerializedName("nix_brand_name") var nixBrandName: String,
    @SerializedName("nix_brand_id") var nixBrandId: String,
    @SerializedName("nix_item_name") var nixItemName: String,
    @SerializedName("nix_item_id") var nixItemId: String,
    @SerializedName("upc") var upc: String,
    @SerializedName("consumed_at") var consumedAt: String,
    @SerializedName("metadata") var metadata: Metadata,
    @SerializedName("source") var source: Int,
    @SerializedName("ndb_no") var ndbNo: Int,
    @SerializedName("tags") var tags: Tags,
    @SerializedName("alt_measures") var altMeasures: List<AltMeasures>,
    @SerializedName("lat") var lat: String,
    @SerializedName("lng") var lng: String,
    @SerializedName("meal_type") var mealType: Int,
    @SerializedName("photo") var photo: Photo
)

fun Foods.toUiModel(): FoodItemUIModel {
    return FoodItemUIModel(
        foodName = foodName,
        calories = nfCalories,
        servingQuantity = servingQty,
        servingUnit = servingUnit,
        protein = nfProtein,
        sugar = nfSugars,
        carbohydrates = nfTotalCarbohydrate,
        image = photo.thumb
    )
}

class FoodItemUIModel(
    val foodName: String?,
    val calories: Double?,
    val servingQuantity: Int,
    val servingUnit: String?,
    val protein: Double?,
    val sugar: Double,
    val carbohydrates: Double,
    val image: String
)
