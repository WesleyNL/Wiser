package br.com.wiser.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.wiser.activity.R;
import br.com.wiser.activity.app.configuracoes.AppConfiguracoesActivity;
import br.com.wiser.activity.app.principal.AppPrincipalActivity;
import br.com.wiser.activity.app.splashscreen.AppSplashScreenActivity;
import br.com.wiser.activity.chat.conversas.ChatConversasFragment;
import br.com.wiser.activity.encontrarusuarios.resultados.ChatResultadosActivity;
import br.com.wiser.activity.app.login.AppLoginActivity;
import br.com.wiser.activity.encontrarusuarios.pesquisa.ChatPesquisaFragment;
import br.com.wiser.activity.forum.discussao.ForumDiscussaoActivity;
import br.com.wiser.activity.forum.minhas_discussoes.ForumMinhasDiscussoesActivity;
import br.com.wiser.activity.forum.nova_discussao.ForumNovaDiscussaoActivity;
import br.com.wiser.activity.forum.pesquisa.ForumPesquisaActivity;
import br.com.wiser.activity.forum.principal.ForumPrincipalFragment;

import br.com.wiser.activity.app.sobre.AppSobreActivity;
import br.com.wiser.business.app.servidor.Servidor;
import br.com.wiser.business.app.usuario.Usuario;
import br.com.wiser.enums.Activities;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Wesley on 03/04/2016.
 */
public class Utils {

    public static int getIDComboBox(Spinner cmb) {
        return ((ComboBoxItem)cmb.getItemAtPosition(cmb.getSelectedItemPosition())).getId();
    }

    public static int getPosicaoItemComboBox(Spinner cmb, int id) {
        for (int i = 0; i < cmb.getAdapter().getCount(); i++) {
            if (((ComboBoxItem) cmb.getItemAtPosition(i)).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    public static String getDescricaoFluencia(int id) {
        return new Servidor().new App().getIdiomas(false).get(id).getDescricao();
    }

    public static String getDescricaoIdioma(int id) {
        return new Servidor().new App().getFluencias(false).get(id).getDescricao();
    }

    public static void carregarComboFluencia(Spinner cmbFluencia, Context context, boolean itemTodos) {
        carregarCombo(new Servidor().new App().getFluencias(itemTodos), cmbFluencia, context);
    }

    public static void carregarComboIdiomas(Spinner cmbIdioma, Context context, boolean itemTodos) {
        carregarCombo(new Servidor().new App().getIdiomas(itemTodos), cmbIdioma, context);
    }

    private static void carregarCombo(List<ComboBoxItem> itens, Spinner cmb, Context context) {
        ArrayAdapter<ComboBoxItem> dataAdapter = new ArrayAdapter<ComboBoxItem>(context, R.layout.frm_spinner_text, itens);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmb.setAdapter(dataAdapter);
    }

    public static void chamarActivity(Activity activity, Activities activityDestino) {
        Intent i = new Intent();
        Class classe = null;

        try {
            switch (activityDestino) {
                case APP_CONFIGURACOES:
                    classe = AppConfiguracoesActivity.class;
                    break;
                case APP_LOGIN:
                    classe = AppLoginActivity.class;
                    break;
                case APP_PRINCIPAL:
                    classe = AppPrincipalActivity.class;
                    break;
                case APP_SOBRE:
                    classe = AppSobreActivity.class;
                    break;
                case APP_SPLASHSCREEN:
                    classe = AppSplashScreenActivity.class;
                    break;

                case CHAT_MENSAGENS:
                    classe = ChatConversasFragment.class;
                    break;
                case CHAT_PESQUISA:
                    classe = ChatPesquisaFragment.class;
                    break;
                case CHAT_RESULTADOS:
                    classe = ChatResultadosActivity.class;
                    break;

                case FORUM_DISCUSSAO:
                    classe = ForumDiscussaoActivity.class;
                    break;
                case FORUM_MINHAS_DISCUSSOES:
                    classe = ForumMinhasDiscussoesActivity.class;
                    break;
                case FORUM_NOVA_DISCUSSAO:
                    classe = ForumNovaDiscussaoActivity.class;
                    break;
                case FORUM_PESQUISA:
                    classe = ForumPesquisaActivity.class;
                    break;
                case FORUM_PRINCIPAL:
                    classe = ForumPrincipalFragment.class;
                    break;
            }

            i.setClass(activity, classe);

            if (activity instanceof AppLoginActivity) {
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            activity.startActivity(i);

            if (activityDestino == Activities.APP_LOGIN) {
                activity.finish();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logout(Activity activity) {
        Intent i = new Intent(activity, AppLoginActivity.class);

        i.putExtra("LOGOUT", true);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(i);
        activity.finish();
    }

    public static void loadImageInBackground(Context context, String url, final ImageView imageView, final ProgressBar prgBarra) {

        if (!TextUtils.isEmpty(url)) {
            imageView.setVisibility(View.INVISIBLE);
            prgBarra.setVisibility(View.VISIBLE);

            Picasso.with(context)
                    .load(url)
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.setVisibility(View.VISIBLE);
                            prgBarra.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {
                            imageView.setVisibility(View.VISIBLE);
                            prgBarra.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    public static void compartilharComoImagem(View view){

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap img = view.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(view.getContext().getContentResolver(),
                img, view.getContext().getString(R.string.compartilhar_titulo), null);

        Intent iCompartilhar = new Intent(Intent.ACTION_SEND);
        Uri imgDiscussao = Uri.parse(path);

        iCompartilhar.setType("image/png");
        iCompartilhar.putExtra(Intent.EXTRA_STREAM, imgDiscussao);
        view.getContext().startActivity(Intent.createChooser(iCompartilhar,
                view.getContext().getString(R.string.compartilhar_discussao_sistema)));
    }

    public static void compartilharAppComoTexto(View view){

        Intent iCompartilhar = new Intent(Intent.ACTION_SEND);
        iCompartilhar.setType("text/plain");
        iCompartilhar.putExtra(android.content.Intent.EXTRA_TEXT,
                view.getContext().getString(R.string.sistema_link_playstore));
        view.getContext().startActivity(Intent.createChooser(iCompartilhar,
                view.getContext().getString(R.string.compartilhar_aplicativo_sistema)));
    }

    public static void vibrar(Context context, long duracao){
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(duracao);
    }
}