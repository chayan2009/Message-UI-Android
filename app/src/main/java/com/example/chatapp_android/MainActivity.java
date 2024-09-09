package com.example.chatapp_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ChatAdapter chatAdapter;
    private final List<Message> messages = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EditText etMessage = findViewById(R.id.etMessage);
        Button btnSend = findViewById(R.id.btnSend);

        // Set up RecyclerView
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Add static bot message
        messages.add(new Message("Welcome! Ask about our products: Smartphone, Laptop, or Smartwatch.", false));
        chatAdapter.notifyDataSetChanged();

        btnSend.setOnClickListener(view -> {
            String userMessage = etMessage.getText().toString();
            if (!userMessage.isEmpty()) {
                // Add user message
                messages.add(new Message(userMessage, true));
                etMessage.getText().clear();

                // Add bot reply based on the user input
                String botReply = getBotReply(userMessage);
                messages.add(new Message(botReply, false));
                chatAdapter.notifyDataSetChanged();

                // Scroll to the latest message
                recyclerView.scrollToPosition(messages.size() - 1);
            }
        });
    }

    private String getBotReply(String userMessage) {
        String lowerCaseMessage = userMessage.toLowerCase(Locale.getDefault());
        if (lowerCaseMessage.contains("smartphone")) {
            return "This smartphone features a 6.5-inch display, 128GB storage, 48MP camera, and a long-lasting battery.";
        } else if (lowerCaseMessage.contains("laptop")) {
            return "A powerful laptop with a 15.6-inch display, 16GB RAM, 512GB SSD, and an Intel Core i7 processor.";
        } else if (lowerCaseMessage.contains("smartwatch")) {
            return "The smartwatch offers fitness tracking, heart rate monitoring, GPS, and 7-day battery life.";
        } else {
            return "Sorry, I don't have information on that product. Please ask about our Smartphone, Laptop, or Smartwatch.";
        }
    }
}
