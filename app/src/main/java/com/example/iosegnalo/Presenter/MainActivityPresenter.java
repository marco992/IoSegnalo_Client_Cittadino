package com.example.iosegnalo.Presenter;

import android.content.Intent;

import com.example.iosegnalo.View.MainView;
import com.example.iosegnalo.Model.Archivio;
import com.example.iosegnalo.Model.Utente;

public class MainActivityPresenter {
    MainView Main;
    Utente Cittadino;
    static final int TipoClient = 0; //definisce il tipo di Client, 0=cittadino, 1=resp.tecnici 2=amm.sistema

    public MainActivityPresenter(MainView main){
        Main=main;
    }

    public void validaCredenziali(String Username, String Password){
        Archivio sys = Archivio.getIstance();
        Cittadino = sys.getUtenteByUsrPass(Username,Password);
        Intent i;
        if(Cittadino.getTipo()!=0)
            Main.mostraErrore();
        else {
            Main.passaCittadinoActivity(Cittadino.getUsername(), Cittadino.getId());
        }
    }
}
