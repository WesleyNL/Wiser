package br.com.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.configuracao.ConfiguracoesActivity;
import br.com.app.activity.contatos.ContatosActivity;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.activity.servidor.Servidor;
import br.com.app.activity.sobre.SobreActivity;
import br.com.app.enums.EnmTelas;

/**
 * Created by Wesley on 03/04/2016.
 */
public class Utils implements LocationListener {

    public static LinkedHashMap<Integer, String> hashIdiomas = new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, String> hashFluencias  = new LinkedHashMap<Integer, String>();;

    public static Location locationPorListener = null;

    public static LinkedList<IdiomaFluencia> pesquisarIdiomas(boolean todos){
        return Servidor.pesquisarIdiomas(todos, hashIdiomas);
    }

    public static void carregarComboIdiomas(Spinner cmbIdioma, Context context){
        carregarComboIdiomas(cmbIdioma, context, false);
    }

    public static void carregarComboIdiomas(Spinner cmbIdioma, Context context, boolean todos) {

        LinkedList<IdiomaFluencia> listaIdiomas = pesquisarIdiomas(todos);
        ArrayAdapter<IdiomaFluencia> dataAdapter = new ArrayAdapter<IdiomaFluencia>(context, android.R.layout.simple_spinner_item, listaIdiomas);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbIdioma.setAdapter(dataAdapter);
    }

    public static LinkedList<IdiomaFluencia> pesquisarFluencias(boolean todos){
        return Servidor.pesquisarFluencias(todos, hashFluencias);
    }

    public static void carregarComboFluencia(Spinner cmbFluencia, Context context){
        carregarComboFluencia(cmbFluencia, context, false);
    }

    public static void carregarComboFluencia(Spinner cmbFluencia, Context context, boolean todos) {

        LinkedList<IdiomaFluencia> listaFluencia = pesquisarFluencias(todos);
        ArrayAdapter<IdiomaFluencia> dataAdapter = new ArrayAdapter<IdiomaFluencia>(context, android.R.layout.simple_spinner_item, listaFluencia);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbFluencia.setAdapter(dataAdapter);
    }

    /**
     * @return Latitude|Longitude
     */
    public static String getCoordenadas(Context context) {

        int status = context.getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, context.getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {

            LocationManager locMng = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = locMng.getAllProviders();

            if (providers != null && providers.contains(LocationManager.NETWORK_PROVIDER) || providers.contains(LocationManager.PASSIVE_PROVIDER) || providers.contains(LocationManager.GPS_PROVIDER)) {

                if(locationPorListener != null) {
                   return locationPorListener.getLatitude() + "|" + locationPorListener.getLongitude();
                }
                else {
                    Location loc = locMng.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (loc == null) {
                        loc = locMng.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    }

                    if (loc == null) {
                        loc = locMng.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    if (loc != null) {
                        return loc.getLatitude() + "|" + loc.getLongitude();
                    }
                }
            }
        }

        return "-23.5559324|-46.8090874";
    }

    public static void chamarActivity(Activity activity, EnmTelas enmActivity) {
        chamarActivity(activity, enmActivity, "", false);
    }

    public static void chamarActivity(Activity activity, EnmTelas enmActivity, String extras, boolean valExtras) {
        Intent i = new Intent();
        Class classe = null;

        try {
            switch (enmActivity) {
                case CONFIGURACOES:
                    classe = ConfiguracoesActivity.class;
                    break;
                case CONTATOS:
                    classe = ContatosActivity.class;
                    break;
                case LOGIN:
                    classe = LoginActivity.class;
                    break;
                case PESQUISA:
                    classe = PesquisaActivity.class;
                    break;
                case SOBRE:
                    classe = SobreActivity.class;
                    break;
            }

            i.setClass(activity, classe);

            if (enmActivity == EnmTelas.LOGIN) {
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            if (activity instanceof LoginActivity) {
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            if (!extras.equals("")) {
                i.putExtra(extras, valExtras);
            }

            activity.startActivity(i);

            if (enmActivity == EnmTelas.LOGIN || enmActivity == EnmTelas.PESQUISA) {
                activity.finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap carregarImagem(String url) {
        Bitmap imagem = null;

        if (!url.equals("")) {
            try {
                URL imgValue = new URL(url);
                imagem = BitmapFactory.decodeStream(imgValue.openConnection().getInputStream());
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return imagem;
    }

    public static int getPosicaoIdioma(int idioma){
        int i;
        Iterator<Integer> itIdioma = Utils.hashIdiomas.keySet().iterator();
        for(i=0; itIdioma.hasNext() && itIdioma.next() != idioma; i++);
        return i;
    }

    public static int getPosicaoFluencia(int fluencia){
        int i;
        Iterator<Integer> itFluencia = Utils.hashFluencias.keySet().iterator();
        for(i=0; itFluencia.hasNext() && itFluencia.next() != fluencia; i++);
        return i;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.locationPorListener = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
}