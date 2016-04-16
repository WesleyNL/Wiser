package br.com.app.business.login;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.app.Sistema;

/**
 * Created by Wesley on 03/04/2016.
 */
public class LoginDAO extends Login{

    private static final String URL = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/LoginDAO?wsdl";
    private static final String NAMESPACE = "http://principal.projeto.com.br";

    private static final String SALVAR = "salvar";

    public boolean salvar(){

        String coordUsuario = getCoordUltimoAcesso();

        if(Sistema.USER_ID.trim().equals("") || coordUsuario.trim().equals("")){
            return false;
        }

        SoapObject objEnvio = new SoapObject(NAMESPACE, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE, "login");
        objSalvar.addProperty("userId", getUserId());
        objSalvar.addProperty("coordUltimoAcesso", coordUsuario);

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