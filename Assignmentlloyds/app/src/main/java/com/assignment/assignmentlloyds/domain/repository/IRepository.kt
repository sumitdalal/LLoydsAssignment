package com.assignment.assignmentlloyds.domain.repository

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import kotlinx.coroutines.flow.Flow

interface IRepository {
     fun getHouseList(): Flow<BaseModelInfo<List<HouseListInfo>>>
     fun getHouseDetail(slug:String): Flow<BaseModelInfo<DetailInfo>>
}