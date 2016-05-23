package br.com.app.activity.chat.mensagens;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 16/05/2016.
 */
public class ChatMensagensFragment extends Fragment {

    public static ChatMensagensFragment newInstance() {
        return new ChatMensagensFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_mensagens, container, false);
        return view;
    }
}
