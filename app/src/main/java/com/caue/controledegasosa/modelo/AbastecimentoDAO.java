package com.caue.controledegasosa.modelo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class AbastecimentoDAO {

    private ArrayList<Abastecimento> bancoDeDados;

    public ArrayList<Abastecimento> obterLista(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults lista = realm.where(Abastecimento.class).findAll().sort("kmAtual", Sort.DESCENDING);
        bancoDeDados.clear();
        bancoDeDados.addAll(realm.copyFromRealm(lista));
        return bancoDeDados;
    }

    public void adicionarNaLista(Abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(a);
        realm.commitTransaction();
    }

    public int atualizaNaLista(Abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(a);
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(a.getId())){
                bancoDeDados.set(i, a);
                return i;
            }
        }
        return -1;
    }

    public int excluiDaLista(Abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Abastecimento.class).equalTo("id", a.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(a.getId())){
                bancoDeDados.remove(i);
                return i;
            }
        }
        return -1;
    }

    public Abastecimento obterAbastecimentoPeloId(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Abastecimento a = realm.copyFromRealm(realm.where(Abastecimento.class).equalTo("id", id).findFirst());
        realm.commitTransaction();
        return a;
    }

    private static AbastecimentoDAO INSTANCIA;

    public static AbastecimentoDAO obterInstancia(){
        if (INSTANCIA == null){
            INSTANCIA = new AbastecimentoDAO();
        }
        return INSTANCIA;
    }

    private AbastecimentoDAO(){
        bancoDeDados = new ArrayList<Abastecimento>();
    }

}
