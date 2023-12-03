package com.jnu.yidongzuoye.statisticdata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jnu.yidongzuoye.R;
public class StatisticmonthlyFragment extends Fragment {
    private int childPosition;

    private int parentPosition;
    public StatisticmonthlyFragment() {
        // Required empty public constructor
    }
    public static StatisticmonthlyFragment newInstance(int parentPosition, int childPosition) {
        StatisticmonthlyFragment fragment = new StatisticmonthlyFragment();
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
        return inflater.inflate(R.layout.fragment_statisticmonthly, container, false);
    }
}