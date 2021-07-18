package com.kaizen_team.kopiku.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.adapter.ItemAdapter
import com.kaizen_team.kopiku.firestore.FirestoreCoffee
import com.kaizen_team.kopiku.local.LocalStorage
import com.kaizen_team.kopiku.model.Cart
import com.kaizen_team.kopiku.model.Coffee
import com.kaizen_team.kopiku.model.ItemCart
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var bottomSheetView: View
    lateinit var bottomSheetDialog: BottomSheetDialog

    var cart = Cart()
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirestoreCoffee.getCoffee{ b, list ->
            RVCoffee.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            RVCoffee.adapter = ItemAdapter(list) {
                showBottomSheet(it)
            }
        }
        FirestoreCoffee.getNonCoffee { b, list ->
            RVNonCoffee.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            RVNonCoffee.adapter = ItemAdapter(list) {
                showBottomSheet(it)
            }
        }

        initBottomSheet()
    }

    private fun initBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
    }

    private fun showBottomSheet(product: Coffee) {
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true

        updateBottomSheetUI(product)

        bottomSheetDialog.show()
    }

    private fun updateBottomSheetUI(coffee: Coffee) {
        bottomSheetView.add_button.setOnClickListener {
            var indexProduct = -1
            val cart = LocalStorage.getCart(requireContext())
            cart.products.forEachIndexed { index, itemCart ->
                if (itemCart.coffee.id.equals(coffee.id)) {
                    indexProduct = index
                }
            }
            Log.d("TAG", cart.toString())
            if (indexProduct != -1) {
                cart.products[indexProduct].quantity = cart.products[indexProduct].quantity.inc()
                bottomSheetView.input_item.text = cart.products[indexProduct].quantity.toString()
                bottomSheetView.tvTotalPrice.text =
                    ((cart.products[indexProduct].quantity * (cart.products[indexProduct].coffee.harga_barang
                        ?: 0)).toString())
            } else {
                cart.products.add(ItemCart(coffee, 1))
                bottomSheetView.input_item.text =
                    cart.products[cart.products.size - 1].quantity.toString()
                bottomSheetView.tvTotalPrice.text =
                    ((cart.products[cart.products.size - 1].quantity * (cart.products[cart.products.size - 1].coffee.harga_barang
                        ?: 0)).toString())
            }
            LocalStorage.setCart(requireContext(), cart)
        }

        bottomSheetView.min_button.setOnClickListener {
            var indexProduct = -1
            val cart = LocalStorage.getCart(requireContext())
            cart.products.forEachIndexed { index, itemCart ->
                if (itemCart.coffee.id.equals(coffee.id)) {
                    indexProduct = index
                }
            }
            if (indexProduct != -1) {
                cart.products[indexProduct].quantity = cart.products[indexProduct].quantity.dec()
                bottomSheetView.input_item.text = cart.products[indexProduct].quantity.toString()
                bottomSheetView.tvTotalPrice.text =
                    ((cart.products[indexProduct].quantity * (cart.products[indexProduct].coffee.harga_barang
                        ?: 0)).toString())
                if (cart.products[indexProduct].quantity == 0) {
                    cart.products.removeAt(indexProduct)
                }
            }
            LocalStorage.setCart(requireContext(), cart)
            Log.d("TAG", cart.toString())
        }

        bottomSheetView.titlepopup.text = coffee.nama_barang
        bottomSheetView.detailpopup.text = coffee.deskripsi

        cart = LocalStorage.getCart(requireContext())

        val savedProduct = cart.products.find {
            it.coffee.id == coffee.id
        }

        if (savedProduct != null) {
            bottomSheetView.input_item.text = savedProduct.quantity.toString()
            bottomSheetView.tvTotalPrice.text =
                ((savedProduct.quantity * (savedProduct.coffee.harga_barang ?: 0)).toString())
        } else {
            bottomSheetView.input_item.text = "0"
            bottomSheetView.tvTotalPrice.text = "Rp 0"
        }

        Glide.with(bottomSheetView).load(coffee.fotoBarang).into(bottomSheetView.img_s)
    }

}