package teste.vederi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
import android.widget.CheckBox;
import android.widget.RadioGroup;

import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
*/ 
 
public class Vederi extends Activity 
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vederi);
 
        //---Button view---
        Button btnSelectieRuta= (Button) findViewById(R.id.widget37);
        btnSelectieRuta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), 
                        "Ati accesat butonul de Selectie Ruta", 
                        Toast.LENGTH_SHORT).show();
            }
        });
 
        Button btnSelectieClient = (Button) findViewById(R.id.widget38);
        btnSelectieClient.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) {
                DisplayToast("Ati accesat butonul Selectie Client");
            }
        });
 
        
        Button btnCautaProdus = (Button) findViewById(R.id.widget39);
        btnCautaProdus.setOnClickListener(new View.OnClickListener() 
        {
            public void onClick(View v) {
               
                    DisplayToast("Ati accesat butonul Cauta Produs");            
           }
        });
 
        
        Button btnComenziNetrimise = (Button) findViewById(R.id.widget40);        
        btnComenziNetrimise.setOnClickListener(new View.OnClickListener () 
        {
            public void onClick(View v) {
                
                DisplayToast("Ati accesat butonul Comenzi Netrimise");
            }
        });
 
        
       Button btnVizualizareSarcini = (Button) findViewById(R.id.widget41);
       btnVizualizareSarcini.setOnClickListener(new View.OnClickListener() 
       {
            public void onClick(View v) {
              
                   DisplayToast("Ati accesat butonul Vizualizare Sarcini ");
            }
        });
    }
 
    private void DisplayToast(String msg)
    {
         Toast.makeText(getBaseContext(), msg, 
                 Toast.LENGTH_SHORT).show();        
    }    
}