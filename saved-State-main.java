import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CLICK_COUNT_KEY = "ClickCount";

    private int clickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            clickCount = savedInstanceState.getInt(CLICK_COUNT_KEY, 0);
        }

        TextView clickCountValue = findViewById(R.id.countTextView);
        clickCountValue.setText(" " + clickCount);

        Button incrementButton = findViewById(R.id.button);
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                clickCountValue.setText(" " + clickCount);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CLICK_COUNT_KEY, clickCount);
    }
}
