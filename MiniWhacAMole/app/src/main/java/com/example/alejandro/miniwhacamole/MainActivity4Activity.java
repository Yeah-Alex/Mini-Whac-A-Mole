package com.example.alejandro.miniwhacamole;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity4Activity extends AppCompatActivity {

    String SQL;
    ControlaBD bd;
    SQLiteDatabase bdControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4);

        bd = new ControlaBD(this, "Record", null, 1);
        bdControl= bd.getWritableDatabase();
    }

    public void onClickEliminar(View view){
        try{
            SQL = "DELETE FROM Alumno";
            bdControl.execSQL(SQL);
            Toast.makeText(getBaseContext(), "Tabla borrada", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){

            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void salir(View view){
        finish();
    }

}
