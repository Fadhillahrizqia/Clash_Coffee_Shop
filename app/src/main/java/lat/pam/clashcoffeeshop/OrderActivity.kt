package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val editNama = findViewById<EditText>(R.id.edit_nama)
        val editAlamat = findViewById<EditText>(R.id.edit_alamat)
        val editPatokan = findViewById<EditText>(R.id.edit_patokan)
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_payment)

        editNama.setText(SessionManager.getName(this))

        val textSummary = findViewById<TextView>(R.id.text_order_summary)
        if (CartManager.isEmpty()) {
            textSummary.text = "Keranjang masih kosong. Pilih menu dulu di Home."
        } else {
            textSummary.text = CartManager.summary() + "\n\nTotal: " +
                CartManager.formatRupiah(CartManager.totalPrice())
        }

        findViewById<TextView>(R.id.text_edit_cart).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.button_order).setOnClickListener {
            val nama = editNama.text.toString().trim()
            val alamat = editAlamat.text.toString().trim()
            val patokan = editPatokan.text.toString().trim()
            val pilihan = radioGroup.checkedRadioButtonId

            if (CartManager.isEmpty()) {
                Toast.makeText(this, "Keranjang masih kosong. Pilih menu dulu di Home.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nama.isEmpty()) {
                editNama.error = "Nama lengkap wajib diisi"
                return@setOnClickListener
            }
            if (alamat.isEmpty()) {
                editAlamat.error = "Alamat wajib diisi"
                return@setOnClickListener
            }
            if (pilihan == -1) {
                Toast.makeText(this, "Pilih metode pembayaran dulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val metode = when (pilihan) {
                R.id.radio_cod -> "Cash di tempat (COD)"
                R.id.radio_transfer -> "Transfer Bank"
                else -> "QRIS"
            }

            if (pilihan == R.id.radio_cod) {
                val order = OrderHistory.place(
                    this, nama, alamat, patokan, metode, "Diproses - bayar di tempat"
                )
                val confirmIntent = Intent(this, ConfirmationActivity::class.java)
                confirmIntent.putExtra("order_id", order.id)
                startActivity(confirmIntent)
            } else {
                val payIntent = Intent(this, PaymentActivity::class.java)
                payIntent.putExtra("nama", nama)
                payIntent.putExtra("alamat", alamat)
                payIntent.putExtra("patokan", patokan)
                payIntent.putExtra("metode", metode)
                startActivity(payIntent)
            }
        }
    }
}
