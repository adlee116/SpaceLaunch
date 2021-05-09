package spaceLaunch.presentation.spaceLaunchList

import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacelaunch.R
import com.example.spacelaunch.databinding.SpaceLaunchDetailBinding
import spaceLaunch.domain.models.Launches

class SpaceListViewHolder(private val binding: SpaceLaunchDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(space: Launches, clickListener: SpaceItemClickListeners, position: Int) {
        binding.launchName.text = space.launchTitle
        binding.launchDate.text = space.launchDate
        space.launchImage?.let { loadImage(it, binding.spaceLaunchImage) }
        binding.tickCrossImage.setImageDrawable(
            ResourcesCompat.getDrawable(
                binding.root.resources,
                getSuccessImage(space.launchSuccess),
                binding.root.resources.newTheme()
            )
        )
        binding.root.setOnClickListener { clickListener.spaceClicked(space, position) }
    }

    /*    TODO There are certain images not working here due to the
           javax.net.ssl.SSLHandshakeException(Unacceptable certificate: CN=DigiCert Global Root CA, OU=www.digicert.com,
           O=DigiCert Inc, C=US)call GlideException#logRootCauses(String) for more detail */
    private fun loadImage(image: String, location: ImageView) {
        binding.noImageAvailableText.visibility = View.INVISIBLE
        Glide.with(binding.root.context)
            .load(image)
            .placeholder(R.drawable.camera)
            .centerCrop()
            .fitCenter()
            .dontAnimate()
            .into(location)
    }

    private fun getSuccessImage(successfulLaunch: Boolean?): Int {
        successfulLaunch?.let { if (it) { return R.drawable.check
        } }
        return R.drawable.cross
    }

    interface SpaceItemClickListeners {
        fun spaceClicked(spaceItem: Launches, position: Int)
    }
}