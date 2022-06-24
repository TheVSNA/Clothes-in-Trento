package com.example.cosahoatrento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_remcat extends AppCompatActivity {
    LinearLayout root, l1,l2,l3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remcat);

        root = findViewById(R.id.rootLayoutrem);
        l1 = findViewById(R.id.vertical1rem);
        l2 = findViewById(R.id.vertical2rem);
        l3 = findViewById(R.id.vertical3rem);

        ArrayList<CheckBox> checks = new ArrayList<CheckBox>();
        DBHelper db = new DBHelper(this);
        Cursor cursor = db.getElenco();
        if(cursor.getCount()==0){
            //no vestiti trovati
            TextView text = new TextView(this);
            text.setText("Nessun vestito trovato");
            root.addView(text);
        }else{
            //mostro elenco vestiti DA PROVARE E SISTEMARE
            int width= LinearLayout.LayoutParams.MATCH_PARENT;
            int height = 150;
            LinearLayout rowlayout = new LinearLayout(this);
            rowlayout.setOrientation(LinearLayout.HORIZONTAL);
            while(cursor.moveToNext()) {
                //textview indice
                TextView t1 = new TextView(this);
                t1.setText((cursor.getInt(0)+1)+"");
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


                //checkbox rimozione
                CheckBox check = new CheckBox(getApplicationContext());
                check.setId(cursor.getInt(0));
                params = new LinearLayout.LayoutParams(width,height);
                check.setLayoutParams(params);
                l3.addView(check);

                checks.add(check);

                TextView line3 = new TextView(this);
                line3.setBackgroundColor(getResources().getColor(R.color.black));
                line3.setTextSize(1);
                params = new LinearLayout.LayoutParams(width,5);
                line3.setLayoutParams(params);
                l3.addView(line3);
            }
        }


        Button remcat = findViewById(R.id.btn_confinv);
        remcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=db.getElenco().getCount();
                boolean res=true;
                for(int i=0;i<n;i++){
                    CheckBox check = checks.get(i);
                    if(check.isChecked()){
                        //rimozione della categoria
                        if(!db.rimuoviCategoria(check.getId())){
                            res=false;
                        }else{
                            l1.removeViewAt(2*i);
                            l1.removeViewAt(2*i);

                            l2.removeViewAt(2*i);
                            l2.removeViewAt(2*i);

                            l3.removeViewAt(2*i);
                            l3.removeViewAt(2*i);
                        }
                    }
                }
                if(res)
                    Toast.makeText(activity_remcat.this ,"Eliminazione avvenuta con successo", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(activity_remcat.this ,"Errore nell'eliminazione di una o più categorie di vestiti", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity_remcat.this,activity_aggiungi.class);
                startActivity(intent);
            }
        });
    }
    /*public void onRestart(){
        super.onRestart();
        Intent intent = new Intent(activity_remcat.this, getCallingActivity().getClass());
        startActivity(intent);
    }*/
}