package ensemble.dear.profile.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.Package

class PastShipmentsAdapter(
    private val packagesList: List<Package>
) :
    RecyclerView.Adapter<PastShipmentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastShipmentsViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return PastShipmentsViewHolder(layoutinflater.inflate(R.layout.tracking_past_item, parent, false))
    }

    override fun getItemCount(): Int = packagesList.size

    override fun onBindViewHolder(holder: PastShipmentsViewHolder, position: Int) {
        val item = packagesList[position]
        holder.render(item)
    }

    fun getpackagesList(): List<Package> {
        return packagesList
    }
}
