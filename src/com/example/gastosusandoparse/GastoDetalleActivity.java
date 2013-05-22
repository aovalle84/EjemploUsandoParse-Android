package com.example.gastosusandoparse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.gastosusandoparse.datos.AccesoDatos;
import com.example.gastosusandoparse.datos.AccesoDatos.IGetItemCallback;
import com.parse.ParseObject;

public class GastoDetalleActivity extends Activity {

	private ParseObject mGasto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gasto_detalle);
		
		// Obtener ID de gasto, del intent
		String id = getIntent().getStringExtra("id");
		if (id.length() == 0) {
			mGasto = AccesoDatos.GetNuevoGasto();
		} else {
			AccesoDatos.getGasto(id, new IGetItemCallback() {
				
				@Override
				public void done(ParseObject item) {
					mGasto = item;
					mostrarDatos();
				}
			});
		}
	}

	private void mostrarDatos() {
		String descripcion = "";
		String monto = "";
		if (mGasto != null) {
			descripcion = mGasto.getString("descripcion");
			monto = Double.toString(mGasto.getDouble("monto"));
		}
		((EditText) findViewById(R.id.edtDescripcion))
				.setText(descripcion);
		((EditText) findViewById(R.id.edtMonto))
				.setText(monto);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_gasto_detalle, menu);
		return true;
	}

	public void onGuardarClick(View arg0) {
		if (mGasto != null) {
			EditText edtDescripcion = (EditText) findViewById(R.id.edtDescripcion);
			EditText edtMonto = (EditText) findViewById(R.id.edtMonto);
			
			String descripcionIngresada = edtDescripcion.getText().toString();
			double montoIngresado = Double.parseDouble(edtMonto.getText().toString());
			
			mGasto.put("descripcion", descripcionIngresada);
			mGasto.put("monto", montoIngresado);
			AccesoDatos.setGasto(mGasto);
			finish();
		}
	}

}
