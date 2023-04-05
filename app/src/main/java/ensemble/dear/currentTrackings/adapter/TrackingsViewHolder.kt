package ensemble.dear.currentTrackings.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.currentTrackings.Tracking

class TrackingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val packageContent = view.findViewById<TextView>(R.id.textTrackingNumber)

    //val packageNumber = view.findViewById<TextView>(R.id.)
    val daysUntilArrival = view.findViewById<TextView>(R.id.daysUntilArrival)
    val shipperCompany = view.findViewById<TextView>(R.id.shipperCompany)
    val btnDelete = view.findViewById<Button>(R.id.buttonDeleteTracking)

    fun render(trackingModel: Tracking, onClickListener: (Tracking) -> Unit, onClickDelete: (Int) -> Unit) {
        daysUntilArrival.text = trackingModel.daysUntilArrival.toString()
        shipperCompany.text = trackingModel.shipperCompany
        packageContent.text = trackingModel.packageContent

        itemView.setOnClickListener { onClickListener(trackingModel) }

        btnDelete.setOnClickListener { onClickDelete(adapterPosition) }
    }
}