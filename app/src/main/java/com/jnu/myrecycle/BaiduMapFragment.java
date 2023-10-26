package com.jnu.myrecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class BaiduMapFragment extends Fragment {


    public BaiduMapFragment() {
        // Required empty public constructor
    }

    public static BaiduMapFragment newInstance(String param1, String param2) {
        BaiduMapFragment fragment = new BaiduMapFragment();
        Bundle args = new Bundle();
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
        View rootview = inflater.inflate(R.layout.fragment_baidu_map, container, false);

        WebView webView = rootview.findViewById(R.id.baidu_map);
        webView.getSettings().setJavaScriptEnabled(true); // 启用JavaScript
        webView.setWebViewClient(new WebViewClient()); // 设置WebViewClient，处理页面加载事件
        webView.loadUrl("https://map.baidu.com/"); // 加载百度地图网页
        return rootview;
    }
}