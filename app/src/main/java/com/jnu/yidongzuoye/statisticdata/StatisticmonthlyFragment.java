package com.jnu.yidongzuoye.statisticdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jnu.yidongzuoye.R;

public class StatisticmonthlyFragment extends Fragment {
    public StatisticmonthlyFragment() {
        // Required empty public constructor
    }
    public static StatisticmonthlyFragment newInstance() {
        StatisticmonthlyFragment fragment = new StatisticmonthlyFragment();
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
        return inflater.inflate(R.layout.fragment_statisticmonthly, container, false);
    }

}