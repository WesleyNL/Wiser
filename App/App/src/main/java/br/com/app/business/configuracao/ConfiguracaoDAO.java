package br.com.app.business.configuracao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import br.com.app.Sistema;

public class ConfiguracaoDAO extends Configuracao{

    private static final String URL = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/ConfiguracaoDAO?wsdl";
    private static final String NAMESPACE = "http://configuracoes.projeto.com.br";

    private static final String SALVAR = "salvar";
    private static final String CONSULTAR = "consultar";
    private static final String DESATIVAR = "desativar";
    private static final String VERIFICAR = "existe";

    public boolean consultar(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, CONSULTAR);

        SoapObject objConsultar = new SoapObject(NAMESPACE, "configuracao");
        objConsultar.addProperty("userId", getUserId());

        objEnvio.addSoapObject(objConsultar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
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

    public boolean existe(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, VERIFICAR);

        SoapObject objVerificar = new SoapObject(NAMESPACE, "configuracao");
        objVerificar.addProperty("userId", getUserId());

        objEnvio.addSoapObject(objVerificar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + VERIFICAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}