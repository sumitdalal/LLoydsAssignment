package com.assignment.assignmentlloyds.data.mapper

import com.assignment.assignmentlloyds.data.response.DetailResponse
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import javax.inject.Inject


class Mapper @Inject constructor() {
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

    fun mapDetail(detailResponse: List<DetailResponse>): DetailInfo {
        var detailInfo: DetailInfo? = null;
        detailResponse?.map { detailItem ->
            with(detailItem) {
                detailInfo = DetailInfo(
                    houseName = detailItem.houseName,
                    slug = detailItem.slug
                )
            }
        }
        return detailInfo?:DetailInfo()
    }

}