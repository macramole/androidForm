package com.parleboo.formulario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    private String lastNombre;
    private String lastOtrodato;
    private String lastMensaje;
    private String lastLocalidad;
    private String lastEje;
    
    private boolean enCam = false;
    
    private final String numTablet = "10";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().hide();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //mPreview = new Preview(this);
        irAForm();
        //checkCSVExists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void txtMensaje_Click(View view)
    {
    	EditText txtMensaje = (EditText)findViewById(R.id.txtMensaje);
    	EditText txtOtroDato = (EditText)findViewById(R.id.txtOtroDato);
    	EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
    	EditText txtLocalidad = (EditText)findViewById(R.id.txtLocalidad);
    	RadioGroup rdoEjes = (RadioGroup)findViewById(R.id.rdoEjes);
    	CheckBox chkFacebook = (CheckBox)findViewById(R.id.chkFacebook);
    	
    	if ( chkFacebook.isChecked() )
    	{
    		lastNombre = txtNombre.getText().toString();
    		lastLocalidad = txtLocalidad.getText().toString();
        	lastOtrodato = txtOtroDato.getText().toString();
        	lastMensaje = txtMensaje.getText().toString();
        	lastEje = ((RadioButton)findViewById(rdoEjes.getCheckedRadioButtonId())).getText().toString();
        	
        	writeCSV();
        	clearForm();
    	}
    	else
    	{
    		Toast.makeText(this, "Debe aceptar que su propuesta sea publicada", Toast.LENGTH_LONG).show();
    	}
		
    }
    
    private void clearForm()
    {
        EditText txtMensaje = (EditText)findViewById(R.id.txtMensaje);
        EditText txtOtroDato = (EditText)findViewById(R.id.txtOtroDato);
        EditText txtNombre = (EditText)findViewById(R.id.txtNombre);
        EditText txtLocalidad = (EditText)findViewById(R.id.txtLocalidad);
        CheckBox chkFacebook = (CheckBox)findViewById(R.id.chkFacebook);
        
        txtMensaje.setText("");
        txtOtroDato.setText("");
        txtNombre.setText("");
        txtLocalidad.setText("");
        chkFacebook.setChecked(false);  
        
        txtNombre.requestFocus();
        
        
        LinearLayout a = (LinearLayout)findViewById(R.id.mainWrapper);
        Log.d("SIZE", a.getWidth() + "x" + a.getHeight() );
    }
    
    public void irAForm()
    {
    	setContentView(R.layout.form);
    	
    	enCam = false;
    	
    	View backgroundColorView = (View)findViewById(R.id.backgroundColor);
	    Drawable backgroundColor = backgroundColorView.getBackground();
	    backgroundColor.setAlpha(190);
    }
    
    private void checkCSVExists()
    {
    	File file = new File(Environment.getExternalStorageDirectory() +  "/voslohaces/datos_" + numTablet + ".csv");
    	
    	try
    	{
    		if ( file.createNewFile() )
        	{
    			PrintWriter csvWriter = new  PrintWriter(new FileWriter(file,true));
    			csvWriter.print("Nombre,Email,Mensaje,Imagen");
    			csvWriter.print("\r\n");
    			csvWriter.close();
        	}
    		
    		
    	}
    	catch ( IOException e )
    	{
    		Log.e("FILE_ERROR", e.getMessage());
    	}
    }
    
    public void writeCSV()
    {
    	File file = new File(Environment.getExternalStorageDirectory() +  "/voslohaces/datos_" + numTablet + ".csv");
    	
    	try
    	{
	    	PrintWriter csvWriter = new  PrintWriter(new FileWriter(file,true));
			csvWriter.print(
			        '"' + lastNombre + '"' + ',' +
			        '"' + lastLocalidad + '"' + ',' +
			        '"' + lastEje + '"' + ',' +
			        '"' + lastOtrodato + '"' + ',' + 
			        '"' + lastMensaje + '"' 
			        );
			csvWriter.print("\r\n");
			csvWriter.close();
			Log.d("FILE", "written");
			Toast.makeText(this, "Propuesta guardada, muchas gracias.", Toast.LENGTH_LONG).show();
    	}
    	catch ( IOException e )
    	{
    		Log.e("FILE_ERROR", e.getMessage());
    	}
    }
    
    public Point getResolution()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point(display.getWidth(), display.getHeight());
        
        return size;
    }
}
