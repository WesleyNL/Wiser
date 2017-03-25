package br.com.wiser.activity.app.principal;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

import br.com.wiser.activity.R;
import br.com.wiser.activity.chat.conversas.ChatConversasFragment;
import br.com.wiser.activity.encontrarusuarios.pesquisa.ChatPesquisaFragment;
import br.com.wiser.activity.forum.principal.ForumPrincipalFragment;

/**
 * Created by Jefferson on 21/05/2016.
 */
public class AppPrincipalAbas extends FragmentPagerAdapter {
    private String tabTitles[] = null;
    private LinkedList<Object> abasFragmentos = new LinkedList<Object>();

    public AppPrincipalAbas(FragmentManager fm, Context context) {
        super(fm);

        abasFragmentos.add(0, ChatPesquisaFragment.newInstance());
        abasFragmentos.add(1, ChatConversasFragment.newInstance());
        abasFragmentos.add(2, ForumPrincipalFragment.newInstance());

        tabTitles = new String[]{context.getString(R.string.app_pesquisa_title), context.getString(R.string.chat_mensagens_title), context.getString(R.string.forum_principal_title)};
    }

    @Override
    public int getCount() {
        return abasFragmentos.size();
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) abasFragmentos.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}