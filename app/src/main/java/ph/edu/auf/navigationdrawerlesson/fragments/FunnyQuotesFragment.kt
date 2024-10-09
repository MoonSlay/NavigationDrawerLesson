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
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentFunnyQuotesBinding
import kotlin.random.Random

class FunnyQuotesFragment : Fragment() {

    private var _binding: FragmentFunnyQuotesBinding? = null
    private val binding get() = _binding!!

    // Retrieve funny quotes from the QuoteRepository
    private val funnyQuotes = QuoteRepository.quotesMap["funny"] ?: emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFunnyQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Automatically display a random quote when the fragment is created
        if (funnyQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(funnyQuotes.size)
            binding.txtFunnyQuote.text = funnyQuotes[randomIndex]
        } else {
            binding.txtFunnyQuote.text = "No quotes available" // Handle case with no quotes
        }

        binding.btnRandomizeQuote.setOnClickListener {
            if (funnyQuotes.isNotEmpty()) {
                val randomIndex = Random.nextInt(funnyQuotes.size)
                binding.txtFunnyQuote.text = funnyQuotes[randomIndex]
            } else {
                Toast.makeText(requireContext(), "No quotes available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtFunnyQuote.text.toString()
            if (currentQuote.isNotEmpty()) {
                if (!FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                    FavoriteQuotesHolder.addQuote(requireContext(), currentQuote)
                    Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnShowQuotes.setOnClickListener {
            showFunnyQuotesDialog()
        }
    }

    private fun showFunnyQuotesDialog() {
        val quotesArray = funnyQuotes.toTypedArray() // Convert to array for the ArrayAdapter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesArray)

        AlertDialog.Builder(requireContext())
            .setTitle("Funny Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
