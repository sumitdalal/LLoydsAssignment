package com.assignment.assignmentlloyds.data.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.assignmentlloyds.data.mapper.Mapper
import com.assignment.assignmentlloyds.data.repository.HouseListRepositoryImpl
import com.assignment.assignmentlloyds.data.response.HouseListResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

class ApiServiceTest {
    private lateinit var houseListRepo: HouseListRepositoryImpl


    private var dataService: IApiService = mockk()


    private var mapper: Mapper = mockk()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.IO)
        houseListRepo = HouseListRepositoryImpl(dataService, mapper)
    }

    @Test
    fun testAPIResourcesListSuccess() {
        runBlockingTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns true
            val houseListResponse = mockk<List<HouseListResponse>>()
            coEvery { response.body() } returns houseListResponse
            coEvery { mapper.map(houseListResponse) } returns listOf(
                mockk()
            )
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals(1, houseListRepo().first())
        }
    }

    @Test
    fun testAPIResourcesListFail() {
        runBlockingTest {
            val response = mockk<Response<List<HouseListResponse>>>()
            coEvery { response.isSuccessful } returns false
            coEvery { response.message() } returns "error"
            coEvery { dataService.getHouseList() } returns response.body()!!
            TestCase.assertEquals("error", houseListRepo().first())
        }
    }
}