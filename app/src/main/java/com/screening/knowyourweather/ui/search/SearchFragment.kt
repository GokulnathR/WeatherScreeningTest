package com.screening.knowyourweather.ui.search

import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.screening.knowyourweather.R
import com.screening.knowyourweather.base.BaseFragment
import com.screening.knowyourweather.data.AppPreference
import com.screening.knowyourweather.databinding.LayoutSearchBinding
import com.screening.knowyourweather.utils.RxJavaUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment(), SearchAdapter.CitySearchListener {
    private var _binding: LayoutSearchBinding? = null
    private var mAdapter: SearchAdapter? = null
    private var searchViewModel: SearchViewModel? = null

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutSearchBinding.inflate(inflater, container, false)
        return _binding?.getRoot()
    }

    override fun onLocationChanged(location: Location) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOncreate()
        initClicks()

    }

    private fun initClicks() {
        _binding?.idBack?.setOnClickListener {
            findNavController(requireView()).navigate(R.id.action_nav_dashboard)
        }

        _binding?.content?.setOnClickListener {
            selectedCity(appPreference.KEY_CITY, appPreference.KEY_LATITUDE, appPreference.KEY_LONGITUDE)
        }
    }

    private fun initOncreate() {

        // Recent Searches
        recentSearchVisibility()

        // Recyclerview Adapter initialization
        mAdapter = SearchAdapter(searchViewModel!!.cities.value, this)
        _binding?.itemList?.setAdapter(mAdapter)


        // Searchview Query implementation
        _binding?.searchBox?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter!!.filter.filter(newText)
                return false
            }
        })
    }

    override fun getViewModel() {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun notifyCitySearch(list: ArrayList<USCities>) {
        _binding?.itemList?.getRecycledViewPool()?.clear()
        mAdapter!!.notifyDataSetChanged()
    }

    override fun selectedCity(cityName: String, latitude: String, longitude: String) {

        // Save recent searches in Shared Preferences
        appPreference.KEY_CITY = cityName
        appPreference.KEY_LATITUDE = latitude
        appPreference.KEY_LONGITUDE = longitude
        appPreference.KEY_UPDATE_CURRENT_LOC = false

        // With the help of RxJava, passing value from [Search Fragment] to @DashboardFragment
        RxJavaUtils.getInstance().publish(cityName)

        // Navigate Back to Dashboard Fragment
        findNavController(requireView()).navigateUp()
    }

    private fun recentSearchVisibility(){

        // Recent Searches taken from Shared Preference
        if(TextUtils.isEmpty(appPreference.KEY_CITY) == false){
            _binding?.idRecentSearchLabel?.visibility = View.VISIBLE
            _binding?.recentSearchLayout?.visibility = View.VISIBLE
            _binding?.content?.text = appPreference.KEY_CITY
            _binding?.latlng?.text = "${appPreference.KEY_LATITUDE}, ${appPreference.KEY_LONGITUDE}"
        }else{
            _binding?.idRecentSearchLabel?.visibility = View.GONE
            _binding?.recentSearchLayout?.visibility = View.GONE
        }
    }


}