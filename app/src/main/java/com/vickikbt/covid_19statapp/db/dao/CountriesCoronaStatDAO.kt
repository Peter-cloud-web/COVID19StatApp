package com.vickikbt.covid_19statapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vickikbt.covid_19statapp.data.CountriesCoronaDataResponse
import com.vickikbt.covid_19statapp.db.entity.CountriesCoronaDataEntry

@Dao
interface CountriesCoronaStatDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(countriesCoronaDataResponse: CountriesCoronaDataResponse)

    @Query("select * from country_statistics")
    fun getCountriesStat(): LiveData<List<CountriesCoronaDataEntry>>


//    @Query("delete from country_statistics where something")

}