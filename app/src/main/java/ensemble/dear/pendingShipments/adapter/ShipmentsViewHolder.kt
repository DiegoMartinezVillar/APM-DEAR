package ensemble.dear.pendingShipments.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.pendingShipments.Shipment

class ShipmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val shipmentNumber = view.findViewById<TextView>(R.id.textTrackingNumber)
    val shipmentAddress = view.findViewById<TextView>(R.id.textAddressPackage)

    fun render(shipmentModel: Shipment, onClickListener: (Shipment) -> Unit ) {
        shipmentAddress.text = shipmentModel.estimatedArrivalDate.toString()
        shipmentNumber.text = "#" + shipmentModel.packageNumber.toString()

        /* Listener */
        itemView.setOnClickListener { onClickListener(shipmentModel) }
    }
}