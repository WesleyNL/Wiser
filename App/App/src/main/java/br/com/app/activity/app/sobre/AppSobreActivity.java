package br.com.app.activity.app.sobre;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.activity.R;
import br.com.app.utils.Utils;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Jefferson on 30/03/2016.
 */
public class AppSobreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_sobre);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){
        TextView lblVersao = (TextView) findViewById(R.id.lblVersao);

        try {
            lblVersao.setText(getString(R.string.versao, getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compartilhar(View view){
        Utils.compartilharAplicativoEmTexto(view);
    }
}
