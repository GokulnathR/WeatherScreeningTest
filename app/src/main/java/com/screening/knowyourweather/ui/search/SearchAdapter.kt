package com.screening.knowyourweather.ui.search

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.screening.knowyourweather.R
import java.util.*


class SearchAdapter(
    private var countryList: ArrayList<USCities>,
    private var listener: CitySearchListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    var cityFilterList = ArrayList<USCities>()
    var mListener: CitySearchListener? = null
    var charSearch = ""

    class CountryHolder(var view: View) :
        RecyclerView.ViewHolder(view)

    init {
        cityFilterList = countryList
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)

        return CountryHolder(binding.rootView)
    }

    override fun getItemCount(): Int {
        return cityFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val countryHolder = holder as CountryHolder


        countryHolder.view.findViewById<TextView>(R.id.content)
            .setText(cityFilterList[position].cityName)
        countryHolder.view.findViewById<TextView>(R.id.latlng).text =
            "${cityFilterList[position].latitude}, ${cityFilterList[position].longitude}"


        countryHolder.view.findViewById<TextView>(R.id.content).setOnClickListener {
                mListener?.selectedCity(
                    cityFilterList[position].cityName,
                    cityFilterList[position].latitude,
                    cityFilterList[position].longitude
                )
            }


    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charSearch = constraint.toString()

                val resultList = ArrayList<USCities>()
                for (row in countryList) {
                    if (row.cityName.contains(charSearch, true)) {
                        resultList.add(row)
                    }
                }
                cityFilterList = resultList

                val filterResults = FilterResults()
                filterResults.values = cityFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                cityFilterList = results?.values as ArrayList<USCities>

                mListener?.notifyCitySearch(cityFilterList)
                //notifyDataSetChanged()
            }

        }
    }

    interface CitySearchListener {
        fun notifyCitySearch(list: ArrayList<USCities>)
        fun selectedCity(cityName: String, lat: String, lng: String)
    }

}