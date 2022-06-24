package com.example.cosahoatrento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class activity_aggiungi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);
        LinearLayout root = findViewById(R.id.rootLayout);
        LinearLayout l1 = findViewById(R.id.vertical1);
        LinearLayout l2 = findViewById(R.id.vertical2);
        LinearLayout l3 = findViewById(R.id.vertical3);
        LinearLayout l4 = findViewById(R.id.vertical4);
        LinearLayout l5 = findViewById(R.id.vertical5);

        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getElenco();
        if(cursor.getCount()==0){
            //no vestiti trovati
            TextView text = new TextView(this);
            text.setText("Nessun vestito trovato");
            l2.addView(text);
        }else{
            //mostro elenco vestiti DA PROVARE E SISTEMARE
            int width= LinearLayout.LayoutParams.MATCH_PARENT;
            int height = 150;
            LinearLayout rowlayout = new LinearLayout(this);
            rowlayout.setOrientation(LinearLayout.HORIZONTAL);
            int index=1;
            while(cursor.moveToNext()) {
                //textview indice
                TextView t1 = new TextView(this);
                t1.setText(index+"");
                index++;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
                t1.setLayoutParams(params);
                l1.addView(t1);

                //linea nera divisoria
                TextView line = new TextView(this);
                line.setBackgroundColor(getResources().getColor(R.color.black));
                line.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line.setLayoutParams(params);
                l1.addView(line);

                //textview nome vestito
                TextView t2 = new TextView(this);
                t2.setText(cursor.getString(1)+"");
                params = new LinearLayout.LayoutParams(width,height);
                t2.setLayoutParams(params);
                l2.addView(t2);

                //linea nera divisoria
                TextView line2 = new TextView(this);
                line2.setBackgroundColor(getResources().getColor(R.color.black));
                line2.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line2.setLayoutParams(params);
                l2.addView(line2);

                //textview quantità
                TextView t3 = new TextView(this);
                t3.setText(cursor.getInt(2)+"");
                params = new LinearLayout.LayoutParams(width,height);
                t3.setId(cursor.getInt(0));
                t3.setLayoutParams(params);
                l3.addView(t3);

                //linea nera divisoria
                TextView line3 = new TextView(this);
                line3.setBackgroundColor(getResources().getColor(R.color.black));
                line3.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line3.setLayoutParams(params);
                l3.addView(line3);

                //bottone aggiunta quantità
                Button plus = new Button(this);
                plus.setText("+");
                params = new LinearLayout.LayoutParams(width,height);
                plus.setLayoutParams(params);
                plus.setId(cursor.getInt(0));

                plus.setOnClickListener(new View.OnClickListener() {
                    int indice=cursor.getInt(0);
                    @Override
                    public void onClick(View v) {           //creare classe mybutton per ottenere id
                        int num = Integer.parseInt((String) ((TextView)findViewById(indice)).getText())+1;
                        ((TextView) findViewById(indice)).setText(num+"");


                        //aggiungere qui (?) l'invio delle informazioni al db (= per bottone meno)
                        db.aggiungiTogliQta(indice,num);
                    }
                });

                l4.addView(plus);

                //linea nera divisoria
                TextView line4 = new TextView(this);
                line4.setBackgroundColor(getResources().getColor(R.color.black));
                line4.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line4.setLayoutParams(params);
                l4.addView(line4);

                //bottone rimossione quantità
                Button minus = new Button(this);
                minus.setText("-");
                params = new LinearLayout.LayoutParams(width,height);
                minus.setLayoutParams(params);
                minus.setId(cursor.getInt(0));

                minus.setOnClickListener(new View.OnClickListener() {
                    int indice=cursor.getInt(0);
                    @Override
                    public void onClick(View v) {           //creare classe mybutton per ottenere id
                        int num = Integer.parseInt((String) ((TextView)findViewById(indice)).getText())-1;
                        if(num<0)
                            ((TextView) findViewById(indice)).setText("0");
                        else
                            ((TextView) findViewById(indice)).setText(num+"");
                        db.aggiungiTogliQta(indice,num);
                    }
                });

                l5.addView(minus);

                //linea nera divisoria
                TextView line5 = new TextView(this);
                line5.setBackgroundColor(getResources().getColor(R.color.black));
                line5.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line5.setLayoutParams(params);
                l5.addView(line5);

            }
        }



        //bottone aggiunta categoria
        Button addcat = findViewById(R.id.btn_addcat);
        addcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();
                Intent intent = new Intent(activity_aggiungi.this, activity_addcat.class);
                //intent.putExtra("db",db);
                startActivity(intent);
            }
        });
        //bottone rimozione categoria
        Button remcat = findViewById(R.id.btn_remcat);
        remcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.close();
                Intent intent = new Intent(activity_aggiungi.this, activity_remcat.class);
                //intent.putExtra("db",db);
                startActivity(intent);
            }
        });

    }
    public void onResume() {
        super.onResume();
    }
    public void onBackPressed(){
       /* Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}