package lat.pam.clashcoffeeshop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        findViewById<RecyclerView>(R.id.recycler_history).layoutManager = LinearLayoutManager(this)
        setupBottomNav(R.id.nav_history)
    }

    override fun onResume() {
        super.onResume()
        val orders = OrderHistory.getAll(this)
        findViewById<RecyclerView>(R.id.recycler_history).adapter = HistoryAdapter(orders)
        findViewById<View>(R.id.text_empty).visibility =
            if (orders.isEmpty()) View.VISIBLE else View.GONE
    }
}
