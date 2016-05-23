package br.com.app.activity.chat.pesquisa;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import br.com.app.Sistema;
import br.com.app.activity.R;
import br.com.app.activity.chat.resultados.ChatResultadosActivity;

import br.com.app.utils.IdiomaFluencia;
import br.com.app.utils.Utils;
import br.com.app.business.chat.pesquisa.PesquisaDAO;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class ChatPesquisaFragment extends Fragment {

    private Spinner cmbIdioma;
    private Spinner cmbFluencia;
    private SeekBar skrDistancia;
    private Button btnProcurar;

    private TextView lblDistanciaSelecionada;
    private int distanciaSelecionada = 0;

    private PesquisaDAO objProcDAO = null;

    public static ChatPesquisaFragment newInstance() {
        return new ChatPesquisaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_pesquisa, container, false);
        initComponentes(view);

        return view;
    }

    private void initComponentes(View view) {

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
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                distanciaSelecionada = skrDistancia.getProgress();
            }
        });

        btnProcurar = (Button) view.findViewById(R.id.btnProcurar);
        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurar(v);
            }
        });

        carregarComboIdioma(view);
        carregarComboFluencia(view);

        objProcDAO = new PesquisaDAO();
    }

    private void carregarComboIdioma(View view) {
        Utils.carregarComboIdiomas(cmbIdioma, view.getContext(), true);
        cmbIdioma.setSelection(Utils.getPosicaoIdioma(1));
    }

    private void carregarComboFluencia(View view) {
        Utils.carregarComboFluencia(cmbFluencia, view.getContext(), true);
        cmbFluencia.setSelection(Utils.getPosicaoFluencia(1));
    }

    private void procurar(View view){


        IdiomaFluencia idiomaFluencia = null;

        objProcDAO.setUserId(Sistema.USER_ID);
        idiomaFluencia = (IdiomaFluencia) cmbIdioma.getItemAtPosition(cmbIdioma.getSelectedItemPosition());
        objProcDAO.setIdioma((byte) idiomaFluencia.getId());
        idiomaFluencia = (IdiomaFluencia) cmbFluencia.getItemAtPosition(cmbFluencia.getSelectedItemPosition());
        objProcDAO.setFluencia((byte) idiomaFluencia.getId());
        objProcDAO.setDistancia(distanciaSelecionada);

        if (objProcDAO.procurar()){

            Bundle bundle = new Bundle();
            bundle.putSerializable("listaUsuarios", objProcDAO.getListaUsuarios());

            Intent i = new Intent(view.getContext(), ChatResultadosActivity.class);
            i.putExtra("listaUsuarios", bundle);
            startActivity(i);
        }
        else {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(view.getContext());
            dialogo.setTitle(getString(R.string.resultado));
            dialogo.setMessage(getString(R.string.usuarios_nao_encontrados));
            dialogo.setNeutralButton(getString(R.string.ok), null);
            dialogo.show();
        }
    }
}
