package com.assignment.assignmentlloyds.data.repository

import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IHouseListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HouseListRepositoryImpl @Inject constructor(
    private val iApiService: IApiService,
    private val mapper: Mapper
) : IHouseListRepository {

    override fun invoke(): Flow<BaseModelInfo<List<HouseListInfo>>> {
        return flow {
            try {
                val responseData = iApiService.getHouseList()
                val houseInfoData = mapper.map(responseData)
                emit(BaseModelInfo.OnSuccess(houseInfoData))
            } catch (e: Exception) {
                emit(BaseModelInfo.OnFailure(e))
            }
        }
    }
}
