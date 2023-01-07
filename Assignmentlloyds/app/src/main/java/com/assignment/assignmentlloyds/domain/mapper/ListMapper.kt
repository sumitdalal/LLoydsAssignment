package com.assignment.assignmentlloyds.domain.mapper

import com.assignment.assignmentlloyds.data.response.HouseListResponse
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import javax.inject.Inject


class ListMapper @Inject constructor() {
    fun map(houseListResponse: List<HouseListResponse>): List<HouseListInfo> {
        return houseListResponse.map { houseItem ->
            with(houseItem) {
                HouseListInfo(
                    houseName = houseItem.houseName,
                    slug = houseItem.slug
                )
            }
        }
    }



}