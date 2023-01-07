package com.assignment.assignmentlloyds.data.repository

import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.domain.mapper.DetailMapper
import com.assignment.assignmentlloyds.domain.mapper.ListMapper
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val iApiService: IApiService,
    private val listMapper: ListMapper,
    private val detailMapper: DetailMapper
) : IRepository {


    override fun getHouseList(): Flow<BaseModelInfo<List<HouseListInfo>>> {
        return flow {
            try {
                val responseData = iApiService.getHouseList()
                val houseInfoData = listMapper.map(responseData)
                emit(BaseModelInfo.OnSuccess(houseInfoData))
            } catch (e: Exception) {
                emit(BaseModelInfo.OnFailure(e))
            }
        }
    }

    override fun getHouseDetail(slug: String): Flow<BaseModelInfo<DetailInfo>> {
        return flow {
            try {
                val responseData = iApiService.getDetail(slug)
                val detailIfoData = detailMapper.map(responseData)
                emit(BaseModelInfo.OnSuccess(detailIfoData))
            } catch (e: Exception) {
                emit(BaseModelInfo.OnFailure(e))
            }
        }
    }


}
