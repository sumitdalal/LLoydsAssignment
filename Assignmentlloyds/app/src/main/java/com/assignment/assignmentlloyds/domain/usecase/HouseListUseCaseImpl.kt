package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HouseListUseCaseImpl @Inject constructor(
    private val iRepository: IRepository
) : IHouseListUseCase {

    override suspend fun invoke(): Flow<BaseModelInfo<List<HouseListInfo>>> {
        return iRepository.getHouseList()
    }
}