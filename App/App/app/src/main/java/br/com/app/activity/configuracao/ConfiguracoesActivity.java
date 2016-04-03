package br.com.app.activity.configuracao;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ConfiguracoesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracoes);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
