package br.com.app.business.pesquisa;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedList;
import java.util.Vector;

import br.com.app.Sistema;
import br.com.app.business.contatos.Contato;

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

            Contato contato = null;
            getListaUsuarios().clear();

            try {
                Vector<SoapObject> objResposta = (Vector<SoapObject>) objEnvelope.getResponse();

                for (SoapObject soapObject : objResposta) {
                    contato = new Contato();
                    contato.setUserID(soapObject.getProperty("userId").toString());
                    contato.setIdioma(Integer.parseInt(soapObject.getProperty("idioma").toString()));
                    contato.setNivelFluencia(Integer.parseInt(soapObject.getProperty("fluencia").toString()));
                    contato.setDistancia(Double.parseDouble(soapObject.getProperty("distancia").toString()));
                    contato.setStatus(soapObject.getProperty("status").toString());
                    getListaUsuarios().add(contato);
                }
            } catch (Exception e){
                SoapObject obj = (SoapObject) objEnvelope.getResponse();

                if(obj == null){
                    return false;
                }

                contato = new Contato();
                contato.setUserID(obj.getProperty("userId").toString());
                contato.setIdioma(Integer.parseInt(obj.getProperty("idioma").toString()));
                contato.setNivelFluencia(Integer.parseInt(obj.getProperty("fluencia").toString()));
                contato.setDistancia(Double.parseDouble(obj.getProperty("distancia").toString()));
                contato.setStatus(obj.getProperty("status").toString());
                getListaUsuarios().add(contato);
            }
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return getListaUsuarios().size() != 0;
    }
}
