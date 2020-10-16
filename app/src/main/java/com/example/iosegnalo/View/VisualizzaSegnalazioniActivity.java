package com.example.iosegnalo.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.iosegnalo.Presenter.VisualizzaActivityPresenter;
import com.example.iosegnalo.R;
import com.google.android.gms.maps.model.LatLng;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class VisualizzaSegnalazioniActivity extends AppCompatActivity implements VisualizzaView {
    VisualizzaActivityPresenter Presenter;
    TableLayout table;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_segnalazioni);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Presenter = new VisualizzaActivityPresenter(this);
        Presenter.creaTabella(getApplicationContext());

    }

    public void apriMappa(LatLng coordinate){

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.d("iosegnalo","lat:"+coordinate.latitude+",lon:"+coordinate.longitude);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?q=" + coordinate.latitude+","+coordinate.longitude));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void aggiungiRiga(TableRow TR)
    {
        table = (TableLayout) findViewById(R.id.tabella);
        table.addView(TR, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void mostraMessaggio(String Messaggio){
        Toast toast=Toast.makeText(getApplicationContext(),Messaggio,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
