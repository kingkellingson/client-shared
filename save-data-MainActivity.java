package edu.byu.viewmodelexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel getViewModel() {
        return new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView clickCountValue = findViewById(R.id.countTextView);
        clickCountValue.setText(" " + getViewModel().getClickCount());

        Button incrementButton = findViewById(R.id.button);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewModel().incrementClickCount();
                clickCountValue.setText(" " + getViewModel().getClickCount());
            }
        });
    }
}
