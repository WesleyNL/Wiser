package br.com.app.business.app.servidor;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Vector;

import br.com.app.Sistema;
import br.com.app.business.app.configuracao.Configuracao;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.app.login.Login;
import br.com.app.business.chat.pesquisa.Pesquisa;
import br.com.app.utils.IdiomaFluencia;

/**
 * Created by Wesley on 11/05/2016.
 */
public class Servidor {

    private static final String SALVAR = "salvar";
    private static final String CONSULTAR = "consultar";
    private static final String DESATIVAR = "desativar";
    private static final String VERIFICAR = "existe";
    private static final String PROCURAR = "procurar";
    private static final String PESQ_IDIOMAS = "pesquisarIdiomas";
    private static final String PESQ_FLUENCIAS = "pesquisarFluencias";
    private static final String GET_ACCESS_TOKEN = "getAccessToken";

    private static final String URL_SISTEMA = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/Sistema?wsdl";
    private static final String NAMESPACE_PROJETO = "http://projeto.com.br";

    private static final String URL_PESQUISA = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/PesquisaDAO?wsdl";
    private static final String NAMESPACE_PESQUISA = "http://pesquisa.projeto.com.br";

    private static final String URL_CONFIGURACAO = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/ConfiguracaoDAO?wsdl";
    private static final String NAMESPACE_CONFIGURACAO = "http://configuracoes.projeto.com.br";

    private static final String URL_LOGIN = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/LoginDAO?wsdl";
    private static final String NAMESPACE_PRINCIPAL = "http://principal.projeto.com.br";

    private static final String URL_UTILS = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/Utils?wsdl";
    private static final String NAMESPACE_UTILS = "http://utils.projeto.com.br";

    public static String getAccessToken(){

        if(Sistema.ACCESS_TOKEN != null){
            return Sistema.ACCESS_TOKEN.getToken();
        }

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(new SoapObject(NAMESPACE_PROJETO, GET_ACCESS_TOKEN));
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_SISTEMA);

        String accessToken = "";

        try{
            objHTTP.call("urn:" + GET_ACCESS_TOKEN, objEnvelope);

            accessToken = ((SoapPrimitive) objEnvelope.getResponse()).toString();
        } catch(Exception e){
            e.printStackTrace();
        }

