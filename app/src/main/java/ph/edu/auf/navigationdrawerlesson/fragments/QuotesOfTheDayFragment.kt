package ph.edu.auf.navigationdrawerlesson.fragments

import android.R
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import ph.edu.auf.navigationdrawerlesson.QuoteRepository
import kotlin.random.Random
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentQuotesOfTheDayBinding

class QuotesOfTheDayFragment : Fragment() {

    private var _binding: FragmentQuotesOfTheDayBinding? = null
    private val binding get() = _binding!!

    private val allQuotes by lazy { QuoteRepository.quotesMap.values.flatten() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuotesOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display a random quote initially
        displayRandomQuote()

        // Set up the Randomize button click listener
        binding.btnRandomize.setOnClickListener {
            displayRandomQuote()
        }

        // Set up the Show All Quotes button click listener
        binding.btnShowAllQuotes.setOnClickListener {
            showAllQuotesDialog()
        }
    }

    private fun displayRandomQuote() {
        if (allQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(allQuotes.size)
            binding.txtQuoteOfTheDay.text = allQuotes[randomIndex]
        } else {
            binding.txtQuoteOfTheDay.text = "No quotes available."
        }
    }

    private fun showAllQuotesDialog() {
        if (allQuotes.isNotEmpty()) {
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, allQuotes.toTypedArray())
            AlertDialog.Builder(requireContext())
                .setTitle("All Quotes")
                .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
                .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("No Quotes")
                .setMessage("There are no quotes available.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
