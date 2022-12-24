package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.usecase.IHouseListUseCase
import com.assignment.assignmentlloyds.presentation.viewmodel.viewstate.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseListViewModel @Inject constructor(
    private val useCase: IHouseListUseCase,
) : ViewModel() {
    private val _houseListFlow: MutableStateFlow<ViewState<List<HouseListInfo>>> by lazy {
        MutableStateFlow(ViewState.Loading(true))
    }

    fun getHouses(): StateFlow<ViewState<List<HouseListInfo>>> = _houseListFlow


    fun fetchHouseNames() {
        viewModelScope.launch {
            useCase().collect {
                when (it) {
                    is BaseModelInfo.OnSuccess -> _houseListFlow.value = ViewState.Success(it.data)
                    is  BaseModelInfo.OnFailure -> _houseListFlow.value =ViewState.Failure(it.throwable)
                }
            }
        }
    }

}