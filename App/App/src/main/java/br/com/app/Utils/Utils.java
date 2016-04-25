package br.com.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import br.com.app.Sistema;
import br.com.app.activity.configuracao.ConfiguracoesActivity;
import br.com.app.activity.contatos.ContatosActivity;
import br.com.app.activity.login.LoginActivity;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.activity.sobre.SobreActivity;
import br.com.app.enums.EnmTelas;

/**
 * Created by Wesley on 03/04/2016.
 */
public class Utils {

    private static final String URL = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/Utils?wsdl";
    private static final String NAMESPACE = "http://utils.projeto.com.br";

    private static final String UTILS_PESQ_IDIOMAS = "pesquisarIdiomas";
    private static final String UTILS_PESQ_FLUENCIAS = "pesquisarFluencias";

    public static LinkedList<String> pesquisarIdiomas(){

        LinkedList<String> listaIdiomas = null;
        SoapObject objPesquisar = new SoapObject(NAMESPACE, UTILS_PESQ_IDIOMAS);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objPesquisar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + UTILS_PESQ_IDIOMAS, objEnvelope);
            SoapObject objResposta = (SoapObject) objEnvelope.bodyIn;

            listaIdiomas = new LinkedList<String>();

            for(int i=0; i<objResposta.getPropertyCount(); i++){
                listaIdiomas.add(objResposta.getPropertyAsString(i));
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return listaIdiomas;
    }

    public static void carregarComboIdiomas(Spinner cmbIdioma, Context context) {

        LinkedList<String> listaIdiomas = pesquisarIdiomas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listaIdiomas);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbIdioma.setAdapter(dataAdapter);
    }

    public static LinkedList<String> pesquisarFluencias(){

        LinkedList<String> listaFluencias = null;
        SoapObject objPesquisar = new SoapObject(NAMESPACE, UTILS_PESQ_FLUENCIAS);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objPesquisar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + UTILS_PESQ_FLUENCIAS, objEnvelope);

            SoapObject objResposta = (SoapObject) objEnvelope.bodyIn;

            listaFluencias = new LinkedList<String>();

            for(int i=0; i<objResposta.getPropertyCount(); i++){
                listaFluencias.add(objResposta.getPropertyAsString(i));
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return listaFluencias;
    }

    public static void carregarComboFluencia(Spinner cmbFluencia, Context context) {

        LinkedList<String> listaFluencia = pesquisarFluencias();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listaFluencia);

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

                Location loc = locMng.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(loc == null) {
                    loc = locMng.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }

                if(loc == null){
                    loc = locMng.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                if (loc != null) {
                    return loc.getLatitude() + "|" + loc.getLongitude();
                }
            }
        }

        return "";
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
}
