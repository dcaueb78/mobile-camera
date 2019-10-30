package com.caue.controledegasosa.modelo;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Abastecimento extends RealmObject {

    @PrimaryKey
    private String id;
    private Float kmAtual;
    private Integer abastecido;
    private String posto;
    private Date dataPura;

    @Ignore
    private Calendar data;

    public Abastecimento() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Float getKmAtual() {
        return kmAtual;
    }

    public void setKmAtual(Float kmAtual) {
        this.kmAtual = kmAtual;
    }

    public Integer getAbastecido() {
        return abastecido;
    }

    public void setAbastecido(Integer abastecido) {
        this.abastecido = abastecido;
    }

    public Calendar getData() {
        if (dataPura != null) {
            data = Calendar.getInstance();
            data.setTime(dataPura);
        }
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
        this.dataPura = data.getTime();
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }
}
