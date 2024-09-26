package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentHistoricalQuotesBinding
import kotlin.random.Random

class HistoricalQuotesFragment : Fragment() {

    private var _binding: FragmentHistoricalQuotesBinding? = null
    private val binding get() = _binding!!

    private val historicalQuotes = listOf(
        "The only thing we have to fear is fear itself. - Franklin D. Roosevelt",
        "I have a dream. - Martin Luther King Jr.",
        "Injustice anywhere is a threat to justice everywhere. - Martin Luther King Jr.",
        "History is written by the victors. - Winston Churchill",
        "Those who do not remember the past are condemned to repeat it. - George Santayana"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricalQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomizeQuote.setOnClickListener {
            val randomIndex = Random.nextInt(historicalQuotes.size)
            binding.txtHistoricalQuote.text = historicalQuotes[randomIndex]
        }

        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtHistoricalQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShowQuotes.setOnClickListener {
            showHistoricalQuotesDialog()
        }
    }

    private fun showHistoricalQuotesDialog() {
        val quotesArray = historicalQuotes.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesArray)

        AlertDialog.Builder(requireContext())
            .setTitle("Historical Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
