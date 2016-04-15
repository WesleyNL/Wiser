package br.com.app.activity.sobre;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.activity.R;
import android.widget.TextView;

/**
 * Created by Jefferson on 30/03/2016.
 */
public class SobreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobre);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        carregar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void carregar(){

        String sobre = "";

        TextView lblSobreTopo = (TextView) findViewById(R.id.lblSobreTopo);

        sobre = "    APP     ";
        try {
            sobre += "  Versão " + getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        lblSobreTopo.setText(sobre);

        TextView lblSobreRodape = (TextView) findViewById(R.id.lblSobreRodape);
        sobre = "  © 2016-2016 FATEC CPB";

        lblSobreRodape.setText(sobre);
    }
}
