package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentFunnyQuotesBinding
import kotlin.random.Random

class FunnyQuotesFragment : Fragment() {

    private var _binding: FragmentFunnyQuotesBinding? = null
    private val binding get() = _binding!!

    private val funnyQuotes = listOf(
        "I'm on a seafood diet. I see food and I eat it.",
        "I used to think I was indecisive, but now I'm not so sure.",
        "Life is short. Smile while you still have teeth.",
        "The only mystery in life is why the kamikaze pilots wore helmets.",
        "I'm not arguing, I'm just explaining why I'm right."
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFunnyQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Randomize and show funny quote
        binding.btnRandomizeQuote.setOnClickListener {
            val randomIndex = Random.nextInt(funnyQuotes.size)
            binding.txtFunnyQuote.text = funnyQuotes[randomIndex]
        }

        // Save quote to favorites
        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtFunnyQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

        // Show all funny quotes
        binding.btnShowQuotes.setOnClickListener {
            showFunnyQuotesDialog()
        }
    }

    private fun showFunnyQuotesDialog() {
        val quotesArray = funnyQuotes.toTypedArray()
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
