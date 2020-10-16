package com.example.iosegnalo.Model;

import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;

public class Archivio {
    private static Archivio istance=null;
    ArrayList<Segnalazione> ListaSegnalazioni = new ArrayList<Segnalazione>();
    Utente Cittadino;
    private static Comunicazione com;

    public static Archivio getIstance() {
        if(istance==null)
            istance = new Archivio();
        return istance;
    }

    public Archivio()
    {
        ListaSegnalazioni=new ArrayList<Segnalazione>();
        com = new ProxyComunicazione();
    }

    public void updateListaSegnalazioni(int IDCittadino)
    {
        ListaSegnalazioni= (ArrayList<Segnalazione>) getSegnalazioniCittadino(IDCittadino).clone();
    }

    public int inserisciSegnalazione(int Tipologia, String Descrizione, int IDUtente, Double latitudine, Double Longitudine, String Recapito)
    {
        Segnalazione s = new Segnalazione();
        //inserisci prima tutto in segnalazione
        s.setTipologia(Tipologia);
        s.setDescrizione(Descrizione);
        s.setIDcittadino(IDUtente);
        s.setLatitudine(latitudine);
        s.setLongitudine(Longitudine);
        s.setRecapito(Recapito);
        //inserisci poi tutto in un messaggio
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(2);
        richiesta.add(s.getTipologia());
        richiesta.add(s.getDescrizione());
        richiesta.add(s.getIDcittadino());
        richiesta.add(s.getLatitudine());
        richiesta.add(s.getLongitudine());
        richiesta.add(s.getRecapito());
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        if(risposta.get(0).toString().equals("1"))
            return 1;
        else return -1;
    }

    public Utente getUtenteByUsrPass(String Username, String Password)
    {
            Cittadino = new Utente();
            ArrayList risposta = new ArrayList();
            ArrayList richiesta = new ArrayList();
            richiesta.add(0);
            richiesta.add(Username);
            richiesta.add(Password);
            com.avviaComunicazione(richiesta);
            risposta = com.getRisposta();
            Cittadino.setUsername(Username);
            Cittadino.setPassword(Password);
            Cittadino.setTipo(Integer.parseInt(risposta.get(0).toString()));
            Cittadino.setId(Integer.parseInt(risposta.get(1).toString()));
        return Cittadino;
    }

    public ArrayList<Segnalazione> getSegnalazioniCittadino(int IDUtente)
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(1);
        richiesta.add(IDUtente);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();
        ArrayList<Segnalazione> ListaSegnalazioniTemp=new ArrayList<Segnalazione>();
        int i;
        for(i=0;i<risposta.size();i=i+6) {
            Segnalazione s = new Segnalazione();
            s.setId(Integer.parseInt(risposta.get(i).toString()));
            s.setDescrizione(risposta.get(i+1).toString());
            s.setDataModifica(Date.valueOf(risposta.get(i+4).toString()));
            //s.setIDcittadino();
            s.setLatitudine(Double.parseDouble(risposta.get(i+2).toString()));
            s.setLongitudine(Double.parseDouble(risposta.get(i+3).toString()));
            //s.getNota();
            //s.getRecapito();
            s.setStato(Integer.parseInt(risposta.get(i+5).toString()));
            ListaSegnalazioniTemp.add(s);
        }
        return ListaSegnalazioniTemp;
    }

    public ArrayList<Segnalazione> getSegnalazioni()
    {
        ArrayList risposta = new ArrayList();
        ArrayList richiesta = new ArrayList();
        richiesta.add(3);
        com.avviaComunicazione(richiesta);
        risposta = com.getRisposta();

        int i;
        for(i=0;i<risposta.size();i=i+6) {
            Segnalazione s = new Segnalazione();
            s.setId(Integer.parseInt(risposta.get(i).toString()));
            s.setDescrizione(risposta.get(i+1).toString());
            s.setDataModifica(Date.valueOf(risposta.get(i+4).toString()));
            //s.setIDcittadino();
            s.setLatitudine(Double.parseDouble(risposta.get(i+2).toString()));
            s.setLongitudine(Double.parseDouble(risposta.get(i+3).toString()));
            //s.getNota();
            //s.getRecapito();
            s.setStato(Integer.parseInt(risposta.get(i+5).toString()));
            ListaSegnalazioni.add(s);
        }
        return ListaSegnalazioni;
    }

    public Utente getUtente(){
        return Cittadino;
    }

    public boolean verificaModificaSegnalazioni()
    {
        ArrayList<Segnalazione> NuovaListaSegnalazioni = new ArrayList<Segnalazione>();
        Archivio sys = Archivio.getIstance();
        NuovaListaSegnalazioni.clear();
        NuovaListaSegnalazioni = (ArrayList<Segnalazione>) sys.getSegnalazioniCittadino(Cittadino.getId()).clone();
        int i;
        if(NuovaListaSegnalazioni.size()==ListaSegnalazioni.size()) {
            for (i = 0; i < NuovaListaSegnalazioni.size(); i++) {
                Log.d("myapp", "Dim1: " + NuovaListaSegnalazioni.get(i).getDataModifica().toString() + "Dim2: " + ListaSegnalazioni.get(i).getDataModifica().toString());

                if (NuovaListaSegnalazioni.get(i).getDataModifica().compareTo(ListaSegnalazioni.get(i).getDataModifica()) != 0) {
                    ListaSegnalazioni = (ArrayList<Segnalazione>) NuovaListaSegnalazioni.clone();
                    return true;
                }
            }
        }
        
        else
            ListaSegnalazioni = (ArrayList<Segnalazione>) NuovaListaSegnalazioni.clone();
        return false;
    }

}