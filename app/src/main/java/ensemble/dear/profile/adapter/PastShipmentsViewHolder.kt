package ensemble.dear.profile.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.entity.Package


class PastShipmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val imgShipperCompany = view.findViewById<ImageView>(R.id.shipperImage)

    fun render(shipment: Package) {
        val daysUntilArrivalText = shipment.arrivalDate.dayOfMonth.toString() + " " + shipment.arrivalDate.month.name.lowercase() + " " + shipment.arrivalDate.year.toString()
        val packageNumberText = "#" + shipment.packageNumber.toString()

        daysUntilArrival.text = daysUntilArrivalText
        shipperCompany.text = shipment.shipperCompany
        packageContent.text = packageNumberText

        imgShipperCompany.loadUrl(shipment.shipperCompanyPhoto)

    }
}