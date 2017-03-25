package br.com.wiser.activity.app.configuracoes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.business.app.usuario.UsuarioDAO;
import br.com.wiser.utils.ComboBoxItem;
import br.com.wiser.utils.Utils;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class AppConfiguracoesActivity extends Activity {

    private Spinner cmbIdioma;
    private Spinner cmbFluencia;
    private TextView lblContLetras;
    private EditText txtStatus;
    private ProgressBar pgbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_configuracoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
        cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);

        lblContLetras = (TextView) findViewById(R.id.lblContLetras);
        txtStatus = (EditText) findViewById(R.id.txtStatus);
        txtStatus.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContLetras.setText(String.valueOf(s.length()) + " / 30");
            }
        });

        pgbLoading = (ProgressBar) findViewById(R.id.pgbLoading);
        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        final Handler hCarregar = new Handler();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.carregarComboIdiomas(cmbIdioma, AppConfiguracoesActivity.this, false);
                Utils.carregarComboFluencia(cmbFluencia, AppConfiguracoesActivity.this, false);

                carregarDados();

                hCarregar.post(new Runnable() {
                    @Override
                    public void run() {
                        pgbLoading.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void carregarDados() {
        if (Sistema.getUsuario(this).isSetouConfiguracoes()) {
            cmbIdioma.setSelection(Utils.getPosicaoItemComboBox(cmbIdioma, Sistema.getUsuario(this).getIdioma()));
            cmbFluencia.setSelection(Utils.getPosicaoItemComboBox(cmbFluencia, Sistema.getUsuario(this).getFluencia()));
            txtStatus.setText(Sistema.getUsuario(this).getStatus());
            txtStatus.setSelection(txtStatus.getText().length());
        }
    }

    public void salvar(View view) {
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

    private void salvar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        UsuarioDAO usuario = new UsuarioDAO(Sistema.getUsuario(this).getUserID());

        ComboBoxItem comboBoxItem = (ComboBoxItem)cmbIdioma.getItemAtPosition(cmbIdioma.getSelectedItemPosition());
        usuario.setIdioma(comboBoxItem.getId());

        comboBoxItem = (ComboBoxItem)cmbFluencia.getItemAtPosition(cmbFluencia.getSelectedItemPosition());
        usuario.setFluencia(comboBoxItem.getId());

        usuario.setStatus(txtStatus.getText().toString());

        if (usuario.salvarConfiguracoes()) {
            Sistema.getUsuario(this).setIdioma(usuario.getIdioma());
            Sistema.getUsuario(this).setFluencia(usuario.getFluencia());
            Sistema.getUsuario(this).setStatus(usuario.getStatus());
            Sistema.getUsuario(this).setSetouConfiguracoes(true);

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
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle(getString(R.string.confirmar));
        dialogo.setMessage(getString(R.string.confirmar_desativar_conta));
        dialogo.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                desativar();
            }
        });
        dialogo.setNegativeButton(getString(R.string.nao), null);
        dialogo.show();
    }

    private void desativar() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        if (Sistema.getUsuario(this).desativarConta()) {
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

    private void logout(){
        Utils.logout(this);
    }
}
