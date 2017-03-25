package br.com.wiser.activity.app.sobre;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.wiser.activity.R;
import br.com.wiser.utils.Utils;

import android.view.View;
import android.widget.TextView;

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
            lblVersao.setText(getString(R.string.app_sobre_item_versao, getPackageManager().getPackageInfo(getPackageName(), 0).versionName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compartilhar(View view){
        Utils.compartilharAppComoTexto(view);
    }
}
