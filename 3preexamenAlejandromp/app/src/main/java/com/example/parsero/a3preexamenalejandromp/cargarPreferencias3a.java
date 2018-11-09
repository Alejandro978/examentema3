package com.example.parsero.a3preexamenalejandromp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class cargarPreferencias3a extends AppCompatActivity {

    TextView txtNombre,txtNombreUsuario,txtFechaNacimiento,txtSexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_preferencias3a);

        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtNombreUsuario = (TextView) findViewById(R.id.txtNombreUsuario);
        txtFechaNacimiento = (TextView) findViewById(R.id.txtFechaNacimiento);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        cargarPreferencias();
    }

    public void cargarPreferencias() {
        //Recojo las preferencias que he pasado antes desde el otro activity:
        SharedPreferences preferenciasUsuario = getSharedPreferences("preferenciasUsuario", Context.MODE_PRIVATE);
        String nombre = preferenciasUsuario.getString("nombre", "0");
        String nombreUsuario = preferenciasUsuario.getString("nombreUsuario", "0");
        String fechaNacimiento = preferenciasUsuario.getString("fechaNacimiento", "0");
        String sexo = preferenciasUsuario.getString("sexo", "0");

        //Se las asigno a distintos TextView que tenemos en este activity:
        txtNombre.setText("El nombre es:"+nombre);
        txtNombreUsuario.setText("El nombre de usuario es es:"+nombreUsuario);
        txtFechaNacimiento.setText("La fecha de nacimiento es:"+fechaNacimiento);
        txtSexo.setText("El sexo es:"+sexo);
    }
}
