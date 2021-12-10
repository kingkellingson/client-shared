package edu.byu.fragmentdatapassingexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReceivingFragment extends Fragment {

    public static final String TEXT_KEY = "ReceivedTextKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receiving, container, false);

        if (getArguments() != null) {
            TextView textView = view.findViewById(R.id.receivedTextView);
            String receivedText = getArguments().getString(TEXT_KEY);
            textView.setText(receivedText);
        }

        return view;
    }
}