package com.example.cryptoprice.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprice.dataStore.MyDataStore
import com.example.cryptoprice.datamodel.CurrentPriceList
import com.example.cryptoprice.datamodel.CurrentPriceResult
import com.example.cryptoprice.db.entity.InterestCoinEntity
import com.example.cryptoprice.repository.DBRepository
import com.example.cryptoprice.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SeletViewModel : ViewModel() {


    private val netWorkRepository = NetWorkRepository()
    private val DBRepository = DBRepository()

    private lateinit var currentPriceResultList : ArrayList<CurrentPriceResult>

    //데이터변화를 관찰 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    private val _saved = MutableLiveData<String>()
    val save : LiveData<String>
        get() = _saved

    //코루틴
    fun getCurrentCoinList() = viewModelScope.launch {
        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

        for (coin in result.data){

            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPriceList::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)


            } catch (e: Exception) {
                Timber.d("ㅇㅇ")
            }
        }

        _currentPriceResult.value = currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    //DB에 데이터 저장
    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =  viewModelScope.launch(Dispatchers.IO) {

        //1. 전체 코인데이터를 가져와서 -> OK   반복문
        for (coin in currentPriceResultList){
            Timber.d(coin.toString())


            //2. 내가 선택한 코인인지 아닌지 구분해서
            //포함하면 TRUE
            val selected = selectedCoinList.contains(coin.coinName)

            val interestCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            //3. 저장
            interestCoinEntity.let{
                DBRepository.insertInterestCoinData(it)
            }

        }

        withContext(Dispatchers.Main){
            _saved.value = "done"
        }



    }


}