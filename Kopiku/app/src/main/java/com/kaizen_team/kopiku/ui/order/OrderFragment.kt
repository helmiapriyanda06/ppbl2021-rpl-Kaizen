package com.kaizen_team.kopiku.ui.order

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaizen_team.kopiku.DetailOrderActivity
import com.kaizen_team.kopiku.R
import com.kaizen_team.kopiku.adapter.OrderAdapter
import kotlinx.android.synthetic.main.order_fragment.*

class OrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderViewModel =
            ViewModelProvider(this).get(OrderViewModel::class.java)
        val root = inflater.inflate(R.layout.order_fragment, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOrder.adapter = OrderAdapter() {
            startActivity(
                Intent(requireContext(), DetailOrderActivity::class.java)
            )
        }
    }
}