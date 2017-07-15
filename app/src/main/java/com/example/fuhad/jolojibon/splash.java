package com.example.fuhad.jolojibon;


        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.Configuration;
        import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

        import java.util.Locale;

public class splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private SharedPreferences dataSave;
    Configuration config = new Configuration();
    Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                if (dataSave.getBoolean("SelectLanguage", false)) {
                    if (!dataSave.getBoolean("LanguageEnglish", true)) {
                        locale = new Locale("bn");
                        config.locale =locale;
                        getResources().updateConfiguration(config, null);
                        Toast.makeText(splash.this,"Bangla",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        config.locale = Locale.ENGLISH;
                        getResources().updateConfiguration(config, null);
                        Toast.makeText(splash.this,"English",Toast.LENGTH_LONG).show();
                    }

                    if (!dataSave.getBoolean("LoggedIn", false)) {

                        startActivity(new Intent(splash.this, LoginActivity.class));
                        finish();

                    } else {
                        startActivity(new Intent(splash.this, MainActivity.class));
                        finish();
                    }
                }
                else
                {
                    startActivity(new Intent(splash.this, Language.class));
                    finish();
                }



                // close this activity
                //finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
