package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentMotivationalQuotesBinding
import kotlin.random.Random

class MotivationalQuotesFragment : Fragment() {

    private var _binding: FragmentMotivationalQuotesBinding? = null
    private val binding get() = _binding!!

    private val motivationalQuotes = listOf(
        "The harder you work for something, the greater you’ll feel when you achieve it.",
        "Don’t stop when you’re tired. Stop when you’re done.",
        "Dream it. Wish it. Do it.",
        "Success doesn’t just find you. You have to go out and get it.",
        "Believe in yourself and all that you are. Know that there is something inside you that is greater than any obstacle."
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMotivationalQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomizeQuote.setOnClickListener {
            val randomIndex = Random.nextInt(motivationalQuotes.size)
            binding.txtMotivationalQuote.text = motivationalQuotes[randomIndex]
        }

        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtMotivationalQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

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
