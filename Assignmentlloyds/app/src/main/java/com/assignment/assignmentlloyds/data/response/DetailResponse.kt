package com.assignment.assignmentlloyds.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(
   @SerializedName("name") var houseName: String,
   @SerializedName("slug") var slug: String,
)
