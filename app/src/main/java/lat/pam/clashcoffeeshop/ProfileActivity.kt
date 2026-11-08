package lat.pam.clashcoffeeshop

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val textGreeting = findViewById<TextView>(R.id.text_greeting)
        val textAvatar = findViewById<TextView>(R.id.text_avatar)
        val editName = findViewById<EditText>(R.id.edit_name)

        val currentName = SessionManager.getName(this)
        textGreeting.text = "Halo " + currentName + ","
        textAvatar.text = currentName.take(1).uppercase()
        editName.setText(currentName)

        findViewById<Button>(R.id.button_save).setOnClickListener {
            val newName = editName.text.toString().trim()
            if (newName.isEmpty()) {
                editName.error = "Nama tidak boleh kosong"
                return@setOnClickListener
            }

            SessionManager.setName(this, newName)
            textGreeting.text = "Halo " + newName + ","
            textAvatar.text = newName.take(1).uppercase()
            Toast.makeText(this, "Profile berhasil disimpan", Toast.LENGTH_SHORT).show()
        }

        setupBottomNav(R.id.nav_profile)
    }
}
