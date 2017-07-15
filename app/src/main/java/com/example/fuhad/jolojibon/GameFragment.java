package com.example.fuhad.jolojibon;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

public class GameFragment extends Fragment {

    String link =  Config.GAME;
    WebView webView =null;
    TextView errorText;
    TextView re ;

    /*FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;
*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_game_fragment, null);

        /*materialDesignFAM = (FloatingActionMenu) v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_camera);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_gallery);
*/


        webView = (WebView) v.findViewById(R.id.webViewGame);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.setWebChromeClient(new WebChromeClient());
        errorText = (TextView) v.findViewById(R.id.errorText2);
        re = (TextView) v.findViewById(R.id.retryText2);



        /*floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                webView.goBack();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                webView.loadUrl(link);
            }
        });
*/

        if (isNetworkAvailable()) {
            webView.loadUrl(link);
            webView.setWebViewClient(new GameFragment.webChecker());
            ProgressD();
            webView.setVisibility(View.VISIBLE);

        } else {
            // display error
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Error");
            builder.setMessage("No Internet Connection");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isNetworkAvailable()) {
                        // fetch data
                        webView.loadUrl(link);
                        webView.setWebViewClient(new GameFragment.webChecker());
                        ProgressD();
                        webView.setVisibility(View.VISIBLE);
                    } else {
                        webView.setVisibility(View.INVISIBLE);
                        errorText.setVisibility(View.VISIBLE);
                        re.setVisibility(View.VISIBLE);
                        re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkAvailable()) {
                                    // fetch data
                                    webView.loadUrl(link);
                                    webView.setWebViewClient(new GameFragment.webChecker());
                                    ProgressD();
                                    webView.setVisibility(View.VISIBLE);
                                    errorText.setVisibility(View.INVISIBLE);
                                    re.setVisibility(View.INVISIBLE);

                                } else {
                                    Toast.makeText(v.getContext(),"No Internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    webView.setVisibility(View.INVISIBLE);
                    errorText.setVisibility(View.VISIBLE);
                    re.setVisibility(View.VISIBLE);

                    //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
                    re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isNetworkAvailable()) {
                                // fetch data
                                webView.loadUrl(link);
                                webView.setWebViewClient(new GameFragment.webChecker());
                                ProgressD();
                                webView.setVisibility(View.VISIBLE);
                                errorText.setVisibility(View.INVISIBLE);
                                re.setVisibility(View.INVISIBLE);

                            } else {
                                Toast.makeText(v.getContext(),"No Internet connection",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return v;
    }
    public void  ProgressD (){
        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 10 seconds...

                    Thread.sleep(6000);



                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();

            }
        }).start();
    }

    public boolean  isNetworkAvailable(){
        final ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null;
    }



    private class webChecker extends WebViewClient {
        /*MapsFragment nf = new MapsFragment();
        nf.*/
        //webView.getSettings().setJavaScriptEnabled(true);
        @Override
        public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
            view.setVisibility(View.INVISIBLE);
            // webView.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.VISIBLE);
            re.setVisibility(View.VISIBLE);
            final boolean[] z = {false};
            //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        // fetch data
                        z[0] = true;
                        /*webView.loadUrl(link);
                        webView.setWebViewClient(new webChecker());*/
                        //view.setVisibility(View.INVISIBLE);
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
                        ringProgressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Here you should write your time consuming task...
                                    // Let the progress ring for 10 seconds...

                                    Thread.sleep(6000);



                                } catch (Exception e) {

                                }
                                ringProgressDialog.dismiss();

                            }
                        }).start();

                        errorText.setVisibility(View.INVISIBLE);
                        re.setVisibility(View.INVISIBLE);
                        view.loadUrl(link);
                        view.setWebViewClient(new GameFragment.webChecker());
                        if(z[0])
                        {
                            view.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(v.getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedError (final WebView viewz, int errorCode, String description, String failingUrl){
            viewz.setVisibility(View.INVISIBLE);
            // webView.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.VISIBLE);
            re.setVisibility(View.VISIBLE);
            final boolean[] x = {false};

            //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        x[0] = true;
                        // fetch data
                        /*webView.loadUrl(link);
                        webView.setWebViewClient(new webChecker());*/
                        viewz.setVisibility(View.INVISIBLE);
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
                        ringProgressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Here you should write your time consuming task...
                                    // Let the progress ring for 10 seconds...

                                    viewz.loadUrl(link);
                                    viewz.setWebViewClient(new GameFragment.webChecker());
                                    Thread.sleep(6000);

                                    viewz.setVisibility(View.VISIBLE);

                                } catch (Exception e) {

                                }
                                ringProgressDialog.dismiss();

                            }
                        }).start();

                        errorText.setVisibility(View.INVISIBLE);
                        re.setVisibility(View.INVISIBLE);

                        /*viewz.loadUrl(link);
                        viewz.setWebViewClient(new webChecker());
                        if(x[0]) {
                            viewz.setVisibility(View.VISIBLE);
                        }*/
                    } else {
                        Toast.makeText(v.getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            super.onReceivedError(viewz, errorCode, description, failingUrl);
        }
    }
}