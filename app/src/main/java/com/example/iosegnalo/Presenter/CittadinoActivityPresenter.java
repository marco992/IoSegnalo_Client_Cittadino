package com.example.iosegnalo.Presenter;

import com.example.iosegnalo.Model.Segnalazione;
import com.example.iosegnalo.View.CittadinoView;
import com.example.iosegnalo.Model.Archivio;
import com.example.iosegnalo.Model.Utente;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CittadinoActivityPresenter {
    CittadinoView View;
    Utente Cittadino;
    ControllerNuoveSegnalazioni CS;

    public CittadinoActivityPresenter(CittadinoView view){
        View = view;
        Archivio sys = Archivio.getIstance();
        Cittadino = sys.getUtente();
        View.setID(Cittadino.getId());
        View.setUsername(Cittadino.getUsername());


        CS = new ControllerNuoveSegnalazioni();
        sys.updateListaSegnalazioni(Cittadino.getId());


        Timer timer = new Timer();
        timer.schedule( CS, 1000, 10000 );
    }
    public void clickSegnalaButton(){
        View.passaSegnalaActivity();
    }
    public void clickVisualizzaButton()
    {
        View.passaVisualizzaActivity();
    }

    public class ControllerNuoveSegnalazioni extends TimerTask {
        public void run() {
            Archivio sys = Archivio.getIstance();
            if(sys.verificaModificaSegnalazioni()==true)
            View.mostraNotifica();
        }
    }
}
