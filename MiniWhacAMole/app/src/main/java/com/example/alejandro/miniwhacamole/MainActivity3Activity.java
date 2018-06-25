package com.example.alejandro.miniwhacamole;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3Activity extends AppCompatActivity {
    TextView error;
    TableLayout tabla;
    int n;


    String SQL;
    ControlaBD bd;
    SQLiteDatabase bdControl;
    Cursor apuntador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3);


        try{
            Intent intent = getIntent();
            int message = intent.getIntExtra("miPuntuacion",1);
            n=message;

            error = (TextView) findViewById(R.id.error);
            tabla = (TableLayout) findViewById(R.id.tabla);

            bd = new ControlaBD(this, "Record", null, 1);
            bdControl= bd.getWritableDatabase();

            mostrarVentana();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void mostrarVentana(){
        final AlertDialog.Builder cajaAlerta = new  AlertDialog.Builder(this);
        cajaAlerta.setCancelable(false);
        cajaAlerta.setTitle("Pon tu nombre campeon :)");
        cajaAlerta.setIcon(R.drawable.trofeoicono);
        final EditText cajaTexto = new EditText(this);
        cajaAlerta.setView(cajaTexto);
        cajaAlerta.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = cajaTexto.getText().toString();
                onClickInsertar(nombre);
                onClickConsultar();
            }
        });
        cajaAlerta.show();

    }

    public void onClickInsertar(String nom){
        try{
            SQL = "INSERT INTO Alumno(nombre,promedio) " +
                    "VALUES('" +
                    nom + "', " +
                    n +
                    ")";
            bdControl.execSQL(SQL);
            Toast.makeText(getBaseContext(),"Felicidades!!",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }



    public void onClickConsultar(){
        try {
            SQL ="SELECT * FROM Alumno";
            apuntador=bdControl.rawQuery(SQL, null);

            if(apuntador.moveToFirst()){
                do{
                    TableRow row = new TableRow(this);
                    row.setGravity(Gravity.CENTER);

                    TextView miNombre = new TextView(this);
                    miNombre.setText(apuntador.getString(0)+"        ");
                    miNombre.setTextSize(18);
                    miNombre.setTextColor(Color.parseColor("#f5f5f5"));
                    row.addView(miNombre);

                    TextView miPunto = new TextView(this);
                    miPunto.setText(apuntador.getString(1));
                    miPunto.setTextSize(18);
                    miPunto.setTextColor(Color.parseColor("#f5f5f5"));
                    row.addView(miPunto);

                    tabla.addView(row);
                }while (apuntador.moveToNext());
            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    public void salir(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
