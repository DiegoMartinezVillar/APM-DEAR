package ensemble.dear.currentTrackings.adapter

import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage

/**
 * load image into ImageView using Coil - SVG images
 */
fun ImageView.loadUrl(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.placeholder_image)
        .error(R.drawable.placeholder_image)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

class TrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageAlias = view.findViewById<TextView>(R.id.textTrackingNumber)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val btnDelete = view.findViewById<Button>(R.id.buttonDeleteTracking)
    val imgShipperCompany = view.findViewById<ImageView>(R.id.shipperImage)

    fun render(trackingModel: DeliveryPackage, onClickListener: (DeliveryPackage) -> Unit, onClickDelete: (Int, Int) -> Unit) {

        val daysUntilArrivalText = trackingModel.arrivalDate.dayOfMonth.toString() + " " + trackingModel.arrivalDate.month.name.lowercase() + " " + trackingModel.arrivalDate.year.toString()
        val packageAliasText = "#" + trackingModel.packageNumber.toString() + " - " + trackingModel.packageAlias

        daysUntilArrival.text = daysUntilArrivalText
        shipperCompany.text = trackingModel.shipperCompany
        packageAlias.text = packageAliasText

        //load shipper company photo in ImageView
        imgShipperCompany.loadUrl(trackingModel.shipperCompanyPhoto)

        // Click Listener for navigating to details page
        itemView.setOnClickListener {
            onClickListener(trackingModel)
        }

        // Delete button Listener
        btnDelete.setOnClickListener {
            onClickDelete(trackingModel.idDelivery, adapterPosition)
        }
    }
}