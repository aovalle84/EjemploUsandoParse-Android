package com.example.gastosusandoparse.datos;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AccesoDatos {

	public static class GastoRegistro {
		public String id;
		public String content;

		public GastoRegistro(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
	
	public static void inicializar(Context contexto) {
		try {
			Parse.initialize(contexto, "applicationId", "clientKey");
		} catch (Exception e) {
			Log.e("parse-error", "[inicializar] " + e.getMessage());
		}
	}
	
	public interface IGetListaCallback {
		/**
		 * Callback para cuando se haya consultado una lista de items.
		 */
		public void done(List<GastoRegistro> lista);
	}
	
	public static void getListaGasto(final List<GastoRegistro> lista,
			final IGetListaCallback callback) {
		try {
			lista.clear();
			
			ParseQuery query = new ParseQuery("Gasto");
			query.findInBackground(new FindCallback() {
				
				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
						// Llenar lista
						ParseObject gasto = null;
						String descripcion = "";
						String id = "";
						for (int i = 0; i < objects.size(); i++) {
							gasto = objects.get(i);
							id = gasto.getObjectId();
							descripcion = gasto.getString("descripcion");
							lista.add(new GastoRegistro(id, descripcion));
						}
						
						lista.add(new GastoRegistro("", "[Agregar nuevo gasto]"));
					} else {
						Log.e("parse-error", "[getListaGasto] " + e.getMessage());
					}
					callback.done(lista);
				}
			});
		} catch (Exception e) {
			Log.e("parse-error", "[getListaGasto] " + e.getMessage());
		}
	}
	
	public interface IGetItemCallback {
		/**
		 * Callback para cuando se haya consultado un item.
		 */
		public void done(ParseObject item);
	}
	
	public static void getGasto(String id,
			final IGetItemCallback callback) {
		try {
			ParseQuery query = new ParseQuery("Gasto");
			query.getInBackground(id, new GetCallback() {
				
				@Override
				public void done(ParseObject object, ParseException e) {
					if (e == null) {
						callback.done(object);
					} else {
						Log.e("parse-error", "[getGasto] " + e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			Log.e("parse-error", "[getGasto] " + e.getMessage());
		}
	}
	
	public static void setGasto(ParseObject gasto) {
		try {
			gasto.saveInBackground();
		} catch (Exception e) {
			Log.e("parse-error", "[setGasto] " + e.getMessage());
		}
	}
	
	public static ParseObject GetNuevoGasto() {
		ParseObject gasto = new ParseObject("Gasto");
		gasto.put("fecha", new Date());
		gasto.put("descripcion", "");
		gasto.put("monto", "0.0");
		return gasto;
	}
}
