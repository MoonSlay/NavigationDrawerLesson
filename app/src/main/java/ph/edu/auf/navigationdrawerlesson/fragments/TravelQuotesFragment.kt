package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.QuoteRepository
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentTravelQuotesBinding
import kotlin.random.Random

class TravelQuotesFragment : Fragment() {

    private var _binding: FragmentTravelQuotesBinding? = null
    private val binding get() = _binding!!

    // Retrieve travel quotes from the QuoteRepository
    private val travelQuotes = QuoteRepository.quotesMap["travel"] ?: emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTravelQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Automatically display a random quote when the fragment is created
        if (travelQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(travelQuotes.size)
            binding.txtTravelQuote.text = travelQuotes[randomIndex]
        } else {
            binding.txtTravelQuote.text = "No quotes available" // Handle case with no quotes
        }

        // Button to randomize quote
        binding.btnRandomizeQuote.setOnClickListener {
            if (travelQuotes.isNotEmpty()) {
                val randomIndex = Random.nextInt(travelQuotes.size)
                binding.txtTravelQuote.text = travelQuotes[randomIndex]
            } else {
                Toast.makeText(requireContext(), "No quotes available", Toast.LENGTH_SHORT).show()
            }
        }

        // Button to save the current quote to favorites
        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtTravelQuote.text.toString()
            if (currentQuote.isNotEmpty()) {
                if (!FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                    FavoriteQuotesHolder.addQuote(requireContext(), currentQuote)
                    Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Button to show all travel quotes in a dialog
        binding.btnShowQuotes.setOnClickListener {
            showTravelQuotesDialog()
        }
    }

    private fun showTravelQuotesDialog() {
        val quotesArray = travelQuotes.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesArray)

        AlertDialog.Builder(requireContext())
            .setTitle("Travel Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
