package edu.byu.viewmodelexample;

import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    private int clickCount;

    public int getClickCount() {
        return clickCount;
    }

    public void incrementClickCount() {
        clickCount++;
    }
}
