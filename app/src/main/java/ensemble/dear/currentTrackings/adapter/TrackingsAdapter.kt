package ensemble.dear.currentTrackings.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage

class TrackingsAdapter(
    private val trackingsList: List<DeliveryPackage>,
    private val onClickListener: (DeliveryPackage) -> Unit,
    private val onClickDelete: (Int, Int) -> Unit
) :
    RecyclerView.Adapter<TrackingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingsViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return TrackingsViewHolder(layoutinflater.inflate(R.layout.tracking_item, parent, false))
    }

    override fun getItemCount(): Int = trackingsList.size

    override fun onBindViewHolder(holder: TrackingsViewHolder, position: Int) {
        val item = trackingsList[position]
        holder.render(item, onClickListener, onClickDelete)
    }

    fun getTrackingsList(): List<DeliveryPackage> {
        return trackingsList
    }
}
