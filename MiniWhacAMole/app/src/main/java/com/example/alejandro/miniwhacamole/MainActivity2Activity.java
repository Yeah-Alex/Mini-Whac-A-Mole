package com.example.alejandro.miniwhacamole;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class MainActivity2Activity extends AppCompatActivity {
    TableLayout topos;
    TextView tiempo, puntuacion;
    MediaPlayer musica;
    SoundPool soundPool;
    int golpe;
    byte segundos = 60;
    int puntos = 0;
    byte numeroAleatorio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        topos = (TableLayout) findViewById(R.id.topos);
        tiempo = (TextView) findViewById(R.id.tiempo);
        puntuacion = (TextView) findViewById(R.id.puntuacion);

        musica = MediaPlayer.create(this, R.raw.musica);

        soundPool = new SoundPool( 10, AudioManager.STREAM_MUSIC , 0);
        golpe = soundPool.load(this, R.raw.golpe, 0);

        iniciarJuego();
        iniciarTiempo();
    }

    private void iniciarTiempo() {
        musica.start();

        @SuppressLint("HandlerLeak") final Handler manejador = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                tiempo.setText("TIEMPO: " + msg.what);

                if (msg.what <= 0) {
                    musica.stop();

                    AlertDialog.Builder cajaAlerta = new  AlertDialog.Builder(MainActivity2Activity.this);
                    cajaAlerta.setIcon(R.drawable.iconotopo);
                    cajaAlerta.setTitle("Se termino el tiempo :)");
                    cajaAlerta.setMessage("Obtuviste:  "+puntos+ "  puntos");
                    cajaAlerta.setCancelable(false);
                    cajaAlerta.setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), MainActivity3Activity.class);
                            intent.putExtra("miPuntuacion", puntos);
                            startActivity(intent);

                        }
                    });
                    cajaAlerta.setNegativeButton("Reiniciar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            segundos = 60;
                            puntos = 0;
                            numeroAleatorio = 0;
                            puntuacion.setText("PUNTUACIÓN: " + puntos);
                            tiempo.setText("TIEMPO: "+segundos);
                            iniciarJuego();
                            iniciarTiempo();
                        }
                    });
                    cajaAlerta.show();
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (byte i=segundos; i>=0;--i){
                    try {
                        manejador.sendMessage(manejador.obtainMessage(i));
                        sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void iniciarJuego() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (segundos >= 1) {
                    try {
                        sleep(1000);
                        mostrarTopos();
                        sleep(1000);
                        desapareceTopos();
                        segundos -= 2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void mostrarTopos() {
        numeroAleatorio = (byte) (1 + Math.random() * 6);

        for (byte i = 1; i <= numeroAleatorio; i++) {
            final ImageView topo = (ImageView) topos.findViewWithTag(String.valueOf(i));

            topo.post(new Runnable() {
                @Override
                public void run() {
                    topo.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void desapareceTopos() {
        for (byte i = numeroAleatorio; i >= 1; i--) {
            final ImageView topo = (ImageView) topos.findViewWithTag(String.valueOf(i));
            topo.post(new Runnable() {
                @Override
                public void run() {
                    topo.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    public void desaparecerTopo(View view){
        soundPool.play(golpe, 1, 1, 1, 0, 1);
        view.setVisibility(View.INVISIBLE);
        mostrarPuntos();
    }

    private void mostrarPuntos() {
        puntos += 1000;
        puntuacion.setText("PUNTUACIÓN: " + puntos);
    }

    @Override
    public void onPause() {
        musica.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        musica.start();
        super.onResume();
    }
}


