package edu.byu.fragmentdatapassingexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

public class ReceivingActivity extends AppCompatActivity {

    public static final String TEXT_KEY = "ReceivedTextKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);

        Intent intent = getIntent();
        String receivedText = intent.getStringExtra(TEXT_KEY);

        FragmentManager fm = this.getSupportFragmentManager();
        ReceivingFragment fragment = (ReceivingFragment) fm.findFragmentById(R.id.fragmentFrameLayout);
        if (fragment == null) {
            fragment = createReceivingFragment(receivedText);
            fm.beginTransaction()
                    .add(R.id.fragmentFrameLayout, fragment)
                    .commit();
        }
    }

    private ReceivingFragment createReceivingFragment(String text) {
        ReceivingFragment fragment = new ReceivingFragment();

        Bundle arguments = new Bundle();
        arguments.putString(ReceivingFragment.TEXT_KEY, text);
        fragment.setArguments(arguments);

        return fragment;
    }
}