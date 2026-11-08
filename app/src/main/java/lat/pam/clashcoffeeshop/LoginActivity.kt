package lat.pam.clashcoffeeshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editUsername = findViewById<EditText>(R.id.edit_username)
        val editPassword = findViewById<EditText>(R.id.edit_password)
        val textError = findViewById<TextView>(R.id.text_error)

        findViewById<Button>(R.id.button_login).setOnClickListener {
            val username = editUsername.text.toString().trim()
            val password = editPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                textError.text = "Username dan password wajib diisi"
                textError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (SessionManager.login(this, username, password)) {
                val homeIntent = Intent(this, HomeActivity::class.java)
                homeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(homeIntent)
            } else {
                textError.text = "Username atau password salah"
                textError.visibility = View.VISIBLE
            }
        }

        findViewById<TextView>(R.id.text_to_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
