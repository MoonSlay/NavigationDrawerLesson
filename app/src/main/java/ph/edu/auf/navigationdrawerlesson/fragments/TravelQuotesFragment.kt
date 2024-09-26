package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentTravelQuotesBinding
import kotlin.random.Random

class TravelQuotesFragment : Fragment() {

    private var _binding: FragmentTravelQuotesBinding? = null
    private val binding get() = _binding!!

    private val travelQuotes = listOf(
        "The world is a book and those who do not travel read only one page. - Saint Augustine",
        "Travel is the only thing you buy that makes you richer.",
        "Take only memories, leave only footprints.",
        "Not all those who wander are lost. - J.R.R. Tolkien",
        "Adventure is worthwhile. - Aesop"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTravelQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomizeQuote.setOnClickListener {
            val randomIndex = Random.nextInt(travelQuotes.size)
            binding.txtTravelQuote.text = travelQuotes[randomIndex]
        }

        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtTravelQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

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
