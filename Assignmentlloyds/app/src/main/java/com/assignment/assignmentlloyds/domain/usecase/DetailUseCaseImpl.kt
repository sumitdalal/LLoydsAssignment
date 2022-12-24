package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.data.repository.HouseListRepositoryImpl
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IDetailRepository
import com.assignment.assignmentlloyds.domain.repository.IHouseListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCaseImpl @Inject constructor(
    private val iDetailRepository: IDetailRepository
) : IDetailUseCase {

    override fun invoke(slug:String): Flow<BaseModelInfo<DetailInfo>> {
        return iDetailRepository.invoke(slug)
    }
}