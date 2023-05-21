package ensemble.dear.currentTrackings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.TrackingsAdapter
import ensemble.dear.database.entity.DeliveryPackage
import ensemble.dear.database.repository.DeliveryRepository

const val TRACKING_ID = "tracking_id"

class TrackingsFragment : Fragment() {

    private lateinit var adapter: TrackingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trackings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this.requireContext())

        if(acct != null) {

            val packagesList = DeliveryRepository(this.requireContext()).getAllUser(acct.email.toString())

            adapter = TrackingsAdapter(
                    trackingsList = packagesList,
                    onClickListener = { tracking -> onItemSelected(tracking) },
                    onClickDelete = { idDelivery, position ->
                        confirmDeletionAlert(idDelivery, position) }
                )
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTracking)

            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter

        }
    }

    private fun confirmDeletionAlert(idDelivery: Int, position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.delete_dialog_title)
        builder.setMessage(R.string.delete_dialog_content)
        builder.setPositiveButton(R.string.delete_text) { _: DialogInterface, _: Int ->
            DeliveryRepository(this.requireActivity()).delete(idDelivery)
            (adapter.getTrackingsList() as MutableList).removeAt(position)
            adapter.notifyItemRemoved(position)
        }
        builder.setNegativeButton(android.R.string.cancel) { _: DialogInterface, _: Int ->
            Toast.makeText(
                context,
                android.R.string.cancel, Toast.LENGTH_LONG
            ).show()
        }
        builder.show()
    }

    private fun onItemSelected(packageEnt: DeliveryPackage) {
        val intent = Intent(context, ClientTrackingDetails()::class.java)

        intent.putExtra(TRACKING_ID, packageEnt.packageNumber)
        startActivity(intent)
    }

}