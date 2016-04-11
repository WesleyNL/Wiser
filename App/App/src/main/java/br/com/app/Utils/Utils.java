package br.com.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedList;
import java.util.List;

import br.com.app.Sistema;
import br.com.app.activity.configuracao.ConfiguracoesActivity;
import br.com.app.activity.contatos.ContatosActivity;
import br.com.app.activity.login.LoginActivity;
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
//        objPesquisar.addProperty("", "");

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objPesquisar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + UTILS_PESQ_IDIOMAS, objEnvelope);
//            String str = ((SoapFault) objEnvelope.bodyIn).faultstring;
//            System.out.println(str);

            SoapObject objResposta = (SoapObject) objEnvelope.bodyIn;

            listaIdiomas = new LinkedList<String>();

            for(int i=0; i<objResposta.getPropertyCount(); i++){
                listaIdiomas.add(objResposta.getPropertyAsString(i));
            }
        } catch(Exception e){
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
//        objPesquisar.addProperty("", "");

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
     * @return Latitude-Longitude
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

    public static void chamarActivity(Context context, Activity tela, EnmTelas Activity, String extras, boolean valExtras) {

        Intent i = new Intent();
        Class classe = null;
        int flag = 0;

        switch (Activity) {
            case CONFIGURACOES:
                classe = ConfiguracoesActivity.class;
                break;
            case CONTATOS:
                classe = ContatosActivity.class;
                break;
            case LOGIN:
                classe = LoginActivity.class;
                flag = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                break;
            case SOBRE:
                classe = SobreActivity.class;
                break;
        }

        i.setClass(context, classe);

        if (flag != 0) {
            i.setFlags(flag);
        }

        if (!extras.equals("")) {
            i.putExtra(extras, valExtras);
        }

        tela.startActivity(i);

        if (flag == Intent.FLAG_ACTIVITY_CLEAR_TOP) {
            tela.finish();
        }
    }
}
