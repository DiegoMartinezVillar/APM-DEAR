package ensemble.dear.pendingShipments.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.entity.Package

class ShipmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val shipmentNumber = view.findViewById<TextView>(R.id.textTrackingNumber)
    val shipmentAddress = view.findViewById<TextView>(R.id.textAddressPackage)
    val imageMap = view.findViewById<ImageView>(R.id.imageMap)

    fun render(packageModel: Package, onClickListener: (Package) -> Unit ) {
        val shipmentNumberText = "#" + packageModel.packageNumber.toString()

        shipmentAddress.text = packageModel.address
        shipmentNumber.text = shipmentNumberText

        // load shipper company photo in ImageView
        imageMap.loadUrl(packageModel.shipperCompanyPhoto)

        // Click Listener for navigating to details page
        itemView.setOnClickListener {
            onClickListener(packageModel)
        }
    }
}