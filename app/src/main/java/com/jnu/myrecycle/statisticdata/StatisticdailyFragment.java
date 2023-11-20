package com.jnu.myrecycle.statisticdata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.myrecycle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticdailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticdailyFragment extends Fragment {

    private int parentPosition;
    private int childPosition;

    public StatisticdailyFragment() {
        // Required empty public constructor
    }

    public static StatisticdailyFragment newInstance(int parentPosition, int childPosition) {
        StatisticdailyFragment fragment = new StatisticdailyFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_statisticdaily, container, false);
        return rootView;
        }
}