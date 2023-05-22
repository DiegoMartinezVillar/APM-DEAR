package ensemble.dear.profile.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.entity.DeliveryPackage


class TrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)

    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val btnDelete = view.findViewById<Button>(R.id.buttonDeleteTracking)
    val imgShipperCompany = view.findViewById<ImageView>(R.id.shipperImage)

    fun render(trackingModel: DeliveryPackage) {

        daysUntilArrival.text = trackingModel.arrivalDate.dayOfMonth.toString() + " " +
                trackingModel.arrivalDate.month.name.lowercase() + " " + trackingModel.arrivalDate.year.toString()
        shipperCompany.text = trackingModel.shipperCompany
        packageContent.text = "#" + trackingModel.packageNumber.toString() + " - " + trackingModel.packageAlias

        imgShipperCompany.loadUrl(trackingModel.shipperCompanyPhoto)

    }
}