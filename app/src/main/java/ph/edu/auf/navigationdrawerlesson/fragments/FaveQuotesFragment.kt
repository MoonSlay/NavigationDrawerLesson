package ph.edu.auf.navigationdrawerlesson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ph.edu.auf.navigationdrawerlesson.R
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentFaveQuotesBinding
import kotlin.random.Random

object FavoriteQuotesHolder {
    val favoriteQuotes = mutableListOf<String>()
}

class FaveQuotesFragment : Fragment() {

    private var _binding: FragmentFaveQuotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFaveQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Automatically display a random favorite quote if available
        if (FavoriteQuotesHolder.favoriteQuotes.isNotEmpty()) {
            randomizeFavoriteQuote()
        } else {
            binding.txtFaveQuote.text = "No favorite quotes yet."
        }

        binding.btnRandomizeQuote.setOnClickListener {
            randomizeFavoriteQuote()
        }

        binding.btnRemoveFave.setOnClickListener {
            removeFavoriteQuote()
        }

        binding.btnShowFaveQuotes.setOnClickListener {
            showFavoriteQuotesDialog()
        }
    }

    private fun randomizeFavoriteQuote() {
        if (FavoriteQuotesHolder.favoriteQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(FavoriteQuotesHolder.favoriteQuotes.size)
            val selectedQuote = FavoriteQuotesHolder.favoriteQuotes[randomIndex]
            binding.txtFaveQuote.text = selectedQuote
        } else {
            Toast.makeText(requireContext(), "No favorite quotes to randomize!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFavoriteQuote() {
        val currentQuote = binding.txtFaveQuote.text.toString()
        if (FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
            FavoriteQuotesHolder.favoriteQuotes.remove(currentQuote)
            // Check if there are still quotes left
            if (FavoriteQuotesHolder.favoriteQuotes.isNotEmpty()) {
                randomizeFavoriteQuote() // Display a new random quote
            } else {
                binding.txtFaveQuote.text = "No favorite quotes yet."
            }
            Toast.makeText(requireContext(), "Quote removed from favorites!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "This quote is not in favorites.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFavoriteQuotesDialog() {
        if (FavoriteQuotesHolder.favoriteQuotes.isEmpty()) {
            Toast.makeText(requireContext(), "No favorite quotes to display.", Toast.LENGTH_SHORT).show()
            return
        }

        val quotesList = FavoriteQuotesHolder.favoriteQuotes.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesList)

        AlertDialog.Builder(requireContext())
            .setTitle("Favorite Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
