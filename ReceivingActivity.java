package edu.byu.datapassingexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReceivingActivity extends AppCompatActivity {

    public static final String TEXT_KEY = "ReceivedTextKey";
    public static final String RESPONSE_RESULT_KEY = "EvaluationResultKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);

        TextView textView = findViewById(R.id.receivedTextView);
        Intent intent = getIntent();
        String receivedString = intent.getStringExtra(TEXT_KEY);
        textView.setText(receivedString);

        String reversedString = new StringBuilder(receivedString).reverse().toString();
        setResponseResult(reversedString);
    }

    private void setResponseResult(String responseResult) {
        Intent data = new Intent();
        data.putExtra(RESPONSE_RESULT_KEY, responseResult);
        setResult(Activity.RESULT_OK, data);
    }
}