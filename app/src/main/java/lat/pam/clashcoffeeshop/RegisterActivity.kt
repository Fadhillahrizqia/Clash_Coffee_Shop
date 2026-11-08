package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editFullname = findViewById<EditText>(R.id.edit_fullname)
        val editUsername = findViewById<EditText>(R.id.edit_username)
        val editPassword = findViewById<EditText>(R.id.edit_password)

        findViewById<Button>(R.id.button_register).setOnClickListener {
            val nama = editFullname.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString()

            if (nama.isEmpty()) {
                editFullname.error = "Nama lengkap wajib diisi"
                return@setOnClickListener
            }
            if (username.isEmpty()) {
                editUsername.error = "Username wajib diisi"
                return@setOnClickListener
            }
            if (password.length < 5) {
                editPassword.error = "Password minimal 5 karakter"
                return@setOnClickListener
            }

            SessionManager.register(this, nama, username, password)
            Toast.makeText(this, "Registrasi berhasil, selamat datang!", Toast.LENGTH_SHORT).show()

            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(homeIntent)
        }

        findViewById<TextView>(R.id.text_to_login).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
