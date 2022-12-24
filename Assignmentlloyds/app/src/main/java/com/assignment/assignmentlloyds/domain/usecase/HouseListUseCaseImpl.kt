package com.assignment.assignmentlloyds.domain.usecase

import com.assignment.assignmentlloyds.data.repository.HouseListRepositoryImpl
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IHouseListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HouseListUseCaseImpl @Inject constructor(
    private val houseListRepository: IHouseListRepository
) : IHouseListUseCase {

    override suspend fun invoke(): Flow<BaseModelInfo<List<HouseListInfo>>> {
        return houseListRepository.invoke()
    }
}