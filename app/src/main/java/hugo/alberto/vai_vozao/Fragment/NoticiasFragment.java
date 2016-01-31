package hugo.alberto.vai_vozao.Fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import hugo.alberto.vai_vozao.R;


public class NoticiasFragment extends Fragment {
    public WebView webview_noticias;
    private static final String baseURl = "https://twitter.com";
    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/CearaSC\" data-widget-id=\"693847934614540288\"  data-chrome=\"noheader noborders nofooter\" ></a>" +
            "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.noticias_view, container, false);

        webview_noticias = (WebView) rootView.findViewById(R.id.noticias);

        webview_noticias.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(getActivity())) {
            webview_noticias.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_noticias.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_noticias.getSettings().setDomStorageEnabled(true);
        webview_noticias.getSettings().setJavaScriptEnabled(true);
        webview_noticias.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);

        AdView mAdView_tabela = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest_tabela = new AdRequest.Builder().build();
        mAdView_tabela.loadAd(adRequest_tabela);

        return rootView;
    }
    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }



}