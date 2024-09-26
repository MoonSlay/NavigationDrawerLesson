package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentInspirationalQuotesBinding
import kotlin.random.Random

class InspirationalQuotesFragment : Fragment() {

    private var _binding: FragmentInspirationalQuotesBinding? = null
    private val binding get() = _binding!!

    private val inspirationalQuotes = listOf(
        "The only way to do great work is to love what you do. - Steve Jobs",
        "Success is not the key to happiness. Happiness is the key to success. - Albert Schweitzer",
        "Believe you can and you're halfway there. - Theodore Roosevelt",
        "In the middle of difficulty lies opportunity. - Albert Einstein",
        "It does not matter how slowly you go as long as you do not stop. - Confucius"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInspirationalQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRandomizeQuote.setOnClickListener {
            val randomIndex = Random.nextInt(inspirationalQuotes.size)
            binding.txtInspirationalQuote.text = inspirationalQuotes[randomIndex]
        }

        binding.btnSaveQuote.setOnClickListener {
            val currentQuote = binding.txtInspirationalQuote.text.toString()
            if (currentQuote.isNotEmpty() && !FavoriteQuotesHolder.favoriteQuotes.contains(currentQuote)) {
                FavoriteQuotesHolder.favoriteQuotes.add(currentQuote)
                Toast.makeText(requireContext(), "Quote saved to favorites!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "This quote is already in favorites.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShowQuotes.setOnClickListener {
            showInspirationalQuotesDialog()
        }
    }

    private fun showInspirationalQuotesDialog() {
        val quotesArray = inspirationalQuotes.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, quotesArray)

        AlertDialog.Builder(requireContext())
            .setTitle("Inspirational Quotes")
            .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
