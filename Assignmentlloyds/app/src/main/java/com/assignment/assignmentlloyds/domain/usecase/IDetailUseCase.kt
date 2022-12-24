package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import kotlinx.coroutines.flow.Flow

interface IDetailUseCase {
    operator fun invoke(slug:String): Flow<BaseModelInfo<DetailInfo>>
}