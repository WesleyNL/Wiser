package br.com.wiser.activity.encontrarusuarios.pesquisa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import br.com.wiser.Sistema;
import br.com.wiser.activity.R;
import br.com.wiser.activity.encontrarusuarios.resultados.ChatResultadosActivity;

import br.com.wiser.utils.ComboBoxItem;
import br.com.wiser.utils.Utils;
import br.com.wiser.business.encontrarusuarios.pesquisa.PesquisaDAO;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ChatPesquisaFragment extends Fragment {

    private Spinner cmbIdioma;
    private Spinner cmbFluencia;
    private SeekBar skrDistancia;
    private Button btnProcurar;
    private ProgressBar pgbLoading;
    private TextView lblDistanciaSelecionada;

    private PesquisaDAO objProcurar;

    private static boolean achou = false;

    public static ChatPesquisaFragment newInstance() {
        return new ChatPesquisaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_pesquisa_pessoas, container, false);
        carregarComponentes(view);

        return view;
    }

    private void carregarComponentes(View view) {
        cmbIdioma = (Spinner) view.findViewById(R.id.cmbIdiomaProcurar);
        cmbFluencia = (Spinner) view.findViewById(R.id.cmbFluenciaProcurar);

        skrDistancia = (SeekBar) view.findViewById(R.id.skrDistancia);
        lblDistanciaSelecionada = (TextView) view.findViewById(R.id.lblDistanciaSelecionada);

        skrDistancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                lblDistanciaSelecionada.setText(progressValue + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnProcurar = (Button) view.findViewById(R.id.btnProcurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurar(v);
            }
        });

        pgbLoading = (ProgressBar)view.findViewById(R.id.pgbLoading);

        Utils.carregarComboIdiomas(cmbIdioma, view.getContext(), true);
        Utils.carregarComboFluencia(cmbFluencia, view.getContext(), true);

        objProcurar = new PesquisaDAO();
    }

    private void procurar(View view){
        final Context context = this.getContext();
        final Handler hCarregar = new Handler();

        pgbLoading.setVisibility(View.VISIBLE);
        pgbLoading.bringToFront();

        objProcurar.setIdioma(Utils.getIDComboBox(cmbIdioma));
        objProcurar.setFluencia(Utils.getIDComboBox(cmbFluencia));
        objProcurar.setDistancia(skrDistancia.getProgress());

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (objProcurar.procurarUsuarios(Sistema.getUsuario(context))){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listaUsuarios", objProcurar.getListaResultados());

                    Intent i = new Intent(context, ChatResultadosActivity.class);
                    i.putExtra("listaUsuarios", bundle);
                    startActivity(i);

                    achou = true;
                }

                hCarregar.post(new Runnable() {
                    @Override
                    public void run() {
                        pgbLoading.setVisibility(View.INVISIBLE);

                        if (!achou) {
                            Toast.makeText(context, getString(R.string.usuarios_nao_encontrados), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
}
