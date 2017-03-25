package br.com.wiser.activity.forum.nova_discussao;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.business.forum.discussao.DiscussaoDAO;
import br.com.wiser.enums.Activities;
import br.com.wiser.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumNovaDiscussaoActivity extends Activity {

    private DiscussaoDAO objDiscussao;

    private EditText txtTituloDiscussao;
    private EditText txtDescricaoDiscussao;

    private TextView lblContTitulo;
    private TextView lblContDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_nova_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        lblContTitulo = (TextView) findViewById(R.id.lblContTitulo);
        lblContDescricao = (TextView) findViewById(R.id.lblContDescricao);

        txtTituloDiscussao = (EditText) findViewById(R.id.txtTituloDiscussao);
        txtDescricaoDiscussao = (EditText) findViewById(R.id.txtDescricaoDiscussao);

        txtTituloDiscussao.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContTitulo.setText(String.valueOf(s.length()) + " / 30");
            }
        });

        txtDescricaoDiscussao.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lblContDescricao.setText(String.valueOf(s.length()) + " / 250");
            }
        });

        objDiscussao = new DiscussaoDAO();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void salvar(View view) {
        if(txtTituloDiscussao.getText().toString().trim().isEmpty() || txtDescricaoDiscussao.getText().toString().trim().isEmpty()){
            Toast.makeText(this, getString(R.string.erro_criar_discussao_campos), Toast.LENGTH_SHORT).show();
            return;
        }

        objDiscussao.setTitulo(txtTituloDiscussao.getText().toString().trim());
        objDiscussao.setDescricao(txtDescricaoDiscussao.getText().toString().trim());
        objDiscussao.setDataHora(new Date());

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        dialogo.setTitle(getString(R.string.confirmar));
        dialogo.setMessage(getString(R.string.confirmar_salvar));
        dialogo.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                salvar();
            }
        });
        dialogo.setNegativeButton(getString(R.string.nao), null);
        dialogo.show();
    }

    public void salvar(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

        if (objDiscussao.salvarDiscussao(Sistema.getUsuario(this))) {
            dialogo.setTitle(getString(R.string.sucesso));
            dialogo.setMessage(getString(R.string.sucesso_criar_discussao));
            dialogo.setNeutralButton(getString(R.string.ok),  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utils.chamarActivity(ForumNovaDiscussaoActivity.this, Activities.FORUM_MINHAS_DISCUSSOES);
                    finish();
                }
            });
        }
        else {
            dialogo.setTitle(getString(R.string.erro));
            dialogo.setMessage(getString(R.string.erro_criar_discussao));
            dialogo.setNeutralButton(R.string.ok,  null);
        }

        dialogo.show();
    }
}
