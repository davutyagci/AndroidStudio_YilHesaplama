package com.theghost.calculator;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AgeCalculator extends AppCompatActivity {
    private TextView BugununTarihiSonuc, SecilecekTarihSonuc, GecenZamanSonuc, KalanZamanSonuc, ToplamAySonuc, ToplamGunSonuc, ToplamSaatSonuc;
    private int bugununyılı, bugununayı, bugunungunu;
    private int seçilecekyıl, seçilecekay, seçilecekgün;
    private int yılsonuc, aysonuc, gunsonuc;
    private Calendar BugununTarihiCal;
    private Calendar SeçilecekTarihCal;
    private long gunhesap;

    public void BugununTarihiniHesapla() {
        BugununTarihiCal = Calendar.getInstance();
        bugununyılı = BugununTarihiCal.get(Calendar.YEAR);
        bugununayı = BugununTarihiCal.get(Calendar.MONTH)+1;
        bugunungunu = BugununTarihiCal.get(Calendar.DAY_OF_MONTH);
        BugununTarihiCal.set(Calendar.MILLISECOND,0);
        BugununTarihiSonuc.setText(bugunungunu + "/" + bugununayı + "/" + bugununyılı); }

    public  void SecilecekTarihiHesaplaClick(View v) {
        SeçilecekTarihCal = Calendar.getInstance();
        seçilecekyıl = SeçilecekTarihCal.get(Calendar.YEAR);
        seçilecekay = SeçilecekTarihCal.get(Calendar.MONTH);
        seçilecekgün = SeçilecekTarihCal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog DatePicker = new DatePickerDialog(AgeCalculator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int selectyear, int selectmonth, int selectday) {
                selectmonth++;
                seçilecekyıl = selectyear;
                seçilecekay  = selectmonth;
                seçilecekgün = selectday;
                SecilecekTarihSonuc.setText(selectday + "/" + selectmonth + "/" + selectyear); } }, seçilecekyıl, seçilecekay, seçilecekgün);
        DatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DatePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "TAMAM", DatePicker);
        DatePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İPTAL", DatePicker);
        DatePicker.show();
    }

    public void GecenZamanYılıHesaplama()
    {yılsonuc = bugununyılı - seçilecekyıl;}

    public void GecenZamanAyıHesaplama() {
        if (bugununayı>=seçilecekay) {
            aysonuc = bugununayı - seçilecekay; }
        else {
            aysonuc = bugununayı - seçilecekay;
            aysonuc = 12 + aysonuc;
            yılsonuc--; }
    }

    public void GecenZamanGunuHesaplama() {
        if (bugunungunu>=seçilecekgün) {
            gunsonuc = bugunungunu - seçilecekgün; }
        else {
            gunsonuc = bugunungunu - seçilecekgün;
            if (bugununayı==1 || bugununayı==3 || bugununayı==5 || bugununayı==7 || bugununayı==8 || bugununayı==10 || bugununayı==12 )
            {gunsonuc = 31 + gunsonuc;}
            else if (bugununayı==2) {
                if (bugununyılı%4==0) {gunsonuc = 29 + gunsonuc;}
                else {gunsonuc = 28 + gunsonuc;} }
            else {gunsonuc = 30 + gunsonuc;}
            if(aysonuc==0) {
                aysonuc=11;
                yılsonuc--; }
            else {aysonuc--; }
        }
    }

    public void KalanZamanSonuc() {
        int kalanay, kalangun, aykontrol;
        if(BugununTarihiSonuc.getText().toString().equals(SecilecekTarihSonuc.getText())) {kalanay=0;  kalangun=0;}
        else {
            kalanay = 11-aysonuc;
            aykontrol = seçilecekay-1;
            if(aykontrol==1 || aykontrol==3 || aykontrol==5 || aykontrol==7 || aykontrol==8 || aykontrol==10 || aykontrol==12) {kalangun=31-gunsonuc;}
            else if(aykontrol==2){if(bugununayı%4==0) {kalangun=29-gunsonuc;} else {kalangun=28-gunsonuc;}}
            else {kalangun=30-gunsonuc;}
        }
        KalanZamanSonuc.setText(kalanay + " AY" + " - "+ kalangun + " GÜN");
    }

    public void ToplamAy()
    { ToplamAySonuc.setText((12*yılsonuc)+aysonuc + " AY" ); }

    public void ToplamGun()
    {
        BugununTarihiCal.set(bugununyılı,bugununayı,bugunungunu);
        SeçilecekTarihCal.set(seçilecekyıl,seçilecekay,seçilecekgün);
        long bugununtarihimilis = BugununTarihiCal.getTimeInMillis();
        long dogumtarihimilis = SeçilecekTarihCal.getTimeInMillis();
        long fark = bugununtarihimilis - dogumtarihimilis;
        if(bugunungunu==seçilecekgün) {gunhesap = fark/(24*60*60*1000);}
        else {gunhesap = fark/(24*60*60*1000)+1;}
        ToplamGunSonuc.setText(gunhesap + " GÜN");

    }

    public void ToplamSaat() {ToplamSaatSonuc.setText(gunhesap*24 + " SAAT");}

    public void BtnHesaplaClick(View v){
        if(SecilecekTarihSonuc.getText().toString().trim().equals(""))
        {
            Toast.makeText(AgeCalculator.this, "SEÇİLECEK TARİHİ GİRİNİZ!", Toast.LENGTH_LONG).show();}
        else {
            if (seçilecekyıl>bugununyılı){
                Toast.makeText(AgeCalculator.this, "Seçilen Yılı "+bugununyılı+"'den(dan) Büyük Olamaz!", Toast.LENGTH_LONG).show(); }
            else if (seçilecekyıl>=bugununyılı && seçilecekay>bugununayı) {
                Toast.makeText(AgeCalculator.this, "Seçilen Ayı "+bugununayı+"'den(dan) Büyük Olamaz!", Toast.LENGTH_LONG).show(); }
            else if (seçilecekyıl>=bugununyılı && seçilecekay>=bugununayı && seçilecekgün>bugunungunu) {
                Toast.makeText(AgeCalculator.this, "Seçilen Günü "+bugunungunu+"'den(dan) Büyük Olamaz!", Toast.LENGTH_LONG).show(); }
            else {
                GecenZamanYılıHesaplama();
                GecenZamanAyıHesaplama();
                GecenZamanGunuHesaplama();
                GecenZamanSonuc.setText(yılsonuc+" YIL" + " - " + aysonuc+" AY" + " - " + gunsonuc+" GÜN");
                KalanZamanSonuc();
                ToplamAy();
                ToplamGun();
                ToplamSaat();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        BugununTarihiSonuc = (TextView) findViewById(R.id.BugununTarihiSonuc);
        SecilecekTarihSonuc = (TextView) findViewById(R.id.SecilecekTarihSonuc);
        GecenZamanSonuc = (TextView)findViewById(R.id.GecenZamanSonuc);
        KalanZamanSonuc = (TextView)findViewById(R.id.KalanZamanSonuc);
        ToplamAySonuc = (TextView)findViewById(R.id.ToplamAySonuc);
        ToplamGunSonuc = (TextView)findViewById(R.id.ToplamGunSonuc);
        ToplamSaatSonuc = (TextView)findViewById(R.id.ToplamSaatSonuc);

        BugununTarihiniHesapla();
    }
}
