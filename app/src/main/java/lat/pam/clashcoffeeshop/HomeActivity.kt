package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recycler = findViewById<RecyclerView>(R.id.recycler_menu)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FoodAdapter(
            FoodData.items,
            onAddClick = { item ->
                CartManager.add(item)
                updateCartBar()
                Toast.makeText(this, item.name + " ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            },
            onRemoveClick = { item ->
                CartManager.remove(item)
                updateCartBar()
            }
        )

        findViewById<View>(R.id.cart_bar).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        setupBottomNav(R.id.nav_home)
    }

    override fun onResume() {
        super.onResume()
        findViewById<TextView>(R.id.text_greeting).text =
            "Halo " + SessionManager.getName(this) + ","
        findViewById<RecyclerView>(R.id.recycler_menu).adapter?.notifyDataSetChanged()
        updateCartBar()
    }

    private fun updateCartBar() {
        val bar = findViewById<View>(R.id.cart_bar)
        if (CartManager.isEmpty()) {
            bar.visibility = View.GONE
        } else {
            findViewById<TextView>(R.id.text_cart_summary).text =
                CartManager.totalItems().toString() + " item  |  " +
                    CartManager.formatRupiah(CartManager.totalPrice())
            bar.visibility = View.VISIBLE
        }
    }
}
