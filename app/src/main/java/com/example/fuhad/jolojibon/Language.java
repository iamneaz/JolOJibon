package com.example.fuhad.jolojibon;


        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.Configuration;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.util.Locale;

public class Language extends AppCompatActivity {

    Configuration config = new Configuration();
    Locale locale;
    SharedPreferences dataSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = dataSave.edit();
        editor.putBoolean("SelectLanguage",true);
        editor.commit();



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.locale = Locale.ENGLISH;
                getResources().updateConfiguration(config, null);
                SharedPreferences.Editor editor = dataSave.edit();

                editor.putBoolean("LanguageEnglish",true);
                editor.commit();
                Intent intent = new Intent(Language.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locale = new Locale("bn");
                config.locale =locale;
                getResources().updateConfiguration(config, null);


                Intent intent = new Intent(Language.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
