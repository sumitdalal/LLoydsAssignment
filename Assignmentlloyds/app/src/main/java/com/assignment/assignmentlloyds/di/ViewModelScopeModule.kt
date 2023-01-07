package com.assignment.assignmentlloyds.di

import com.assignment.assignmentlloyds.domain.mapper.ListMapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.repository.RepositoryImpl
import com.assignment.assignmentlloyds.domain.mapper.DetailMapper
import com.assignment.assignmentlloyds.domain.repository.IRepository
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
    fun getQuoteUseCase(iRepository: IRepository): IHouseListUseCase =
        HouseListUseCaseImpl(iRepository)

    @Provides
    fun getDetailUseCase(iRepository: IRepository): IDetailUseCase =
        DetailUseCaseImpl(iRepository)

    @Provides
    fun getRepository(apiService: IApiService, listMapper: ListMapper, detailMapper: DetailMapper): IRepository =
        RepositoryImpl(apiService, listMapper,detailMapper)



}