package spaceLaunch.presentation.spaceLaunchList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.Toast
import spaceLaunch.domain.models.Launches
import com.example.spacelaunch.R
import com.example.spacelaunch.databinding.SpaceLaunchListLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpaceLaunchListActivity : AppCompatActivity() {

    private lateinit var binding: SpaceLaunchListLayoutBinding
    private val viewModel: SpaceLaunchListViewModel by viewModel()
    lateinit var adapter: SpaceLaunchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SpaceLaunchListLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setObservers()
        getSpaceLaunchItems()
        setOnClickListeners()
        signUpHighlightText()
    }

    private fun getSpaceLaunchItems() {
        viewModel.getSpaceLaunchItems()
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setAdapter() {
        adapter = SpaceLaunchListAdapter(launchItemClickListeners)
        binding.launchList.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.tryAgainText.setOnClickListener {
            it.isEnabled = false
            viewModel.getSpaceLaunchItems()
        }
    }

    private fun setObservers() {
        viewModel.spaceLaunchItems.observe(this) {
            binding.progressBar.visibility = View.GONE
            adapter.updateItems(it)
        }
        viewModel.failedRequest.observe(this) {
            Toast.makeText(binding.root.context, it, Toast.LENGTH_SHORT).show()
            binding.tryAgainText.visibility = View.VISIBLE
            binding.tryAgainText.isEnabled = true
            binding.progressBar.visibility = View.GONE
        }
    }

    private val launchItemClickListeners = object : SpaceListViewHolder.SpaceItemClickListeners {
        override fun spaceClicked(spaceItem: Launches, position: Int) {
            Toast.makeText(binding.root.context, "item $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUpHighlightText() {
        // This works for english for now, will have to have a start and end for other languages, or do the whole things in the strings list
        val string = resources.getString(R.string.try_again)
        val span = SpannableString(string)
        val colour = binding.root.resources.getColor(R.color.blue)
        span.setSpan(ForegroundColorSpan(colour), 0, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE )
        span.setSpan(UnderlineSpan(), 0, span.length, 0)
        binding.tryAgainText.text = span
    }
}