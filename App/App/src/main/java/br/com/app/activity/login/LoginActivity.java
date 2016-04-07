package br.com.app.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import br.com.app.activity.pesquisa.PesquisaActivity;
import br.com.app.activity.R;
import br.com.app.enums.EnmTelas;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class LoginActivity extends Activity implements IFacebook {
    private FacebookActivity facebookActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebookActivity = new FacebookActivity(this);

        if (getIntent().getBooleanExtra("LOGOUT", false)) {
            facebookActivity.logout();
        }

        if (facebookActivity.logado()) {

            encerrar();
            return;
        }

        setContentView(R.layout.login);
        facebookActivity.setBtnLogin((Button) super.findViewById(R.id.btnLogin));
    }

    // Quando clica no botão de LoginActivity, é iniciado uma nova Activity e este método é o retorno desta Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookActivity.callbackManager(requestCode, resultCode, data);
    }

    public void setResultado_Login(String resultado_Login) {
        if (resultado_Login.equals("SUCESSO")) {
            encerrar();
        }
        else {
            mostrarMensagem(resultado_Login);
        }
    }

    private void mostrarMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void encerrar() {
        PesquisaActivity.User_ID = facebookActivity.getUser_ID();
        mostrarMensagem("Olá, " + facebookActivity.getUser_Name() + " ;)");

        Intent i = new Intent(this, PesquisaActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(EnmTelas.PESQUISA.name(), true);
        startActivity(i);
        finish();
    }
}
