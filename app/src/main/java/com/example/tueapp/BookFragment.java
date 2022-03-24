package com.example.tueapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BookFragment extends Fragment {

    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewBook = inflater.inflate(R.layout.fragment_book, container, false);
        WebView webView = (WebView)viewBook.findViewById(R.id.WebView);

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