package classes;

import java.util.Date;

public class Books {
    private String name;
    private String name_ehdakonande;
    private String writer;
    private long mohlat;
    private String date;
    private int id;
    private String vaziyat;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_ehdakonande() {
        return name_ehdakonande;
    }

    public void setName_ehdakonande(String name_getter) {
        this.name_ehdakonande = name_getter;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public long getMohlat() {
        return mohlat;
    }

    public void setMohlat(long mohlat) {
        this.mohlat = mohlat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVaziyat() {
        return vaziyat;
    }

    public void setVaziyat(String vaziyat) {
        this.vaziyat = vaziyat;
    }
}
