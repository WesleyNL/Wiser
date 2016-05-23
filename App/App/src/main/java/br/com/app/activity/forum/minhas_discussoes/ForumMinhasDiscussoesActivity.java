package br.com.app.activity.forum.minhas_discussoes;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 19/05/2016.
 */
public class ForumMinhasDiscussoesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_minhas_discussoes);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
