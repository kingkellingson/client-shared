package edu.byu.cs.asyncwebaccess;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private static final String TOTAL_SIZE_KEY = "TotalSizeKey";

    private TextView totalSizeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalSizeTextView = findViewById(R.id.totalSizeTextView);

        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    resetTotalSize();

                    // Set up a handler that will process messages from the task and make updates on the UI thread
                    Handler uiThreadMessageHandler = new Handler() {
                        @Override
                        public void handleMessage(Message message) {
                            Bundle bundle = message.getData();
                            long totalSize = bundle.getLong(TOTAL_SIZE_KEY, 0);
                            totalSizeTextView.setText(getString(R.string.downloadSizeLabel, totalSize));
                            Context context = getApplicationContext();
                            CharSequence text = "Hello toast!";
                            int duration = Toast.LENGTH_SHORT;
                            
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    };

                    // Create and execute the download task on a separate thread
                    DownloadTask task = new DownloadTask(uiThreadMessageHandler, new URL("https://www.byu.edu"),
                            new URL("https://www.whitehouse.gov/"),
                            new URL("https://www.oracle.com/index.html"));
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.submit(task);
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }
        });

        resetTotalSize();
    }

    private void resetTotalSize() {
        totalSizeTextView.setText(getString(R.string.downloadSizeEmptyLabel));
    }

    private static class DownloadTask implements Runnable {

        private final Handler messageHandler;

        private final URL[] urls;

        public DownloadTask(Handler messageHandler, URL... urls) {
            this.messageHandler = messageHandler;
            this.urls = urls;
        }

        @Override
        public void run() {
            HttpClient httpClient = new HttpClient();

            long totalSize = 0;

            for (URL url : urls) {
                String urlContent = httpClient.getUrl(url);
                if (urlContent != null) {
                    totalSize += urlContent.length();
                }
            }

            sendMessage(totalSize);
        }

        private void sendMessage(long totalSize) {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putLong(TOTAL_SIZE_KEY, totalSize);
            message.setData(messageBundle);

            messageHandler.sendMessage(message);
        }
    }
}
