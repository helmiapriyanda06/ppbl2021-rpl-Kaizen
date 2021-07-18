package com.kaizen_team.kopiku.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kaizen_team.kopiku.MainActivity
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.adapter.CartAdapter
import com.kaizen_team.kopiku.local.LocalStorage
import kotlinx.android.synthetic.main.bottom_sheet_layout_cart.view.*
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
            cart.products.remove(it)
            LocalStorage.setCart(requireContext(),cart)
            var totalPrice = 0
            cart.products.forEach {
                totalPrice = totalPrice.plus(
                    it.quantity * (it.coffee.harga_barang?:0)
                )
            }

            tvTotalHarga.text = "Rp $totalPrice"
        }
        rvCart.layoutManager = LinearLayoutManager(requireContext())
        rvCart.adapter = adapter
        initBottomSheet()

        var totalPrice = 0
        cart.products.forEach {
            totalPrice = totalPrice.plus(
                it.quantity * (it.coffee.harga_barang?:0)
            )
        }

        tvTotalHarga.text = "Rp $totalPrice"

        btnPay.setOnClickListener {
            showBottomSheet()
        }

    }

    private fun initBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout_cart, null)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView.btntutup.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }


    private fun showBottomSheet() {
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true
        bottomSheetDialog.show()
    }
}