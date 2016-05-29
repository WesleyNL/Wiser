package br.com.app.activity.app.configuracoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.IdiomaFluencia;
import br.com.app.utils.Utils;
import br.com.app.business.app.configuracao.ConfiguracaoDAO;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class AppConfiguracoesActivity extends Activity {

    private ConfiguracaoDAO objConfDAO = null;

    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_configuracoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView lblContLetras = (TextView) findViewById(R.id.lblContLetras);
        EditText txtStatus = (EditText) findViewById(R.id.txtStatus);
        TextWatcher textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContLetras.setText(String.valueOf(s.length()) + " / 30");
            }
        };
        txtStatus.addTextChangedListener(textWatcher);

        objConfDAO = new ConfiguracaoDAO();

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Handler hCarregar = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        carregarComboIdioma();
                        carregarComboFluencia();

                        consultar();

                        hCarregar.post(new Runnable() {
                            @Override
                            public void run() {
                                pgbLoading.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public synchronized void carregarComboIdioma(){
        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
        Utils.carregarComboIdiomas(cmbIdioma, this);
    }

    public synchronized void carregarComboFluencia(){
        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);
        Utils.carregarComboFluencia(cmbFluencia, this);
    }

    public synchronized void consultar(){

        objConfDAO.setUserId(Sistema.USER_ID);

        if(objConfDAO.consultar()){
            Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
            cmbIdioma.setSelection(Utils.getPosicaoIdioma(objConfDAO.getIdioma()));

            Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);
            cmbFluencia.setSelection(Utils.getPosicaoFluencia(objConfDAO.getFluencia()));

            if(objConfDAO.getStatus().trim().isEmpty()){
                objConfDAO.setStatus("");
            }

            EditText txtStatus = (EditText) findViewById(R.id.txtStatus);
            txtStatus.setText(objConfDAO.getStatus());
            txtStatus.setSelection(txtStatus.getText().length());
        }
        else{
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle(getString(R.string.erro));
            dialogo.setMessage(getString(R.string.indisponivel_configuracao));
            dialogo.setNeutralButton(getString(R.string.ok), null);
            dialogo.show();
        }
    }

    public void salvar(View view){

        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);
        TextView txtStatus = (EditText) findViewById(R.id.txtStatus);
        IdiomaFluencia idiomaFluencia = null;

        objConfDAO.setUserId(Sistema.USER_ID);
        idiomaFluencia = (IdiomaFluencia)cmbIdioma.getItemAtPosition(cmbIdioma.getSelectedItemPosition());
        objConfDAO.setIdioma((byte)idiomaFluencia.getId());
        idiomaFluencia = (IdiomaFluencia)cmbFluencia.getItemAtPosition(cmbFluencia.getSelectedItemPosition());
        objConfDAO.setFluencia((byte)idiomaFluencia.getId());
        objConfDAO.setStatus(txtStatus.getText().toString());

        if(objConfDAO.getStatus().trim().isEmpty()){
            objConfDAO.setStatus(" ");
        }

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle(getString(R.string.confirmar));
        dialogo.setMessage(getString(R.string.confirmar_salvar));
        dialogo.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                salvar();
            }
        });
        dialogo.setNegativeButton(getString(R.string.nao), null);
        dialogo.show();
    }

    public void salvar(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        if (objConfDAO.salvar()) {
            dialogo.setTitle(getString(R.string.sucesso));
            dialogo.setMessage(getString(R.string.sucesso_salvar_configuracao));
            dialogo.setNeutralButton(getString(R.string.ok),  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
        }
        else {
            dialogo.setTitle(getString(R.string.erro));
            dialogo.setMessage(getString(R.string.erro_salvar_configuracao));
            dialogo.setNeutralButton(R.string.ok,  null);
        }

        dialogo.show();
    }

    public void desativar(View view){

        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        AlertDialog.Builder confirmar = new AlertDialog.Builder(this);
        confirmar.setTitle(getString(R.string.confirmar));
        confirmar.setMessage(getString(R.string.confirmar_desativar_conta));

        confirmar.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                objConfDAO.setUserId(Sistema.USER_ID);

                if (objConfDAO.desativar()) {
                    dialogo.setTitle(getString(R.string.sucesso));
                    dialogo.setMessage(R.string.sucesso_conta_desativada);
                    dialogo.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            logout();
                        }
                    });
                }
                else {
                    dialogo.setTitle(getString(R.string.erro));
                    dialogo.setMessage(getString(R.string.erro_desativar_conta));
                    dialogo.setNeutralButton(getString(R.string.ok), null);
                }

                dialogo.show();
            }
        });
        confirmar.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;
            }
        });

        confirmar.show();
    }

    public void logout(){
        Utils.chamarActivity(this, EnmTelas.APP_LOGIN, "LOGOUT", true);
    }
}
