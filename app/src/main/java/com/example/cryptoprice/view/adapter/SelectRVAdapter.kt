package com.example.cryptoprice.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoprice.R
import com.example.cryptoprice.datamodel.CurrentPriceResult
import timber.log.Timber

class SelectRVAdapter(val content: Context, val coinPriceList : List<CurrentPriceResult>)
    : RecyclerView.Adapter<SelectRVAdapter.ViewHolder>(){

    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val coinName : TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage : ImageView = view.findViewById(R.id.likeBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRVAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectRVAdapter.ViewHolder, position: Int) {

        holder.coinName.text = coinPriceList[position].coinName

        val flucate_24H = coinPriceList[position].coinInfo.fluctate_24H
        if(flucate_24H.contains("-")){
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        }else{
            holder.coinPriceUpDown.text = "상승입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }

        val likeImage = holder.likeImage
        val currentCoin = coinPriceList[position].coinName

        //밑에 똑같이 하트눌러지는거 예외처리, RV재활용때매
        if(selectedCoinList.contains(currentCoin)){
            likeImage.setImageResource(R.drawable.like_red)
        }else{
            likeImage.setImageResource(R.drawable.like_grey)
        }


        likeImage.setOnClickListener{
            if(selectedCoinList.contains(currentCoin)){
                //포함하면
                selectedCoinList.remove(currentCoin)
                likeImage.setImageResource(R.drawable.like_grey)
            }else{
                //포함안하면
                selectedCoinList.add(currentCoin)
                likeImage.setImageResource(R.drawable.like_red)
            }

            Timber.d(selectedCoinList.toString())



        }

    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }
}