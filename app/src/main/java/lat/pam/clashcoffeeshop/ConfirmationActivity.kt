package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val orderId = intent.getStringExtra("order_id") ?: ""
        val order = OrderHistory.getById(this, orderId)

        val nama = order?.nama ?: SessionManager.getName(this)
        findViewById<TextView>(R.id.text_greeting).text = "Halo " + nama + ","

        if (order != null) {
            findViewById<TextView>(R.id.text_order_id).text =
                "No. Pesanan " + order.id + "  |  " + order.timeText()
            findViewById<TextView>(R.id.text_order_items).text =
                order.ringkasan + "\n\nTotal: " + CartManager.formatRupiah(order.total)
            findViewById<TextView>(R.id.text_payment).text =
                order.metode + "\n" + order.status
            findViewById<TextView>(R.id.text_address).text =
                if (order.patokan.isEmpty()) order.alamat else order.alamat + "\n" + order.patokan

            val cod = order.metode.startsWith("Cash")
            findViewById<TextView>(R.id.text_message).text =
                if (cod) {
                    "Terima kasih sudah memesan. Siapkan uang tunai, kurir kami segera mengantarkan pesananmu!"
                } else {
                    "Terima kasih sudah memesan. Pembayaran diterima, kurir kami segera mengantarkan pesananmu!"
                }
        }

        findViewById<Button>(R.id.button_history).setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        findViewById<Button>(R.id.button_ok).setOnClickListener {
            goHome()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goHome()
            }
        })
    }

    private fun goHome() {
        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(homeIntent)
        finish()
    }
}
