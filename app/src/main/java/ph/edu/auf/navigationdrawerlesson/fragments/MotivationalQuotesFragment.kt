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
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentMotivationalQuotesBinding
import kotlin.random.Random

class MotivationalQuotesFragment : Fragment() {

    private var _binding: FragmentMotivationalQuotesBinding? = null
    private val binding get() = _binding!!

    // Retrieve motivational quotes from the QuoteRepository
    private val motivationalQuotes = QuoteRepository.quotesMap["motivational"] ?: emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMotivationalQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Automatically display a random quote when the fragment is created
        if (motivationalQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(motivationalQuotes.size)
            binding.txtMotivationalQuote.text = motivationalQuotes[randomIndex]
        } else {
            binding.txtMotivationalQuote.text = "No quotes available" // Handle case with no quotes
        }

        // Button to randomize quote
        binding.btnRandomizeQuote.setOnClickListener {
            if (motivationalQuotes.isNotEmpty()) {
                val randomIndex = Random.nextInt(motivationalQuotes.size)
                binding.txtMotivationalQuote.text = motivationalQuotes[randomIndex]
            } else {
                Toast.makeText(requireContext(), "No quotes available", Toast.LENGTH_SHORT).show()
            }
        }

        // Button to save the current quote to favorites
        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtMotivationalQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

        // Button to show all motivational quotes in a dialog
        binding.btnShowQuotes.setOnClickListener {
            showMotivationalQuotesDialog()
        }
    }

    private fun showMotivationalQuotesDialog() {
        val quotesArray = motivationalQuotes.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesArray)

        AlertDialog.Builder(requireContext())
            .setTitle("Motivational Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
