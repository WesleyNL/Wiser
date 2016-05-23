package br.com.app.activity.app.principal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.adapter.SampleFragmentPageAdapter;
import br.com.app.business.app.configuracao.ConfiguracaoDAO;
import br.com.app.business.app.login.LoginDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class AppPrincipalActivity extends AppCompatActivity {

    private LoginDAO objLoginDAO = null;

    private final int ACCESS_COARSE_LOCATION = 0;
    private LocationManager locationManager = null;

    private SampleFragmentPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_principal);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (hasLocationPermission()) {
            salvar();
        }

        verificarConfiguracao();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_wiser);

        adapter = new SampleFragmentPageAdapter(getSupportFragmentManager(), AppPrincipalActivity.this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLocalizacao();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ACCESS_COARSE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                salvar();
            }
            else {
                Toast.makeText(this, getString(R.string.necessario_permitir), Toast.LENGTH_SHORT).show();
                encerrar();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.itmMinhasDiscussoes:
                Utils.chamarActivity(this, EnmTelas.FORUM_MINHAS_DISCUSSOES);
                break;

            case R.id.itmConfiguracoes:
                Utils.chamarActivity(this, EnmTelas.APP_CONFIGURACOES);
                break;

            case R.id.itmSobre:
                Utils.chamarActivity(this, EnmTelas.APP_SOBRE);
                break;

            case R.id.itmSair:
                encerrar();
                break;
        }

        return (true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return (true);
    }

    private void atualizarLocalizacao(){
        if(locationManager != null){
            if(getPackageManager().checkPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName()) ==
                    getPackageManager().PERMISSION_GRANTED){

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener)new Utils());
            }
        }
    }

    private boolean hasLocationPermission() {
        int status = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_COARSE_LOCATION);
                return false;
            }
        }
        else {
            status = getPackageManager().checkPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION, getPackageName());

            if (status != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public void verificarConfiguracao(){

        ConfiguracaoDAO objConfDAO = new ConfiguracaoDAO();
        objConfDAO.setUserId(Sistema.USER_ID);

        if(!objConfDAO.existe()){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            dialogo.setTitle(getString(R.string.configurar));
            dialogo.setMessage(getString(R.string.configuracoes_nao_personalizadas)
                    + "\n" + getString(R.string.fazer_agora));
            dialogo.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utils.chamarActivity(AppPrincipalActivity.this, EnmTelas.APP_CONFIGURACOES);
                }
            });
            dialogo.setNegativeButton(getString(R.string.nao), null);
            dialogo.show();
        }
    }

    public void salvar() {

        objLoginDAO = new LoginDAO();
        objLoginDAO.setUserId(Sistema.USER_ID);
        objLoginDAO.setCoordUltimoAcesso(Utils.getCoordenadas(this));

        if (!objLoginDAO.salvar()) {
            Toast.makeText(this, getString(R.string.indisponivel_sistema), Toast.LENGTH_LONG).show();
            encerrar();
        }
    }

    protected void encerrar() {
        Utils.chamarActivity(this, EnmTelas.APP_LOGIN, "LOGOUT", true);
    }
}
