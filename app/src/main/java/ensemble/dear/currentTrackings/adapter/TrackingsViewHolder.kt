package ensemble.dear.currentTrackings.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.entity.Package

class TrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)

    //val packageNumber = view.findViewById<TextView>(R.id.)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val btnDelete = view.findViewById<Button>(R.id.buttonDeleteTracking)

    fun render(trackingModel: DeliveryPackage, onClickListener: (DeliveryPackage) -> Unit, onClickDelete: (Int) -> Unit) {
        ////daysUntilArrival.text = trackingModel.daysUntilArrival.toString()

        //daysUntilArrival.text = trackingModel.estimatedArrivalDate.toString()
        //shipperCompany.text = trackingModel.shipperCompany
        //packageContent.text = trackingModel.packageContent

        daysUntilArrival.text = trackingModel.arrivalDate
        shipperCompany.text = trackingModel.shipperCompany
        packageContent.text = trackingModel.packageContent


        /* Listeners */
        itemView.setOnClickListener {
            onClickListener(trackingModel)



        }
        btnDelete.setOnClickListener {
            onClickDelete(adapterPosition)
        }
    }
}