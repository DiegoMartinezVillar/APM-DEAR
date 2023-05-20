package ensemble.dear.currentTrackings.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage

class TrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)

    //val packageNumber = view.findViewById<TextView>(R.id.)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val btnDelete = view.findViewById<Button>(R.id.buttonDeleteTracking)

    fun render(trackingModel: DeliveryPackage, onClickListener: (DeliveryPackage) -> Unit, onClickDelete: (Int, Int) -> Unit) {

        daysUntilArrival.text = trackingModel.arrivalDate.dayOfMonth.toString() + " " +
                trackingModel.arrivalDate.month.name.lowercase() + " " + trackingModel.arrivalDate.year.toString()
        shipperCompany.text = trackingModel.shipperCompany
        packageContent.text = trackingModel.packageAlias

        /* Listeners */
        itemView.setOnClickListener {
            onClickListener(trackingModel)
        }

        btnDelete.setOnClickListener {
            onClickDelete(trackingModel.idDelivery, adapterPosition)
            //onClickDelete(adapterPosition)
        }
    }
}