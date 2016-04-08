package br.com.app.activity.configuracao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.App;
import br.com.app.activity.R;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;
import br.com.app.telas.configuracao.ConfiguracaoDAO;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ConfiguracoesActivity extends Activity {

    private ConfiguracaoDAO objConfDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        objConfDAO = new ConfiguracaoDAO();

        carregarComboIdioma();
        carregarComboFluencia();

        consultar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregarComboIdioma(){

        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);

        Utils.carregarComboIdiomas(cmbIdioma, this);
    }

    public void carregarComboFluencia(){

        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);

        Utils.carregarComboFluencia(cmbFluencia, this);
    }

    public void consultar(){

        objConfDAO.setUserId(App.USER_ID);

        if(objConfDAO.consultar()){
            Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
            cmbIdioma.setSelection(objConfDAO.getIdioma());

            Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbIdiomaConfig);
            cmbFluencia.setSelection(objConfDAO.getFluencia());

            TextView txtStatus = (EditText) findViewById(R.id.txtStatus);
            txtStatus.setText(objConfDAO.getStatus());
        }
        else{
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Erro");
            dialogo.setMessage("Não foi possível carregar as configurações.");
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
        }
    }

    public void salvar(View view){

        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);
        TextView txtStatus = (EditText) findViewById(R.id.txtStatus);

        objConfDAO.setUserId(App.USER_ID);
        objConfDAO.setIdioma(Byte.parseByte(cmbIdioma.getSelectedItem().toString().split("-")[0].trim()));
        objConfDAO.setFluencia(Byte.parseByte(cmbFluencia.getSelectedItem().toString().split("-")[0].trim()));
        objConfDAO.setStatus(txtStatus.getText().toString());

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle("Confirmar");
        dialogo.setMessage("Deseja realmente salvar?");
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                salvar();
            }
        });
        dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){

            }
        });
        dialogo.show();
    }

    public void salvar(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        if(objConfDAO.salvar()){
            dialogo.setTitle("Sucesso");
            dialogo.setMessage("A configuração foi salva com sucesso.");
        }
        else{
            dialogo.setTitle("Erro");
            dialogo.setMessage("Não foi possível salvar a configuração.");
        }

        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }

    public void desativar(View view){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        objConfDAO.setUserId(App.USER_ID);

        if(objConfDAO.desativar()){
            dialogo.setTitle("Sucesso");
            dialogo.setMessage("Conta desativada com sucesso.");
        }
        else{
            dialogo.setTitle("Erro");
            dialogo.setMessage("Não foi possível desativar a conta.");
        }

        dialogo.setNeutralButton("OK", null);
        dialogo.show();

        logout();
    }

    public void logout(){
        Utils.chamarActivity(this, this, EnmTelas.LOGIN, "LOGOUT", true);
    }
}
