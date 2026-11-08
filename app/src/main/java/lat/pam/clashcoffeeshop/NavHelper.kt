package lat.pam.clashcoffeeshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun AppCompatActivity.setupBottomNav(selectedId: Int) {
    val nav = findViewById<BottomNavigationView>(R.id.bottom_nav)
    nav.selectedItemId = selectedId
    nav.setOnItemSelectedListener { item ->
        if (item.itemId == selectedId) {
            true
        } else {
            when (item.itemId) {
                R.id.nav_home -> {
                    val homeIntent = Intent(this, HomeActivity::class.java)
                    homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(homeIntent)
                }
                R.id.nav_order -> startActivity(Intent(this, CartActivity::class.java))
                R.id.nav_history -> startActivity(Intent(this, HistoryActivity::class.java))
                R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            false
        }
    }
}
