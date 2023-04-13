package ensemble.dear.pendingShipments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.ClientTrackingDetails
import ensemble.dear.CourierTrackingDetails
import ensemble.dear.R
import ensemble.dear.currentTrackings.Tracking
import ensemble.dear.currentTrackings.adapter.TrackingsAdapter
import ensemble.dear.pendingShipments.adapter.ShipmentsAdapter


const val SHIPMENT_ID = "shipment_id"

class ShipmentsFragment : Fragment() {

    private var shipmentsMutableList: MutableList<Shipment> =
        ShipmentsProvider.shipmentsList.toMutableList()
    private lateinit var adapter: ShipmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shipments_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initRecyclerView

        adapter = ShipmentsAdapter(
            shipmentsList = shipmentsMutableList,
            onClickListener = { shipment -> onItemSelected(shipment) }
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerShipments)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun onItemSelected(shipment: Shipment) {
        //Toast.makeText(context, tracking.packageNumber, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, CourierTrackingDetails()::class.java)

        intent.putExtra(SHIPMENT_ID, shipment.packageNumber)
        startActivity(intent)
    }

}