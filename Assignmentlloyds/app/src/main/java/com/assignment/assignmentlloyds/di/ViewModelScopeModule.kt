package com.assignment.assignmentlloyds.di

import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.repository.DetailRepositoryImpl
import com.assignment.assignmentlloyds.data.repository.HouseListRepositoryImpl
import com.assignment.assignmentlloyds.domain.repository.IDetailRepository
import com.assignment.assignmentlloyds.domain.repository.IHouseListRepository
import com.assignment.assignmentlloyds.domain.usecase.DetailUseCaseImpl
import com.assignment.assignmentlloyds.domain.usecase.IHouseListUseCase
import com.assignment.assignmentlloyds.domain.usecase.HouseListUseCaseImpl
import com.assignment.assignmentlloyds.domain.usecase.IDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelScopeModule {
    @Provides
    fun getQuoteUseCase(iHouseListRepository: IHouseListRepository): IHouseListUseCase =
        HouseListUseCaseImpl(iHouseListRepository)

    @Provides
    fun getQuoteRepo(apiService: IApiService, mapper: Mapper): IHouseListRepository =
        HouseListRepositoryImpl(apiService, mapper)

    @Provides
    fun getDetailUseCase(iDetailRepository: IDetailRepository): IDetailUseCase =
        DetailUseCaseImpl(iDetailRepository)

    @Provides
    fun getDetailRepo(apiService: IApiService, mapper: Mapper): IDetailRepository =
        DetailRepositoryImpl(apiService, mapper)



}