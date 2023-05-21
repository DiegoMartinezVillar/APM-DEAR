package ensemble.dear.pendingShipments.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.entity.Package

class ShipmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val shipmentNumber = view.findViewById<TextView>(R.id.textTrackingNumber)
    val shipmentAddress = view.findViewById<TextView>(R.id.textAddressPackage)

    fun render(packageModel: Package, onClickListener: (Package) -> Unit ) {
        shipmentAddress.text = packageModel.arrivalDate.dayOfMonth.toString() + " " +
                packageModel.arrivalDate.month.name.lowercase() + " " + packageModel.arrivalDate.year.toString()
        shipmentNumber.text = "#" + packageModel.packageNumber.toString()

        /* Listener */
        itemView.setOnClickListener {
            onClickListener(packageModel)
        }
    }
}