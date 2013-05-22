package com.example.gastosusandoparse;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gastosusandoparse.datos.AccesoDatos;
import com.example.gastosusandoparse.datos.AccesoDatos.GastoRegistro;
import com.example.gastosusandoparse.datos.AccesoDatos.IGetListaCallback;

public class MainActivity extends ListActivity {

	List<GastoRegistro> mObjListaGasto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AccesoDatos.inicializar(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void llenarLista() {
		mObjListaGasto = new ArrayList<GastoRegistro>(); 
		AccesoDatos.getListaGasto(mObjListaGasto, new IGetListaCallback() {
			
			@Override
			public void done(List<GastoRegistro> lista) {
				setListAdapter(new ArrayAdapter<GastoRegistro>(getBaseContext(),
						android.R.layout.simple_list_item_activated_1,
						android.R.id.text1, lista));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		llenarLista();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent detalleIntent = new Intent(this, GastoDetalleActivity.class);
		String idGasto = mObjListaGasto.get(position).id;
		detalleIntent.putExtra("id", idGasto);
		startActivity(detalleIntent);
	}

}
