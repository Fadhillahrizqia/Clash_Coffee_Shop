package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recycler = findViewById<RecyclerView>(R.id.recycler_cart)
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = CartAdapter { updateSummary() }
        recycler.adapter = adapter

        findViewById<Button>(R.id.button_checkout).setOnClickListener {
            if (CartManager.isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong. Pilih menu dulu di Home.", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, OrderActivity::class.java))
            }
        }

        findViewById<TextView>(R.id.text_clear).setOnClickListener {
            CartManager.clear()
            adapter.refresh()
            updateSummary()
        }

        setupBottomNav(R.id.nav_order)
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
        updateSummary()
    }

    private fun updateSummary() {
        val kosong = CartManager.isEmpty()
        findViewById<View>(R.id.text_empty).visibility = if (kosong) View.VISIBLE else View.GONE
        findViewById<View>(R.id.panel_checkout).visibility = if (kosong) View.GONE else View.VISIBLE
        findViewById<TextView>(R.id.text_total).text =
            CartManager.formatRupiah(CartManager.totalPrice())
    }
}
