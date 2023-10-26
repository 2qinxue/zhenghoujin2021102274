package com.jnu.myrecycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class WebFragment extends Fragment {
    public WebFragment() {
        // Required empty public constructor
    }
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_web, container, false);

        WebView webview = rootview.findViewById(R.id.web_view);
        webview.loadUrl("https://www.baidu.com");
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        Button refreshButton = rootview.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.reload(); // 刷新网页
            }
        });
        // Inflate the layout for this fragment
        return rootview;

    }

    private class WebViewClient extends android.webkit.WebViewClient {
    }
}