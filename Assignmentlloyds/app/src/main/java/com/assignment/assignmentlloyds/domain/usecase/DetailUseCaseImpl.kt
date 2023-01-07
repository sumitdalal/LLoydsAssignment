package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCaseImpl @Inject constructor(
    private val iRepository: IRepository
) : IDetailUseCase {

    override fun invoke(slug:String): Flow<BaseModelInfo<DetailInfo>> {
        return iRepository.getHouseDetail(slug)
    }
}