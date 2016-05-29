package br.com.app.activity.forum.nova_discussao;

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

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.business.chat.contatos.Contato;
import br.com.app.business.forum.discussao.DiscussaoDAO;
import br.com.app.enums.EnmTelas;
import br.com.app.utils.Utils;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ForumNovaDiscussaoActivity extends Activity {

    private DiscussaoDAO objDiscussaoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_nova_discussao);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView lblContTitulo = (TextView) findViewById(R.id.lblContTitulo);
        final TextView lblContDescricao = (TextView) findViewById(R.id.lblContDescricao);

        EditText txtTituloDiscussao = (EditText) findViewById(R.id.txtTituloDiscussao);
        EditText txtDescricaoDiscussao = (EditText) findViewById(R.id.txtDescricaoDiscussao);

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

        objDiscussaoDAO = new DiscussaoDAO();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void salvar(View view){

        EditText txtTituloDiscussao = (EditText) findViewById(R.id.txtTituloDiscussao);
        EditText txtDescricaoDiscussao = (EditText) findViewById(R.id.txtDescricaoDiscussao);

        objDiscussaoDAO.setTitulo(txtTituloDiscussao.getText().toString().trim());
        objDiscussaoDAO.setDescricao(txtDescricaoDiscussao.getText().toString().trim());

        if(objDiscussaoDAO.getTitulo().trim().isEmpty() || objDiscussaoDAO.getDescricao().trim().isEmpty()){
            Toast.makeText(this, getString(R.string.erro_criar_discussao_campos), Toast.LENGTH_SHORT).show();
            return;
        }

        Contato objContato = new Contato();
        objContato.setUserID(Sistema.USER_ID);

        objDiscussaoDAO.setContato(objContato);

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
        final Activity atual = this;

        if (objDiscussaoDAO.salvar()) {
            dialogo.setTitle(getString(R.string.sucesso));
            dialogo.setMessage(getString(R.string.sucesso_criar_discussao));
            dialogo.setNeutralButton(getString(R.string.ok),  new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utils.chamarActivity(atual, EnmTelas.FORUM_MINHAS_DISCUSSOES);
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
