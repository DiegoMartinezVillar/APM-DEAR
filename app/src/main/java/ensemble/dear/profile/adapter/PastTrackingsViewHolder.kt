package ensemble.dear.profile.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.entity.DeliveryPackage


class PastTrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val imgShipperCompany = view.findViewById<ImageView>(R.id.shipperImage)

    fun render(trackingModel: DeliveryPackage) {
        val daysUntilArrivalText = trackingModel.arrivalDate.dayOfMonth.toString() + " " + trackingModel.arrivalDate.month.name.lowercase() + " " + trackingModel.arrivalDate.year.toString()
        val packageAliasText = "#" + trackingModel.packageNumber.toString() + " - " + trackingModel.packageAlias

        daysUntilArrival.text = daysUntilArrivalText
        shipperCompany.text = trackingModel.shipperCompany
        packageContent.text = packageAliasText

        imgShipperCompany.loadUrl(trackingModel.shipperCompanyPhoto)

    }
}