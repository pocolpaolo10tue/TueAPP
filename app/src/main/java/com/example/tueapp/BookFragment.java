package com.example.tueapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class BookFragment extends Fragment {

    /**
     * required empty public constructor
     */
    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * @param inflater inflater
     * @param container container to put fragment in
     * @param savedInstanceState savedInstance
     * @return webview fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewBook = inflater.inflate(R.layout.fragment_book, container, false);
        WebView webView = viewBook.findViewById(R.id.WebView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://fmis.tue.nl/case/tue/RES_001");
        return viewBook;
    }
}