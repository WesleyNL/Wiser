package br.com.app.activity.forum.nova_discussao;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumNovaDiscussaoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_nova_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView lblContTitulo = (TextView) super.findViewById(R.id.lblContTitulo);
        final TextView lblContDescricao = (TextView) super.findViewById(R.id.lblContDescricao);

        EditText txtTituloDiscussao = (EditText) super.findViewById(R.id.txtTituloDiscussao);
        EditText txtDescricaoDiscussao = (EditText) super.findViewById(R.id.txtDescricaoDiscussao);

        TextWatcher textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContTitulo.setText(String.valueOf(s.length()) + " / 30");
            }
        };
        txtTituloDiscussao.addTextChangedListener(textWatcher);

        textWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContDescricao.setText(String.valueOf(s.length()) + " / 250");
            }
        };
        txtDescricaoDiscussao.addTextChangedListener(textWatcher);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
