package br.com.app.business.pesquisa;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedList;
import java.util.Vector;

import br.com.app.Sistema;

/**
 * Created by Wesley on 03/04/2016.
 */
public class PesquisaDAO extends Pesquisa {

    private static final String URL = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/PesquisaDAO?wsdl";
    private static final String NAMESPACE = "http://pesquisa.projeto.com.br";

    private static final String PROCURAR = "procurar";

    public boolean procurar(){

        SoapObject objEnvio = new SoapObject(NAMESPACE, PROCURAR);

        SoapObject objProcurar = new SoapObject(NAMESPACE, "procurar");
        objProcurar.addProperty("userId", getUserId());
        objProcurar.addProperty("idioma", String.valueOf(getIdioma()));
        objProcurar.addProperty("fluencia", String.valueOf(getFluencia()));
        objProcurar.addProperty("distancia", String.valueOf(getDistancia()));

        objEnvio.addSoapObject(objProcurar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL);

        try{
            objHTTP.call("urn:" + PROCURAR, objEnvelope);

            SoapObject objResposta = (SoapObject) objEnvelope.bodyIn;

            for(int i=0; i<objResposta.getPropertyCount(); i++){
                getListaUsuarios().add(objResposta.getPropertyAsString(i));
            }
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return getListaUsuarios().size() != 0;
    }
}
