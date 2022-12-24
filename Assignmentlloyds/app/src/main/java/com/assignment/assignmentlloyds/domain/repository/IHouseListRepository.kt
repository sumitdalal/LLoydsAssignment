package com.assignment.assignmentlloyds.domain.repository

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import kotlinx.coroutines.flow.Flow

interface IHouseListRepository {
    operator fun invoke(): Flow<BaseModelInfo<List<HouseListInfo>>>
}