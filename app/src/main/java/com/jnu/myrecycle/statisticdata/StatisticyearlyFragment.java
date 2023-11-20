package com.jnu.myrecycle.statisticdata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.myrecycle.R;


public class StatisticyearlyFragment extends Fragment {


    private int childPosition;

    private int parentPosition;

    public StatisticyearlyFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static StatisticyearlyFragment newInstance(int parentPosition, int childPosition) {
        StatisticyearlyFragment fragment = new StatisticyearlyFragment();
        Bundle args = new Bundle();

        fragment.parentPosition = parentPosition;
        fragment.childPosition = childPosition;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statisticyearly, container, false);
    }
}