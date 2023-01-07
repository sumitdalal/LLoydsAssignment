package com.assignment.assignmentlloyds.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(
   @SerializedName("name") val houseName: String,
   @SerializedName("slug") val slug: String,
)
