package br.com.app.business.app.servidor;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Vector;

import br.com.app.Sistema;
import br.com.app.business.app.configuracao.Configuracao;
import br.com.app.business.app.facebook.Facebook;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.app.login.Login;
import br.com.app.business.chat.pesquisa.Pesquisa;
import br.com.app.business.forum.discussao.Discussao;
import br.com.app.business.forum.discussao.Resposta;
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
    private static final String CARREGAR = "carregar";
    private static final String RESPONDER = "responder";
    private static final String PESQ_IDIOMAS = "pesquisarIdiomas";
    private static final String PESQ_FLUENCIAS = "pesquisarFluencias";
    private static final String PESQ_DISCUSSAO_USUARIO = "pesquisarUsuario";
    private static final String PESQ_DISCUSSAO_ESPECIFICA = "pesquisarEspecifico";
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

    private static final String URL_FORUM = "http://" + Sistema.SERVIDOR_WS + "/Projeto_Android_WS/services/DiscussaoDAO?wsdl";
    private static final String NAMESPACE_FORUM = "http://forum.projeto.com.br";

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
        objEnvio.addProperty("appIdioma", String.valueOf(Sistema.APP_IDIOMA));

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

    public static  LinkedList<Discussao> pesquisarDiscussoesUsuario(){
        return carregarDiscussoes(null, Sistema.USER_ID);
    }

    public static LinkedList<Discussao> pesquisarDiscussoesEspecifico(Discussao dados){
        return carregarDiscussoes(dados, "");
    }

    public static LinkedList<Discussao> carregarDiscussoes(){
        return carregarDiscussoes(null, "");
    }

    private static LinkedList<Discussao> carregarDiscussoes(Discussao dados, String userId){

        String metodo = CARREGAR;

        if(!userId.trim().isEmpty()){
            metodo = PESQ_DISCUSSAO_USUARIO;
        }else if(dados != null && dados.getBuscaEspecifica() != 0){
            metodo = PESQ_DISCUSSAO_ESPECIFICA;
        }

        SoapObject objEnvio = new SoapObject(NAMESPACE_FORUM, metodo);

        SoapObject objCarregar = new SoapObject(NAMESPACE_FORUM, "discussao");

        if(!userId.trim().isEmpty()) {
            objCarregar.addProperty("autor", userId);
        }

        if(dados != null && dados.getBuscaEspecifica() != 0) {
            objCarregar.addProperty("idDiscussao", String.valueOf(dados.getIdDiscussao()));
            objCarregar.addProperty("buscaEspecifica", String.valueOf(dados.getBuscaEspecifica()));
            objCarregar.addProperty("autor", dados.getContato().getUserID());
            objCarregar.addProperty("titulo", dados.getTitulo());
            objCarregar.addProperty("descricao", dados.getDescricao());
        }

        objEnvio.addSoapObject(objCarregar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_FORUM);

        LinkedList<Discussao> listaDiscussoes = new LinkedList<Discussao>();

        try {
            objHTTP.call("urn:" + metodo, objEnvelope);

            Discussao objDiscussao = null;
            Resposta objResposta = null;
            LinkedList<Resposta> listaRespostas = null;
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            try {
                Vector<SoapObject> objSoapResposta = (Vector<SoapObject>) objEnvelope.getResponse();

                for (SoapObject soapObject : objSoapResposta) {
                    objDiscussao = new Discussao();
                    objDiscussao.setIdDiscussao(Long.parseLong(soapObject.getProperty("idDiscussao").toString()));
                    objDiscussao.setTitulo(soapObject.getProperty("titulo").toString());
                    objDiscussao.setDescricao(soapObject.getProperty("descricao").toString());
                    objDiscussao.setContRespostas(Long.parseLong(soapObject.getProperty("contRespostas").toString()));
                    objDiscussao.setDataHora(fmt.parse(soapObject.getProperty("dataHoraCustom").toString()));
                    objDiscussao.setContato(Facebook.getProfile(soapObject.getProperty("autor").toString()));

                    try {
                        listaRespostas = new LinkedList<Resposta>();

                        for(int i=7; i<7+objDiscussao.getContRespostas(); i++){
                            SoapObject soapObjectResp = (SoapObject) soapObject.getProperty(i);

                            if (!soapObjectResp.getProperty("idResposta").toString().trim().equals("-1")) {
                                objResposta = new Resposta();
                                objResposta.setIdResposta(Long.parseLong(soapObjectResp.getProperty("idResposta").toString()));
                                objResposta.setDataHora(fmt.parse(soapObjectResp.getProperty("dataHoraCustom").toString()));
                                objResposta.setResposta(soapObjectResp.getProperty("resposta").toString());
                                objResposta.setContato(Facebook.getProfile(soapObjectResp.getProperty("autor").toString()));
                                listaRespostas.add(objResposta);
                            }
                        }

                        objDiscussao.setListaRespostas(listaRespostas);
                        objDiscussao.setContRespostas(listaRespostas.size());
                        listaDiscussoes.add(objDiscussao);

                    }catch(Exception e2){
                        SoapObject soapObjectResp = (SoapObject) soapObject.getProperty("listaRespostas");

                        if(soapObjectResp != null){
                            listaRespostas = new LinkedList<Resposta>();
                            if(!soapObjectResp.getProperty("idResposta").toString().trim().equals("-1")) {
                                objResposta = new Resposta();
                                objResposta.setIdResposta(Long.parseLong(soapObjectResp.getProperty("idResposta").toString()));
                                objResposta.setDataHora(fmt.parse(soapObjectResp.getProperty("dataHoraCustom").toString()));
                                objResposta.setResposta(soapObjectResp.getProperty("resposta").toString());
                                objResposta.setContato(Facebook.getProfile(soapObjectResp.getProperty("autor").toString()));
                                listaRespostas.add(objResposta);
                            }
                        }

                        objDiscussao.setListaRespostas(listaRespostas);
                        objDiscussao.setContRespostas(listaRespostas.size());
                        listaDiscussoes.add(objDiscussao);
                    }
                }
            } catch (Exception e) {
                SoapObject objSoapResposta = (SoapObject) objEnvelope.getResponse();

                if (objSoapResposta != null) {
                    objDiscussao = new Discussao();
                    objDiscussao.setIdDiscussao(Long.parseLong(objSoapResposta.getProperty("idDiscussao").toString()));
                    objDiscussao.setTitulo(objSoapResposta.getProperty("titulo").toString());
                    objDiscussao.setDescricao(objSoapResposta.getProperty("descricao").toString());
                    objDiscussao.setContRespostas(Long.parseLong(objSoapResposta.getProperty("contRespostas").toString()));
                    objDiscussao.setDataHora(fmt.parse(objSoapResposta.getProperty("dataHoraCustom").toString()));
                    objDiscussao.setContato(Facebook.getProfile(objSoapResposta.getProperty("autor").toString()));

                    try {
                        listaRespostas = new LinkedList<Resposta>();

                        for(int i=7; i<7+objDiscussao.getContRespostas(); i++){
                            SoapObject soapObjectResp = (SoapObject) objSoapResposta.getProperty(i);

                            if (!soapObjectResp.getProperty("idResposta").toString().trim().equals("-1")) {
                                objResposta = new Resposta();
                                objResposta.setIdResposta(Long.parseLong(soapObjectResp.getProperty("idResposta").toString()));
                                objResposta.setDataHora(fmt.parse(soapObjectResp.getProperty("dataHoraCustom").toString()));
                                objResposta.setResposta(soapObjectResp.getProperty("resposta").toString());
                                objResposta.setContato(Facebook.getProfile(soapObjectResp.getProperty("autor").toString()));
                                listaRespostas.add(objResposta);
                            }
                        }

                        objDiscussao.setListaRespostas(listaRespostas);
                        objDiscussao.setContRespostas(listaRespostas.size());
                        listaDiscussoes.add(objDiscussao);
                    }catch(Exception e2){
                        SoapObject soapObjectResp = (SoapObject) objSoapResposta.getProperty("listaRespostas");

                        if(soapObjectResp != null){
                            listaRespostas = new LinkedList<Resposta>();
                            if(!soapObjectResp.getProperty("idResposta").toString().trim().equals("-1")) {
                                objResposta = new Resposta();
                                objResposta.setIdResposta(Long.parseLong(soapObjectResp.getProperty("idResposta").toString()));
                                objResposta.setDataHora(fmt.parse(soapObjectResp.getProperty("dataHoraCustom").toString()));
                                objResposta.setResposta(soapObjectResp.getProperty("resposta").toString());
                                objResposta.setContato(Facebook.getProfile(soapObjectResp.getProperty("autor").toString()));
                                listaRespostas.add(objResposta);
                            }
                        }

                        objDiscussao.setListaRespostas(listaRespostas);
                        objDiscussao.setContRespostas(listaRespostas.size());
                        listaDiscussoes.add(objDiscussao);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return listaDiscussoes;
    }

    public static long criarDiscussao(Discussao dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_FORUM, SALVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE_FORUM, "discussao");
        objSalvar.addProperty("titulo", dados.getTitulo());
        objSalvar.addProperty("descricao", dados.getDescricao());
        objSalvar.addProperty("autor", dados.getContato().getUserID());

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_FORUM);

        try{
            objHTTP.call("urn:" + SALVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Long.parseLong(objResposta.toString());
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean desativarDiscussao(Discussao dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_FORUM, DESATIVAR);

        SoapObject objSalvar = new SoapObject(NAMESPACE_FORUM, "discussao");
        objSalvar.addProperty("autor", dados.getContato().getUserID());
        objSalvar.addProperty("idDiscussao", String.valueOf(dados.getIdDiscussao()));

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_FORUM);

        try{
            objHTTP.call("urn:" + DESATIVAR, objEnvelope);

            SoapPrimitive objResposta = (SoapPrimitive) objEnvelope.getResponse();

            return Boolean.parseBoolean(objResposta.toString());
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void responderDiscussao(Resposta dados){

        SoapObject objEnvio = new SoapObject(NAMESPACE_FORUM, RESPONDER);

        SoapObject objSalvar = new SoapObject(NAMESPACE_FORUM, "resposta");
        objSalvar.addProperty("autor", dados.getContato().getUserID());
        objSalvar.addProperty("idDiscussao", String.valueOf(dados.getIdDiscussao()));
        objSalvar.addProperty("resposta", dados.getResposta());

        objEnvio.addSoapObject(objSalvar);

        SoapSerializationEnvelope objEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        objEnvelope.setOutputSoapObject(objEnvio);
        objEnvelope.implicitTypes = true;

        HttpTransportSE objHTTP = new HttpTransportSE(URL_FORUM);

        try{
            objHTTP.call("urn:" + RESPONDER, objEnvelope);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}