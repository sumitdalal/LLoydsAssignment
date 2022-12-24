package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import kotlinx.coroutines.flow.Flow

interface IHouseListUseCase {
    operator suspend fun invoke(): Flow<BaseModelInfo<List<HouseListInfo>>>
}