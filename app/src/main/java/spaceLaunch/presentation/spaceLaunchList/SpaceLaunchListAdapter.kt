package spaceLaunch.presentation.spaceLaunchList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import spaceLaunch.domain.models.Launches
import com.example.spacelaunch.databinding.SpaceLaunchDetailBinding

class SpaceLaunchListAdapter(private val onClickListeners: SpaceListViewHolder.SpaceItemClickListeners) : RecyclerView.Adapter<SpaceListViewHolder>() {

    private val items = mutableListOf<Launches>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceListViewHolder {
        val binding = SpaceLaunchDetailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SpaceListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SpaceListViewHolder, position: Int) {
        holder.bind(items[position], onClickListeners, position)
    }

    fun updateItems(newItems: List<Launches>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}