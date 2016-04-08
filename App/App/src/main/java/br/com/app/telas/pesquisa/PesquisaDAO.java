package br.com.app.telas.pesquisa;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

import br.com.app.App;

/**
 * Created by Wesley on 03/04/2016.
 */
public class PesquisaDAO extends Pesquisa {

    private static final String URL = "http://" + App.SERVIDOR_WS + "/Projeto_Android_WS/services/PesquisaDAO?wsdl";
    private static final String NAMESPACE = "http://pesquisa.projeto.com.br";

    private static final String PROCURAR = "procurar";

    public boolean procurar(){

        SoapObject objProcurar = new SoapObject(NAMESPACE, PROCURAR);
        objProcurar.addProperty("userId", getUserId());

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objProcurar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + PROCURAR, objEnvelope);

            Vector<SoapObject> objResposta = (Vector<SoapObject>) objEnvelope.getResponse();

            for(SoapObject soapObject : objResposta){
                getListaUsuarios().add(soapObject.getProperty("userId").toString());
            }
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return getListaUsuarios().size() != 0;
    }
}
