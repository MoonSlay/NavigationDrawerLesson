package ph.edu.auf.navigationdrawerlesson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.R
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentFaveQuotesBinding

class FaveQuotesFragment : Fragment() {

    private var _binding: FragmentFaveQuotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFaveQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayFavoriteQuotes()

        binding.btnRemoveFave.setOnClickListener {
            // Remove the favorite quote and refresh the display
            val currentQuote = binding.txtFaveQuote.text.toString()
            if (FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.remove(currentQuote)
                displayFavoriteQuotes()
                Toast.makeText(requireContext(), "Quote removed from favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is not in favorites.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayFavoriteQuotes() {
        if (FavoriteQuotesHolder.favoriteQuotes.isNotEmpty()) {
            binding.txtFaveQuote.text = FavoriteQuotesHolder.favoriteQuotes.joinToString("\n\n")
        } else {
            binding.txtFaveQuote.text = "No favorite quotes yet."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
