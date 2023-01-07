package com.assignment.assignmentlloyds.domain.mapper

import com.assignment.assignmentlloyds.data.response.DetailResponse
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import javax.inject.Inject


class DetailMapper @Inject constructor() {

    fun map(detailResponse: List<DetailResponse>): DetailInfo {
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