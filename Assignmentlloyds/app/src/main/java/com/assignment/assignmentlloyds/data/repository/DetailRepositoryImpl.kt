package com.assignment.assignmentlloyds.data.repository

import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.repository.IDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val iApiService: IApiService,
    private val mapper: Mapper
) : IDetailRepository {

    override fun invoke(slug:String): Flow<BaseModelInfo<DetailInfo>> {
        return flow {
            try {
                val responseData = iApiService.getDetail(slug)
                val detailIfoData = mapper.mapDetail(responseData)
                emit(BaseModelInfo.OnSuccess(detailIfoData))
            } catch (e: Exception) {
                emit(BaseModelInfo.OnFailure(e))
            }
        }
    }
}
