package ph.edu.auf.navigationdrawerlesson.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import ph.edu.auf.navigationdrawerlesson.QuoteChanger
import ph.edu.auf.navigationdrawerlesson.QuoteRepository
import ph.edu.auf.navigationdrawerlesson.databinding.FragmentQuotesOfTheDayBinding
import java.util.*
import kotlin.random.Random
import androidx.appcompat.app.AlertDialog

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

        // Display the quote of the day
        displayQuoteOfTheDay()

        // Set up the Randomize button click listener
        binding.btnRandomize.setOnClickListener {
            val newQuote = generateRandomQuote()
            binding.txtQuoteOfTheDay.text = newQuote
            saveQuoteOfTheDay(newQuote)
        }

        // Set up the Show All Quotes button click listener
        binding.btnShowAllQuotes.setOnClickListener {
            showAllQuotesDialog()
        }

        // Schedule the midnight reset
        scheduleMidnightReset()
    }

    private fun displayQuoteOfTheDay() {
        val (savedQuote, timestamp) = loadQuoteOfTheDay()
        val currentTime = System.currentTimeMillis()
        val isSameDay = isSameDay(timestamp, currentTime)

        if (savedQuote != null && isSameDay) {
            binding.txtQuoteOfTheDay.text = savedQuote
        } else {
            val newQuote = generateRandomQuote()
            binding.txtQuoteOfTheDay.text = newQuote
            saveQuoteOfTheDay(newQuote)
        }
    }

    private fun generateRandomQuote(): String {
        return if (allQuotes.isNotEmpty()) {
            val randomIndex = Random.nextInt(allQuotes.size)
            allQuotes[randomIndex]
        } else {
            "No quotes available."
        }
    }

    private fun saveQuoteOfTheDay(quote: String) {
        val sharedPreferences = requireContext().getSharedPreferences("quote_of_the_day", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("quote", quote)
        editor.putLong("timestamp", System.currentTimeMillis())
        editor.apply()
    }

    private fun loadQuoteOfTheDay(): Pair<String?, Long> {
        val sharedPreferences = requireContext().getSharedPreferences("quote_of_the_day", Context.MODE_PRIVATE)
        val quote = sharedPreferences.getString("quote", null)
        val timestamp = sharedPreferences.getLong("timestamp", 0)
        return Pair(quote, timestamp)
    }

    private fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val calendar1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val calendar2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
    }

    private fun scheduleMidnightReset() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), QuoteChanger::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun showAllQuotesDialog() {
        if (allQuotes.isNotEmpty()) {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, allQuotes.toTypedArray())
            AlertDialog.Builder(requireContext())
                .setTitle("All Quotes")
                .setAdapter(adapter) { dialog, _ -> dialog.dismiss() }
                .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("No Quotes")
                .setMessage("There are no quotes available.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}