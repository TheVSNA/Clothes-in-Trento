package com.example.cosahoatrento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_addcat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcat);

        //DBHelper db = (DBHelper) getIntent().getSerializableExtra("MyClass");
        DBHelper db = new DBHelper(this);
        TextView catname= findViewById(R.id.view_catname);

        Button invia = findViewById(R.id.btn_invia);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = catname.getText().toString();
                boolean ret = db.addCategoriaVestiti(nome,0);
                if(ret==true)
                    Toast.makeText(activity_addcat.this ,"Inserimento avvenuto con successo", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(activity_addcat.this ,"Errore nell'inserimento della nuova categoria", Toast.LENGTH_LONG).show();
                db.close();
                Intent intent= new Intent(activity_addcat.this, activity_aggiungi.class);
                startActivity(intent);
            }
        });
    }
    public void onResume() {
        super.onResume();
    }
}