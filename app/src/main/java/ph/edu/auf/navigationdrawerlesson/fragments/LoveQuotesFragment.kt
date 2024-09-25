package ph.edu.auf.navigationdrawerlesson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.R
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentLoveQuotesBinding
import kotlin.random.Random

object FavoriteQuotesHolder {
    val favoriteQuotes = mutableListOf<String>()
}

class LoveQuotesFragment : Fragment() {

    private var _binding: FragmentLoveQuotesBinding? = null
    private val binding get() = _binding!!

    // List of love quotes
    private val loveQuotes = listOf(
        "In all the world, there is no heart for me like yours. - Maya Angelou",
        "Love is composed of a single soul inhabiting two bodies. - Aristotle",
        "To love and be loved is to feel the sun from both sides. - David Viscott",
        "The best thing to hold onto in life is each other. - Audrey Hepburn",
        "You don't love someone because they're perfect. You love them in spite of the fact that they're not. - Jodi Picoult",
        "We accept the love we think we deserve. - Stephen Chbosky",
        "Love is the only force capable of transforming an enemy into a friend. - Martin Luther King, Jr."
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoveQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomizeQuote.setOnClickListener {
            // Randomly select a quote and display it
            val randomIndex = Random.nextInt(loveQuotes.size)
            binding.txtLoveQuote.text = loveQuotes[randomIndex]
        }

        binding.btnSaveQuote.setOnClickListener {
            // Save the current quote to favorites
            val currentQuote = binding.txtLoveQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
