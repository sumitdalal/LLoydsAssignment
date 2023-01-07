package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.usecase.IDetailUseCase
import com.assignment.assignmentlloyds.domain.usecase.IHouseListUseCase
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: IDetailUseCase,
) : ViewModel() {
    private val _detailFlow: MutableStateFlow<ViewState<DetailInfo>> by lazy {
        MutableStateFlow(ViewState.Loading(true))
    }

    val getDetail: StateFlow<ViewState<DetailInfo>> = _detailFlow


    fun fetchHouseDetail(slug:String) {
        viewModelScope.launch {
            useCase(slug).collect {
                when (it) {
                    is BaseModelInfo.OnSuccess -> _detailFlow.value = ViewState.Success(it.data)
                    is  BaseModelInfo.OnFailure -> _detailFlow.value =ViewState.Failure(it.throwable)
                }
            }
        }
    }

}