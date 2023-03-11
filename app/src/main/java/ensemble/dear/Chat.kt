package ensemble.dear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class Chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val buttonChatSendMessage = findViewById<ImageView>(R.id.buttonChatSendMessage)
        buttonChatSendMessage.setOnClickListener{
            Toast.makeText(applicationContext, "Sending message", Toast.LENGTH_LONG).show()
        }
    }
}