package com.kaizen_team.kopiku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kaizen_team.kopiku.adapter.DetailOrderAdapter

class DetailOrderActivity : AppCompatActivity() {

    lateinit var bottomSheetView: View
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        initBottomSheet()
    }


    private fun initBottomSheet() {
        bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout_cart, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

    }
}