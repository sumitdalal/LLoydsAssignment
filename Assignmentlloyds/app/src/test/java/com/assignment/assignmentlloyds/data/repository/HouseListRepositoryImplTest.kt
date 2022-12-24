package com.assignment.assignmentlloyds.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.network.IApiService
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import com.assignment.assignmentlloyds.domain.model.BaseModelInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class HouseListRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()

    private lateinit var houseListRepositoryImpl: HouseListRepositoryImpl


    private  var dataService: IApiService = mockk()

    private  var mapper: Mapper = mockk()



    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        houseListRepositoryImpl = HouseListRepositoryImpl(dataService, mapper)
    }

    @Test
    fun testAPIHouseListSuccess() {
        runBlockingTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns true
            val houseListResponse = mockk<List<HouseListResponse>>()
            coEvery { response.body() } returns houseListResponse
            coEvery { mapper.map(houseListResponse) } returns listOf(
                mockk()
            )
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals(1, houseListRepositoryImpl().first())
        }
    }

    @Test
    fun testAPIHouseListFail() {
        runBlockingTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals("error", houseListRepositoryImpl().first())
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}