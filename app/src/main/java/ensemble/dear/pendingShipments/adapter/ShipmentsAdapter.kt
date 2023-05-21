package ensemble.dear.pendingShipments.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.R
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.entity.Package

class ShipmentsAdapter(
    private val shipmentsList: List<Package>,
    private val onClickListener: (Package) -> Unit
) :
    RecyclerView.Adapter<ShipmentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentsViewHolder {
        val layoutinflater = LayoutInflater.from(parent.context)
        return ShipmentsViewHolder(layoutinflater.inflate(R.layout.shipment_item, parent, false))
    }

    override fun getItemCount(): Int = shipmentsList.size

    override fun onBindViewHolder(holder: ShipmentsViewHolder, position: Int) {
        val item = shipmentsList[position]
        holder.render(item, onClickListener)
    }

    fun getDeliveriesList(): List<Package> {
        return shipmentsList
    }
}
