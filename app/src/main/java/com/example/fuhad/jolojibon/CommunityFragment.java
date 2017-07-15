package com.example.fuhad.jolojibon;

/**
 * Created by Fuhad on 04-Mar-17.
 */

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CommunityFragment extends Fragment {

    String link =  Config.Community;
    WebView webView =null;
    TextView errorText;
    TextView re ;
    String email,name,top,det;
    Context context;

    SharedPreferences dataSave;

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_community_fargment, null);

        context = getActivity().getApplicationContext();

        dataSave = v.getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        name = dataSave.getString("username", "no user");
        email = dataSave.getString("email","no email");

        View subView = inflater.inflate(R.layout.dialog_post, null);
        final EditText userName = (EditText) subView.findViewById(R.id.name);
        final EditText userEmail = (EditText) subView.findViewById(R.id.emailname);
        final EditText topic = (EditText) subView.findViewById(R.id.topic);
        final EditText detail = (EditText) subView.findViewById(R.id.details);
        userName.setText(name);
        userEmail.setText(email);
        final AlertDialog.Builder postBuilder = new AlertDialog.Builder(v.getContext());
        postBuilder.setTitle("ENTER YOUR INFORMATION ");
        postBuilder.setView(subView);


        materialDesignFAM = (FloatingActionMenu) v.findViewById(R.id.social_floating_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_camera);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_gallery);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.floating_blog);



        webView = (WebView) v.findViewById(R.id.webViewCom);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.setWebChromeClient(new WebChromeClient());
        errorText = (TextView) v.findViewById(R.id.errorText2);
        re = (TextView) v.findViewById(R.id.retryText2);



        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
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
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                postBuilder.setPositiveButton("POST", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        top = topic.getText().toString();
                        det = detail.getText().toString();
                        if(!name.equals(null) && !email.equals(null)){
                            userName.setText(name);
                            userEmail.setText(email);
                        }else {
                            name = userName.getText().toString();
                            email = userEmail.getText().toString();
                        }

                        if(!top.equals("") && !det.equals("") && !name.equals("") && !email.equals("")){
                            new Encode_image().execute();
                        }else{
                            Toast.makeText(context,"Enter Fill up all text box above",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });


        if (isNetworkAvailable()) {
            webView.loadUrl(link);
            webView.setWebViewClient(new CommunityFragment.webChecker());
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
                        webView.setWebViewClient(new CommunityFragment.webChecker());
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
                                    webView.setWebViewClient(new CommunityFragment.webChecker());
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
                                webView.setWebViewClient(new CommunityFragment.webChecker());
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

    public class Encode_image extends AsyncTask<Void ,Void ,Void> {

        ProgressDialog load;
        //String encoded_string;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load = ProgressDialog.show(context, "Submitting Problem", "Please wait...", false, false);

        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    },new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("volly",error+"");
                    Toast.makeText(context,"volley error in submitting "+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("name",name);
                    hashMap.put("email",email);
                    hashMap.put("topic",top);
                    hashMap.put("detail",det);

                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            load.dismiss();
            Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();

        }
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
                        view.setWebViewClient(new CommunityFragment.webChecker());
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
                                    viewz.setWebViewClient(new CommunityFragment.webChecker());
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