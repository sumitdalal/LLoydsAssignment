package com.assignment.assignmentlloyds.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import com.assignment.assignmentlloyds.domain.model.DetailInfo
import com.assignment.assignmentlloyds.domain.model.HouseListInfo
import com.assignment.assignmentlloyds.domain.repository.IDetailRepository
import com.assignment.assignmentlloyds.domain.repository.IHouseListRepository
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailUseCaseImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private var repository: IDetailRepository = mockk()

    private lateinit var useCase: DetailUseCaseImpl
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = DetailUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataSuccess() = runBlocking {
        val flow = flow {
            emit(BaseModelInfo.OnSuccess(DetailInfo()))
        }
        Mockito.`when`(repository.invoke("slug")).thenReturn(flow)
        val response = useCase.invoke("slug").first()
        Assert.assertTrue(response is BaseModelInfo.OnSuccess<*>)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFetchDataFail() = runBlocking {
        val flow = flow {
            emit(BaseModelInfo.OnFailure(Throwable("Error")))
        }
        Mockito.`when`(repository.invoke("slug")).thenReturn(flow)
        val response = useCase.invoke("slug").first()
        Assert.assertTrue(response is BaseModelInfo.OnFailure)
    }


}