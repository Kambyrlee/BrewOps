package com.example.brewopscoffeeshoptracker.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.brewopscoffeeshoptracker.R;

public class CustomerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_customer, container, false);
    }
}
