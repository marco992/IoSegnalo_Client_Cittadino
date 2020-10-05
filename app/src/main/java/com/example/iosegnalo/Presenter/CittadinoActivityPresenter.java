package com.example.iosegnalo.Presenter;

import android.util.Log;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.View.CittadinoView;
import com.example.iosegnalo.Model.Sistema;
import com.example.iosegnalo.Model.Utente;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CittadinoActivityPresenter {
    CittadinoView View;
    Utente Cittadino;
    ControlloreNuoveSegnalazioni CS;
    private static ArrayList<Segnalazione> ListaSegnalazioni;


    public CittadinoActivityPresenter(CittadinoView view){
        View = view;
        Sistema sys = Sistema.getIstance();
        Cittadino = sys.getUtente();
        View.setID(Cittadino.getId());
        View.setUsername(Cittadino.getUsername());


        CS = new ControlloreNuoveSegnalazioni();
        ListaSegnalazioni = new ArrayList<Segnalazione>();
        ListaSegnalazioni = (ArrayList<Segnalazione>) sys.getSegnalazioniCittadino(Cittadino.getId()).clone();

        Timer timer = new Timer();
        timer.schedule( CS, 1000, 300000 );
    }
    public void clickSegnalaButton(){
        View.passaSegnalaActivity();
    }
    public void clickVisualizzaButton()
    {
        View.passaVisualizzaActivity();
    }

    public class ControlloreNuoveSegnalazioni extends TimerTask {
        public void run() {
            Sistema sys = Sistema.getIstance();
            if(sys.verificaModificaSegnalazioni()==true)
            View.mostraNotifica();
        }
    }
}
