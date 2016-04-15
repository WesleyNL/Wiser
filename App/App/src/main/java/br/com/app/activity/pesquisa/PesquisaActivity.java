package br.com.app.activity.pesquisa;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.contatos.ContatosActivity;

import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;
import br.com.app.business.login.LoginDAO;
import br.com.app.business.pesquisa.PesquisaDAO;

import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class PesquisaActivity extends Activity {

    private TextView lblDistanciaSelecionada = null;
    private SeekBar skrDistancia = null;
    private int distanciaSelecionada = 0;

    private PesquisaDAO objProcDAO = null;
    private LoginDAO objLoginDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (!getIntent().getBooleanExtra(EnmTelas.PESQUISA.name(), false)) {
            Utils.chamarActivity(this, EnmTelas.LOGIN);
            return;
        }

        if (!salvar()) {
            Utils.chamarActivity(this, EnmTelas.LOGIN, "LOGOUT", true);
            return;
        }

        setContentView(R.layout.pesquisa);

        carregarComboIdioma();
        carregarComboFluencia();

        skrDistancia = (SeekBar) findViewById(R.id.skrDistancia);
        lblDistanciaSelecionada = (TextView) findViewById(R.id.lblDistanciaSelecionada);

        skrDistancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                lblDistanciaSelecionada.setText("Distância: " + progressValue + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distanciaSelecionada = skrDistancia.getProgress();
            }
        });
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return (true);
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmConfiguracoes:
                Utils.chamarActivity(this, EnmTelas.CONFIGURACOES);
                break;

            case R.id.itmSobre:
                Utils.chamarActivity(this, EnmTelas.SOBRE);
                break;

            case R.id.itmSair:
                Utils.chamarActivity(this, EnmTelas.LOGIN, "LOGOUT", true);
                break;
        }

        return (true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Manifest.permission.ACCESS_COARSE_LOCATION.hashCode()) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                salvar();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean salvar() {

        objLoginDAO = new LoginDAO();
        objLoginDAO.setUserId(Sistema.USER_ID);
        objLoginDAO.setDataUltimoAcesso(new Date());
        objLoginDAO.setCoordUltimoAcesso(Utils.getCoordenadas(this));

        if(!objLoginDAO.salvar()){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Erro");
            dialogo.setMessage("Não foi possível acessar o sistema.");
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
            return false;
        }

        return true;
    }

    public void carregarComboIdioma(){

        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaProcurar);

        Utils.carregarComboIdiomas(cmbIdioma, this);
    }

    public void carregarComboFluencia(){

        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaProcurar);

        Utils.carregarComboFluencia(cmbFluencia, this);
    }

    public void procurar(View view){

        Spinner cmbIdioma = (Spinner) findViewById(R.id.cmbIdiomaConfig);
        Spinner cmbFluencia = (Spinner) findViewById(R.id.cmbFluenciaConfig);

        objProcDAO.setUserId(Sistema.USER_ID);
        objProcDAO.setIdioma(Byte.parseByte(cmbIdioma.getSelectedItem().toString().split("-")[0].trim()));
        objProcDAO.setFluencia(Byte.parseByte(cmbFluencia.getSelectedItem().toString().split("-")[0].trim()));
        objProcDAO.setDistancia(distanciaSelecionada);

        if(objProcDAO.procurar()){
            Intent i = new Intent(this, ContatosActivity.class);
            i.putExtra("listaUsuarios", objProcDAO.getListaUsuarios());
            startActivity(i);
        }
        else{
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("Resultado");
            dialogo.setMessage("Não foram encontrados usuários para os filtros selecionados.");
            dialogo.setNeutralButton("OK", null);
            dialogo.show();
        }
    }
}
