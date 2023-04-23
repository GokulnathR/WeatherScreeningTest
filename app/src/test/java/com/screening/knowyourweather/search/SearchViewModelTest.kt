package com.screening.knowyourweather.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.screening.knowyourweather.generics.Result
import com.screening.knowyourweather.network.ApiConstants
import com.screening.knowyourweather.ui.search.CityRepository
import com.screening.knowyourweather.ui.search.SearchViewModel
import com.screening.knowyourweather.ui.search.USCities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest {

    @Mock
    lateinit var cityRepository: CityRepository

    lateinit var searchViewModel: SearchViewModel

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)

        searchViewModel = SearchViewModel(cityRepository)

    }

    @Test
    fun `when fetching city list success`(){
        val list = arrayListOf(
            USCities("New York", "40.697", "-74.259"),
            USCities("New Jersey", "40.046", "-77.224"))

        runBlocking {
            Mockito.`when`(cityRepository.allCityList())
                .thenReturn(list)
            searchViewModel.cities
            dispatcher.scheduler.advanceUntilIdle()

            assert(searchViewModel.cities.value is ArrayList<USCities>)
        }
    }

}