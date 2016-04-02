package br.com.app.activity.pesquisa;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.app.activity.R;

/**
 * Created by Jefferson on 31/03/2016.
 */
public class PesquisaActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_pesquisa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

//        MenuItem m1 = menu.add(0, 0, 0, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//        MenuItem m2 = menu.add(0, 1, 1, "Item 2");
//        m2.setIcon(R.drawable.com_facebook_button_icon);
//        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        MenuItem m3 = menu.add(0, 2, 2, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
//
//        MenuItem m4 = menu.add(0, 3, 3, "Item 1");
//        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return (true);
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.item1:
//                Toast.makeText(PesquisaActivity.this, "Item 1", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item2:
//                Toast.makeText(PesquisaActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item3:
//                Toast.makeText(PesquisaActivity.this, "Item 3", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.item4:
//                Toast.makeText(PesquisaActivity.this, "Item 4", Toast.LENGTH_SHORT).show();
//                break;
//        }

        return (true);
    }
}
