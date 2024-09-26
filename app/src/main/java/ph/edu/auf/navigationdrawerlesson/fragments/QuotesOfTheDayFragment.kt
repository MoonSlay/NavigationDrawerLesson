package ph.edu.auf.navigationdrawerlesson.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun displayRandomQuote() {
        if (allQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(allQuotes.size)
            binding.txtQuoteOfTheDay.text = allQuotes[randomIndex]
        } else {
            binding.txtQuoteOfTheDay.text = "No quotes available."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
