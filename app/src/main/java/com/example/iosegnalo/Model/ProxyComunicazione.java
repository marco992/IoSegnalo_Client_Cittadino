package com.example.iosegnalo.Model;

import android.util.Log;

import java.util.ArrayList;

public class ProxyComunicazione implements Comunicazione {
    private RealComunicazione realComunicazione;
    private static ArrayList Mess;

    public ProxyComunicazione(){
        Mess = new ArrayList();
    }
    public ArrayList getRisposta(){
            if(realComunicazione!=null)
                return realComunicazione.getRisposta();
            else
                return null;
    }
    public void avviaComunicazione(ArrayList Richiesta)
    {
        if(!comparaMessaggi(Mess,Richiesta)) {
            Mess.clear();
            int i;
            //Messaggio= (ArrayList) Richiesta.clone();
            for(i=0;i<Richiesta.size();i++) {
                Mess.add(Richiesta.get(i).toString());
            }
            realComunicazione = new RealComunicazione();
            realComunicazione.avviaComunicazione(Mess);
        }
    }
    public boolean comparaMessaggi(ArrayList VecchiaRichiesta, ArrayList NuovaRichiesta)
    {
        if((NuovaRichiesta.size())!=VecchiaRichiesta.size()) {
            return false;
        }
        else
        {
            int i;
            if((Integer.parseInt(NuovaRichiesta.get(0).toString())==1) || (Integer.parseInt(NuovaRichiesta.get(0).toString())==4)){
                //backdoor per consentire al client di poter inviare piu richieste (uguali) di visualizzazione delle segnalazioni
                return false;
            }
            for (i=0;i<NuovaRichiesta.size();i++){
                if(VecchiaRichiesta.get(i).toString().compareTo(NuovaRichiesta.get(i).toString())!=0){
                   return false;
                }
            }
        }
        return true;
    }
}
