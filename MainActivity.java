package edu.byu.datapassingexample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        Button passTextButton = findViewById(R.id.passTextButton);

        // Setup an activity launcher with a callback for the result
        ActivityResultLauncher<Intent> receivingActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent resultData = result.getData();
                            if(resultData != null) {
                                String responseString = resultData.getStringExtra(ReceivingActivity.RESPONSE_RESULT_KEY);
                                if(responseString == null) {
                                    responseString = "";
                                }

                                responseTextView.setText(responseString);
                            }
                        }
                    }
                });

        passTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReceivingActivity.class);
                intent.putExtra(ReceivingActivity.TEXT_KEY, editText.getText().toString());
                receivingActivityLauncher.launch(intent);
            }
        });

        responseTextView = findViewById(R.id.responseTextView);
    }
}