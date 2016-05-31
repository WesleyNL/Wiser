package br.com.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import br.com.app.activity.R;
import br.com.app.activity.app.configuracoes.AppConfiguracoesActivity;
import br.com.app.activity.app.principal.AppPrincipalActivity;
import br.com.app.activity.app.splashscreen.AppSplashScreenActivity;
import br.com.app.activity.chat.mensagens.ChatMensagensFragment;
import br.com.app.activity.chat.resultados.ChatResultadosActivity;
import br.com.app.activity.app.login.AppLoginActivity;
import br.com.app.activity.chat.pesquisa.ChatPesquisaFragment;
import br.com.app.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.app.activity.forum.minhas_discussoes.ForumMinhasDiscussoesActivity;
import br.com.app.activity.forum.nova_discussao.ForumNovaDiscussaoActivity;
import br.com.app.activity.forum.pesquisa.ForumPesquisaActivity;
import br.com.app.activity.forum.principal.ForumPrincipalFragment;
import br.com.app.business.app.servidor.Servidor;
import br.com.app.activity.app.sobre.AppSobreActivity;
import br.com.app.enums.EnmTelas;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        //ArrayAdapter<IdiomaFluencia> dataAdapter = new ArrayAdapter<IdiomaFluencia>(context, android.R.layout.simple_spinner_item, listaIdiomas);
        ArrayAdapter<IdiomaFluencia> dataAdapter = new ArrayAdapter<IdiomaFluencia>(context, R.layout.app_spinner_item, listaIdiomas);

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
        ArrayAdapter<IdiomaFluencia> dataAdapter = new ArrayAdapter<IdiomaFluencia>(context, R.layout.app_spinner_item, listaFluencia);

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
                case APP_CONFIGURACOES:
                    classe = AppConfiguracoesActivity.class;
                    break;
                case APP_LOGIN:
                    classe = AppLoginActivity.class;
                    break;
                case APP_PRINCIPAL:
                    classe = AppPrincipalActivity.class;
                    break;
                case APP_SOBRE:
                    classe = AppSobreActivity.class;
                    break;
                case APP_SPLASHSCREEN:
                    classe = AppSplashScreenActivity.class;
                    break;

                case CHAT_MENSAGENS:
                    classe = ChatMensagensFragment.class;
                    break;
                case CHAT_PESQUISA:
                    classe = ChatPesquisaFragment.class;
                    break;
                case CHAT_RESULTADOS:
                    classe = ChatResultadosActivity.class;
                    break;
                case FORUM_DISCUSSAO:
                    classe = ForumDiscussaoActivity.class;
                    break;
                case FORUM_MINHAS_DISCUSSOES:
                    classe = ForumMinhasDiscussoesActivity.class;
                    break;
                case FORUM_NOVA_DISCUSSAO:
                    classe = ForumNovaDiscussaoActivity.class;
                    break;
                case FORUM_PESQUISA:
                    classe = ForumPesquisaActivity.class;
                    break;
                case FORUM_PRINCIPAL:
                    classe = ForumPrincipalFragment.class;
                    break;
            }

            i.setClass(activity, classe);

            if (enmActivity == EnmTelas.APP_LOGIN) {
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            if (activity instanceof AppLoginActivity) {
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            if (!extras.equals("")) {
                i.putExtra(extras, valExtras);
            }

            activity.startActivity(i);

            if (enmActivity == EnmTelas.APP_LOGIN) {
                activity.finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void carregarImagem(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load(url).into(imageView);
        }
    }

    public static String formatDate(Date data, String format) {
        return new SimpleDateFormat(format).format(data);
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

    public static byte getCodAppIdioma(String appIdioma){
        switch(appIdioma.toLowerCase()){
            case "pt-br":
                return 1;
            case "en":
                return 2;
            default:
                return 1;
        }
    }

    public static boolean soNumeros(String texto){

        if(texto == null || texto.isEmpty()) {
            return false;
        }

        for (char letra : texto.toCharArray()) {
            if (letra < '0' || letra > '9') {
                return false;
            }
        }

        return true;
    }

    public static void compartilharEmImagem(View view){

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap img = view.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(), img, view.getContext().getString(R.string.compartilhar_titulo), null);

        Intent iCompartilhar = new Intent(Intent.ACTION_SEND);
        Uri imgDiscussao = Uri.parse(path);

        iCompartilhar.setType("image/png");
        iCompartilhar.putExtra(Intent.EXTRA_STREAM, imgDiscussao);
        view.getContext().startActivity(Intent.createChooser(iCompartilhar, view.getContext().getString(R.string.compartilhar_discussao_sistema)));
    }

    public static void compartilharAplicativoEmTexto(View view){

        Intent iCompartilhar = new Intent(Intent.ACTION_SEND);
        iCompartilhar.setType("text/plain");
        iCompartilhar.putExtra(android.content.Intent.EXTRA_TEXT, view.getContext().getString(R.string.sistema_link_playstore));
        view.getContext().startActivity(Intent.createChooser(iCompartilhar, view.getContext().getString(R.string.compartilhar_aplicativo_sistema)));
    }
}