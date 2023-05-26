package ensemble.dear.profile.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage

class PastTrackingsAdapter(
    private val trackingsList: List<DeliveryPackage>
) :
    RecyclerView.Adapter<PastTrackingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastTrackingsViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return PastTrackingsViewHolder(layoutinflater.inflate(R.layout.tracking_past_item, parent, false))
    }

    override fun getItemCount(): Int = trackingsList.size

    override fun onBindViewHolder(holder: PastTrackingsViewHolder, position: Int) {
        val item = trackingsList[position]
        holder.render(item)
    }

    fun getTrackingsList(): List<DeliveryPackage> {
        return trackingsList
    }
}
