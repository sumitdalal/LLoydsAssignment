package com.assignment.assignmentlloyds.data.network

import com.assignment.assignmentlloyds.data.response.DetailResponse
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiService {
    @GET("houses")
    suspend fun getHouseList(): List<HouseListResponse>

    @GET("house/{slug}")
    suspend fun getDetail(@Path("slug") slug: String): List<DetailResponse>

}