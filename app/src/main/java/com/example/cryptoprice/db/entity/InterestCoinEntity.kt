package com.example.cryptoprice.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interest_coin_table")
data class InterestCoinEntity(

    //룸3대요소
    //@database : 클래스를 데이터베이스로 지정하는 annotation, Roomdatabase를 상속받은 클래스여야함
    //-room..databaseBuilder를 이용해 인스턴스를 생성함

    //@Entity : 클래스를 테이블 스키마로 지정하는 annotation
    //데이터베이스 안에 테이블들을 만들어야함.. 그 테이블들을 만드는 스키마를 지정하는?

    //@DAO : 클래스를 DAO(Data Access Object)로 지정하는 annotation
    //기본적인 insert,delete,update SQL은 자동적으로 만들어주며, 복잡한 SQL은 직접 만들 수 있음


    @PrimaryKey(autoGenerate =true)
    val id : Int,
    val coinName : String,
    val opening_price : String,
    val closing_price : String,
    val min_price : String,
    val max_price : String,
    val units_traded : String,
    val acc_trade_value : String,
    val prev_closing_price : String,
    val units_traded_24H : String,
    val acc_trade_value_24H : String,
    val fluctate_24H : String,
    val fluctate_rate_24H : String,
    var selected : Boolean
)

