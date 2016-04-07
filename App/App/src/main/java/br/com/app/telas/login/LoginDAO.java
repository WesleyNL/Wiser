package br.com.app.telas.login;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.app.Utils.App;
import br.com.app.Utils.Utils;

/**
 * Created by Wesley on 03/04/2016.
 */
public class LoginDAO extends Login{

    private static final String URL = "http://" + App.SERVIDOR_WS + "/Projeto_Android_WS/services/LoginDAO?wsdl";
    private static final String NAMESPACE = "http://principal.projeto.com.br";

    private static final String SALVAR = "salvar";

    public boolean salvar(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE, "login");
        objSalvar.addProperty("userId", getUserId());
        objSalvar.addProperty("dataUltimoAcesso", getDataUltimoAcesso());
        objSalvar.addProperty("coordUltimoAcesso", getCoordUltimoAcesso());;

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + SALVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}