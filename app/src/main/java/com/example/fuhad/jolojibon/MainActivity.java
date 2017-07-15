package com.example.fuhad.jolojibon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private RequestQueue requestQueue;
    private StringRequest request;
    AlertDialog.Builder update = null;
    AlertDialog.Builder exitbuilder = null;
    Dialog ranPop = null;
    String longi = "0";
    String lati = "0";

    Context context =this;
    private String verName;
    private int verCode;

    boolean islogin;
    SharedPreferences dataSave;
    /*LoggedUser lu;
    Bundle extras;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* extras = getIntent().getExtras();
        if (extras != null) {
            lu = (LoggedUser)getIntent().getSerializableExtra("userData"); //Obtaining data
            Toast.makeText(this,lu.getUserName(),Toast.LENGTH_SHORT).show();
        }*/
        //deleteCache(getApplicationContext());
        requestQueue = Volley.newRequestQueue(this);
        try{
            verName = context.getPackageManager().getPackageInfo (context.getPackageName(), 0).versionName;
            verCode = context.getPackageManager().getPackageInfo (context.getPackageName(), 0).versionCode;

            //Toast.makeText(this,verName+" , "+verCode,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("version",e+"");
            //Toast.makeText(this,e+"",Toast.LENGTH_SHORT).show();
        }

        dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        /*Toast.makeText(MainActivity.this,dataSave.getBoolean("LoggedIn",false)+" "+
                dataSave.getString("username","aa")+" "+
                dataSave.getString("email","bb")+" "+
                dataSave.getString("Com","nothing"),Toast.LENGTH_LONG).show();*/
        /*islogin = dataSave.getBoolean("LoggedIn",false);*/

        checker();
        //piechecker();



        ranPop=onCreateDialog();
        ranPop.show();


        update = new AlertDialog.Builder(this);
        update.setMessage("New Version is now available! Please update").setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //checkUpdate(context);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.APP_DOWNLOAD_URL));
                startActivity(browserIntent);
            }
        }).setNegativeButton("REMIND ME LATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        exitbuilder = new AlertDialog.Builder(this);

        final AlertDialog.Builder choose = new AlertDialog.Builder(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the way to upload").setItems(R.array.choice, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    choose.setMessage("Please Choose").setPositiveButton("Take Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Camera.class);
                            intent.putExtra("camera",true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).setNegativeButton("Shoot A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Camera.class);
                            intent.putExtra("camera",false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "camera open", Toast.LENGTH_SHORT).show();
                }
                if (which == 1) {
                    choose.setMessage("Please Choose").setPositiveButton("Choose Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), gallery.class);
                            intent.putExtra("camera",false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).setNegativeButton("Choose A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), gallery.class);
                            intent.putExtra("camera",true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "gellary open", Toast.LENGTH_SHORT).show();
                }
                if (which == 2) {
                    Intent intent = new Intent(getApplicationContext(),ProblemActivity.class);
                    intent.putExtra("nothing",true);
                    intent.putExtra("isImage",false);
                    intent.putExtra("isVideo",false);
                    intent.putExtra("longitude",longi);
                    intent.putExtra("latitude",lati);
                    startActivity(intent);
                    //finish();
                    //Toast.makeText(getApplicationContext(), "nothing open", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.loginNav) {


                    SharedPreferences.Editor editor = dataSave.edit();
                    editor.clear();
                    editor.commit();
                    deleteCache(getApplicationContext());
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(i);
                    finish();
                }

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    Intent i = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(i);
                    //finish();
                }

                if(menuItem.getItemId() == R.id.nav_item_exit){
                    exitbuilder.setMessage("Do you want to exit?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    deleteCache(getApplicationContext());
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    AlertDialog xd =  exitbuilder.create();
                    xd.show();
                }
                if (menuItem.getItemId() == R.id.report) {

                    Uri uriUrl = Uri.parse(Config.REPORT);
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
                if (menuItem.getItemId() == R.id.profile) {

                    //Uri uriUrl = Uri.parse(Config.REPORT);
                    Intent profiler = new Intent(MainActivity.this, Profile.class);
                    startActivity(profiler);
                }
                if (menuItem.getItemId() == R.id.emergencyNumbers) {

                    //Uri uriUrl = Uri.parse(Config.REPORT);
                    Intent profiler = new Intent(MainActivity.this, EmergencyNumbers.class);
                    startActivity(profiler);
                }
                /*if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }*/

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }

    public void checker (){
        request = new StringRequest(Request.Method.POST, Config.APP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean go = jsonObject.getBoolean("success");
                    //Toast.makeText(getBaseContext(),go+"",Toast.LENGTH_LONG).show();

                    if(go){
                        AlertDialog xd =  update.create();
                        xd.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(),"Connection or server Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("check","mcme");
                hashMap.put("vername",verName);
                hashMap.put("vercode",verCode+"");

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    /*public void piechecker (){
        request = new StringRequest(Request.Method.POST, Config.PIECHART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Config.WriteToFile(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Connection or server Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("give","mcme");
                return hashMap;
            }
        };
        requestQueue.add(request);
    }*/

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            Toast.makeText(context,e+"",Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exitbuilder.setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog xd =  exitbuilder.create();
        xd.show();
    }
    @Override
    public void onBackPressed() {
        exitbuilder.setMessage("Do you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog xd =  exitbuilder.create();
        xd.show();
    }

    //@Override
    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.ran_pop, null))
                // Add action buttons
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        Toast.makeText(getApplicationContext(),"Well done!!",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                        Toast.makeText(getApplicationContext(),"You shouldn't done it!!",Toast.LENGTH_SHORT).show();
                    }
                });
        return builder.create();
    }
}
