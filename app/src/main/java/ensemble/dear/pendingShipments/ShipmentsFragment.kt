package ensemble.dear.pendingShipments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import ensemble.dear.R
import ensemble.dear.currentTrackings.TRACKING_ID
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.pendingShipments.adapter.ShipmentsAdapter
import ensemble.dear.database.entity.Package

class ShipmentsFragment : Fragment() {

    private var shipmentsMutableList: MutableList<Shipment> =
        ShipmentsProvider.shipmentsList.toMutableList()
    private lateinit var adapter: ShipmentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shipments_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this.requireContext())

        if(acct != null) {
            val packagesList = PackageRepository(this.requireContext()).getAllCourierPackagesForToday(acct.email.toString())

            adapter = ShipmentsAdapter(
                shipmentsList = packagesList,
                onClickListener = { delivery -> onItemSelected(delivery) }
            )
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerShipments)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
    }

    private fun onItemSelected(delivery: Package) {
        val intent = Intent(context, CourierTrackingDetails()::class.java)

        intent.putExtra(TRACKING_ID, delivery.packageNumber)
        startActivity(intent)
    }

}