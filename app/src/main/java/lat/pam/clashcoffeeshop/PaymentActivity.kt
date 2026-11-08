package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (CartManager.isEmpty()) {
            Toast.makeText(this, "Keranjang kosong, tidak ada yang perlu dibayar.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        setContentView(R.layout.activity_payment)

        val nama = intent.getStringExtra("nama") ?: SessionManager.getName(this)
        val alamat = intent.getStringExtra("alamat") ?: "-"
        val patokan = intent.getStringExtra("patokan") ?: ""
        val metode = intent.getStringExtra("metode") ?: "Transfer Bank"
        val isQris = metode == "QRIS"

        findViewById<TextView>(R.id.text_method).text = "Metode: " + metode
        findViewById<TextView>(R.id.text_total).text =
            CartManager.formatRupiah(CartManager.totalPrice())
        findViewById<View>(R.id.panel_transfer).visibility = if (isQris) View.GONE else View.VISIBLE
        findViewById<View>(R.id.panel_qris).visibility = if (isQris) View.VISIBLE else View.GONE

        findViewById<Button>(R.id.button_paid).setOnClickListener {
            if (CartManager.isEmpty()) return@setOnClickListener

            val order = OrderHistory.place(
                this, nama, alamat, patokan, metode, "Dibayar - sedang diproses"
            )
            val confirmIntent = Intent(this, ConfirmationActivity::class.java)
            confirmIntent.putExtra("order_id", order.id)
            startActivity(confirmIntent)
        }
    }
}
