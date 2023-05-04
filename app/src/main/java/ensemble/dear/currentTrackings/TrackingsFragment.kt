package ensemble.dear.currentTrackings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ensemble.dear.ClientTrackingDetails
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.TrackingsAdapter
import ensemble.dear.database.repository.PackageRepository

//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

const val TRACKING_ID = "tracking_id"

class TrackingsFragment : Fragment() {

    private var trackingsMutableList: MutableList<Tracking> = TrackingsProvider.trackingsList.toMutableList()
    private lateinit var adapter: TrackingsAdapter
    val packageRepo = this.context?.let { PackageRepository.getInstance(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
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

        //initRecyclerView

        adapter = TrackingsAdapter(
            trackingsList = trackingsMutableList,
            onClickListener = { tracking -> onItemSelected(tracking) },
            onClickDelete = { position -> confirmDeletionAlert(position) }
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTracking)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    private fun confirmDeletionAlert(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.delete_dialog_title)
        builder.setMessage(R.string.delete_dialog_content)
        builder.setPositiveButton(R.string.delete_text) { _: DialogInterface, _: Int ->
            trackingsMutableList.removeAt(position)
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

    private fun onItemSelected(tracking: Tracking) {
        //Toast.makeText(context, tracking.packageNumber, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, ClientTrackingDetails()::class.java)

        intent.putExtra(TRACKING_ID, tracking.packageNumber)
        startActivity(intent)
    }

}