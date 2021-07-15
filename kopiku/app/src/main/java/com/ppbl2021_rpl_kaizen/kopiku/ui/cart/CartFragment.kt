package com.ppbl2021_rpl_kaizen.kopiku.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ppbl2021_rpl_kaizen.kopiku.R
import com.ppbl2021_rpl_kaizen.kopiku.adapter.CartAdapter
import com.ppbl2021_rpl_kaizen.kopiku.local.LocalStorage
import kotlinx.android.synthetic.main.activity_metode_pembayaran.view.*
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    lateinit var bottomSheetView: View
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cart, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cart = LocalStorage.getCart(requireContext())
        val adapter = CartAdapter(cart){
            cart.coffees.remove(it)
            LocalStorage.setCart(requireContext(),cart)
            var totalPrice = 0
            cart.coffees.forEach {
                totalPrice = totalPrice.plus(
                    it.quantity * (it.coffee.hargaBarang?:0)
                )
            }

            tvTotalPrice.text = "Rp $totalPrice"
        }
        rvCart.adapter = adapter
        initBottomSheet()

        var totalPrice = 0
        cart.coffees.forEach {
            totalPrice = totalPrice.plus(
                it.quantity * (it.coffee.hargaBarang?:0)
            )
        }

        tvTotalPrice.text = "Rp $totalPrice"

        btnPay.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun initBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.activity_metode_pembayaran, null)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView.backbutton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    private fun showBottomSheet() {
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true
        bottomSheetDialog.show()
    }
}