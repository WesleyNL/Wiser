package br.com.wiser.activity.app.principal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.enums.Activities;
import br.com.wiser.utils.Utils;
import br.com.wiser.utils.UtilsLocation;

public class AppPrincipalActivity extends AppCompatActivity {

    private AppPrincipalAbas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_principal);

        verificarConfiguracao();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new AppPrincipalAbas(getSupportFragmentManager(), AppPrincipalActivity.this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilsLocation.atualizarLocalizacao(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmMinhasDiscussoes:
                Utils.chamarActivity(this, Activities.FORUM_MINHAS_DISCUSSOES);
                break;
            case R.id.itmConfiguracoes:
                Utils.chamarActivity(this, Activities.APP_CONFIGURACOES);
                break;
            case R.id.itmSobre:
                Utils.chamarActivity(this, Activities.APP_SOBRE);
                break;
            case R.id.itmSair:
                logout();
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

    public void verificarConfiguracao(){

        if (!Sistema.getUsuario(this).isSetouConfiguracoes()){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            dialogo.setTitle(getString(R.string.configurar));
            dialogo.setMessage(getString(R.string.configuracoes_nao_personalizadas)
                    + "\n" + getString(R.string.fazer_agora));
            dialogo.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utils.chamarActivity(AppPrincipalActivity.this, Activities.APP_CONFIGURACOES);
                }
            });
            dialogo.setNegativeButton(getString(R.string.nao), null);
            dialogo.show();
        }
    }

    protected void logout() {
        Utils.logout(this);
    }
}