        return accessToken;
    }

    public static boolean pesquisarUsuarios(Pesquisa dados) {

        SoapObject objEnvio = new SoapObject(NAMESPACE_PESQUISA, PROCURAR);

        SoapObject objProcurar = new SoapObject(NAMESPACE_PESQUISA, "procurar");
        objProcurar.addProperty("userId", dados.getUserId());
        objProcurar.addProperty("idioma", String.valueOf(dados.getIdioma()));
        objProcurar.addProperty("fluencia", String.valueOf(dados.getFluencia()));
        objProcurar.addProperty("distancia", String.valueOf(dados.getDistancia()));

        objEnvio.addSoapObject(objProcurar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_PESQUISA);

        try {
            objHTTP.call("urn:" + PROCURAR, objEnvelope);

            Contato contato = null;
            dados.getListaUsuarios().clear();

            try {
                Vector<SoapObject> objResposta = (Vector<SoapObject>) objEnvelope.getResponse();

                for (SoapObject soapObject : objResposta) {
                    contato = new Contato();
                    contato.setUserID(soapObject.getProperty("userId").toString());
                    contato.setIdioma(Integer.parseInt(soapObject.getProperty("idioma").toString()));
                    contato.setNivelFluencia(Integer.parseInt(soapObject.getProperty("fluencia").toString()));
                    contato.setDistancia(Double.parseDouble(soapObject.getProperty("distancia").toString()));
                    contato.setStatus(soapObject.getProperty("status").toString());
                    dados.getListaUsuarios().add(contato);
                }
            } catch (Exception e) {
                SoapObject obj = (SoapObject) objEnvelope.getResponse();

                if (obj == null) {
                    return false;
                }

                contato = new Contato();
                contato.setUserID(obj.getProperty("userId").toString());
                contato.setIdioma(Integer.parseInt(obj.getProperty("idioma").toString()));
                contato.setNivelFluencia(Integer.parseInt(obj.getProperty("fluencia").toString()));
                contato.setDistancia(Double.parseDouble(obj.getProperty("distancia").toString()));
                contato.setStatus(obj.getProperty("status").toString());
                dados.getListaUsuarios().add(contato);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return dados.getListaUsuarios().size() != 0;
    }

    public static boolean consultarConfig(Configuracao dados) {

        SoapObject objEnvio = new SoapObject(NAMESPACE_CONFIGURACAO, CONSULTAR);

        SoapObject objConsultar = new SoapObject(NAMESPACE_CONFIGURACAO, "configuracao");
        objConsultar.addProperty("userId", dados.getUserId());

        objEnvio.addSoapObject(objConsultar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_CONFIGURACAO);

        try {
            objHTTP.call("urn:" + CONSULTAR, objEnvelope);

            SoapObject objResposta = (SoapObject) objEnvelope.getResponse();

            dados.setUserId(objResposta.getProperty("userId").toString());
            dados.setIdioma(Byte.parseByte(objResposta.getProperty("idioma").toString()));
            dados.setFluencia(Byte.parseByte(objResposta.getProperty("fluencia").toString()));
            dados.setStatus(objResposta.getProperty("status").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean salvarConfig(Configuracao dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_CONFIGURACAO, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE_CONFIGURACAO, "configuracao");
        objSalvar.addProperty("userId", dados.getUserId());
        objSalvar.addProperty("idioma", String.valueOf(dados.getIdioma()));
        objSalvar.addProperty("fluencia", String.valueOf(dados.getFluencia()));
        objSalvar.addProperty("status", dados.getStatus());

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_CONFIGURACAO);

        try{
            objHTTP.call("urn:" + SALVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean desativarConfig(Configuracao dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_CONFIGURACAO, DESATIVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE_CONFIGURACAO, "configuracao");
        objSalvar.addProperty("userId", dados.getUserId());

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_CONFIGURACAO);

        try{
            objHTTP.call("urn:" + DESATIVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean existeConfig(Configuracao dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_CONFIGURACAO, VERIFICAR);

        SoapObject objVerificar = new SoapObject(NAMESPACE_CONFIGURACAO, "configuracao");
        objVerificar.addProperty("userId", dados.getUserId());

        objEnvio.addSoapObject(objVerificar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_CONFIGURACAO);

        try{
            objHTTP.call("urn:" + VERIFICAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean salvarLogin(Login dados){

        String coordUsuario = dados.getCoordUltimoAcesso();

        if(Sistema.USER_ID.trim().equals("") || coordUsuario.trim().equals("")){
            return false;
        }

        SoapObject objEnvio = new SoapObject(NAMESPACE_PRINCIPAL, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE_PRINCIPAL, "login");
        objSalvar.addProperty("userId", dados.getUserId());
        objSalvar.addProperty("coordUltimoAcesso", String.valueOf(coordUsuario));

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_LOGIN);

        try{
            objHTTP.call("urn:" + SALVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static LinkedList<IdiomaFluencia> pesquisarIdiomas(boolean todos, LinkedHashMap<Integer, String> hashIdiomas) {
        return pesquisarIdiomaFluencia(todos, hashIdiomas, PESQ_IDIOMAS);
    }

    public static LinkedList<IdiomaFluencia> pesquisarFluencias(boolean todos, LinkedHashMap<Integer, String> hashFluencias) {
        return pesquisarIdiomaFluencia(todos, hashFluencias, PESQ_FLUENCIAS);
    }

    private static LinkedList<IdiomaFluencia> pesquisarIdiomaFluencia(boolean todos, LinkedHashMap<Integer, String> hashMap, String metodo){

        LinkedList<IdiomaFluencia> lista = null;
        SoapObject objPesquisar = new SoapObject(NAMESPACE_UTILS, metodo);

        SoapObject objEnvio = new SoapObject(NAMESPACE_UTILS, "utils");
        objEnvio.addProperty("todos", todos);

        objPesquisar.addSoapObject(objEnvio);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objPesquisar);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_UTILS);

        try {
            objHTTP.call("urn:" + metodo, objEnvelope);

            SoapObject objResposta = (SoapObject) objEnvelope.bodyIn;

            lista = new LinkedList<IdiomaFluencia>();
            hashMap.clear();
            String[] aux;

            for (int i = 0; i < objResposta.getPropertyCount(); i++) {
                aux = objResposta.getPropertyAsString(i).split("-");
                lista.add(new IdiomaFluencia(Integer.parseInt(aux[0].trim()), aux[1].trim()));
                hashMap.put(Integer.parseInt(aux[0].trim()), aux[1].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}