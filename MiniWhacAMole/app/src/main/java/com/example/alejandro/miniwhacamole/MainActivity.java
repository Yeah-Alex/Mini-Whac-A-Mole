package com.example.alejandro.miniwhacamole;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void juego(View view){
        Intent intent = new Intent(this, MainActivity2Activity.class);
        startActivity(intent);

    }

    public  void opciones(View view){
        Intent intent = new Intent(this, MainActivity4Activity.class);
        startActivity(intent);
    }

}
