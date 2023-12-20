package com.jnu.yidongzuoye.statisticdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jnu.yidongzuoye.R;

public class StatisticyearlyFragment extends Fragment {
    public StatisticyearlyFragment() {
        // Required empty public constructor
    }
    public static StatisticyearlyFragment newInstance() {
        StatisticyearlyFragment fragment = new StatisticyearlyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_statisticyearly, container, false);
    }
}