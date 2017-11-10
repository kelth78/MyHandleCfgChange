package com.example.myhandlecfgchange;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

// Retain an object (e.g. fragment) during configuration change
public class RetainedFragment extends Fragment {

    private String storedData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment
        setRetainInstance(true);
    }

    public void setData(String someData) {
        storedData = someData;
    }

    public String getData() {
        return storedData;
    }
}
