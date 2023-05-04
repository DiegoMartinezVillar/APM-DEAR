package ensemble.dear.currentTrackings.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.currentTrackings.Tracking
import ensemble.dear.database.entities.PackageEntity

class TrackingsAdapter(
    //private val trackingsList: LiveData<List<PackageEntity>>,
    private val trackingsList: MutableList<Tracking>,
    private val onClickListener: (Tracking) -> Unit,
    private val onClickDelete: (Int) -> Unit
) :
    RecyclerView.Adapter<TrackingsViewHolder>() {

    val notifyTrackingsList = MediatorLiveData<List<PackageEntity?>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackingsViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return TrackingsViewHolder(layoutinflater.inflate(R.layout.tracking_item, parent, false))
    }

    override fun getItemCount(): Int = trackingsList.size

    override fun onBindViewHolder(holder: TrackingsViewHolder, position: Int) {
        val item = trackingsList[position]
        holder.render(item, onClickListener, onClickDelete)
    }
}
