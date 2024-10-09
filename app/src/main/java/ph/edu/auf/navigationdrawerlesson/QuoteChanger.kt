package ph.edu.auf.navigationdrawerlesson

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class QuoteChanger : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences = context.getSharedPreferences("quote_of_the_day", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }
}