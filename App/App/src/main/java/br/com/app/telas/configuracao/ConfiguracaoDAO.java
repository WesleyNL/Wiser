package br.com.app.telas.configuracao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.app.App;

public class ConfiguracaoDAO extends Configuracao{

    private static final String URL = "http://" + App.SERVIDOR_WS + "/Projeto_Android_WS/services/ConfiguracaoDAO?wsdl";
    private static final String NAMESPACE = "http://configuracoes.projeto.com.br";

    private static final String SALVAR = "salvar";
    private static final String CONSULTAR = "consultar";
    private static final String DESATIVAR = "desativar";

    public boolean consultar(){

        SoapObject objConsultar = new SoapObject(NAMESPACE, CONSULTAR);
        objConsultar.addProperty("userId", getUserId());

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objConsultar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + CONSULTAR, objEnvelope);

            SoapObject objResposta = (SoapObject) objEnvelope.getResponse();

            setUserId(objResposta.getProperty("userId").toString());
            setIdioma(Byte.parseByte(objResposta.getProperty("idioma").toString()));
            setFluencia(Byte.parseByte(objResposta.getProperty("fluencia").toString()));
            setStatus(objResposta.getProperty("status").toString());

        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean salvar(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE, "configuracao");
        objSalvar.addProperty("userId", getUserId());
        objSalvar.addProperty("idioma", String.valueOf(getIdioma()));
        objSalvar.addProperty("fluencia", String.valueOf(getFluencia()));
        objSalvar.addProperty("status", getStatus());

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

    public boolean desativar(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, DESATIVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE, "configuracao");
        objSalvar.addProperty("userId", getUserId());

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + DESATIVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}