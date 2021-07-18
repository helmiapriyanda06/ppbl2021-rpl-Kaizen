package com.kaizen_team.kopiku.local

import android.content.Context
import com.kaizen_team.kopiku.model.Cart
import com.orhanobut.hawk.Hawk

object LocalStorage {
    private const val TOKEN = "TOKEN"
    private const val LOGIN = "LOGIN"
    private const val EMAIL = "EMAIL"
    private const val NAMA = "NAMA"
    private const val CART = "CART"
    private const val TANDAKLINIS_POST = "TK_POST"

    fun init(context: Context) {
        Hawk.init(context).build();
    }

    fun setEmail(context: Context, token: String) {
        init(context)
        Hawk.put(EMAIL, token)
    }

    fun getEmail(context: Context): String {
        init(context)
        return Hawk.get(EMAIL, "")
    }

    fun setNama(context: Context, token: String) {
        init(context)
        Hawk.put(NAMA, token)
    }

    fun getNama(context: Context): String {
        init(context)
        return Hawk.get(NAMA, "")
    }

    fun setLogin(context: Context, isLogin: Boolean) {
        init(context)
        Hawk.put(LOGIN, isLogin)
    }

    fun isLogin(context: Context): Boolean {
        init(context)
        return Hawk.get(LOGIN, false)
    }

    fun logout(context: Context) {
        init(context)
        Hawk.deleteAll()
    }


    fun getCart(context: Context): Cart {
        init(context)
        return Hawk.get(CART, Cart())
    }

    fun setCart(context: Context, cart: Cart) {
        init(context)
        Hawk.put(CART, cart)
    }

}