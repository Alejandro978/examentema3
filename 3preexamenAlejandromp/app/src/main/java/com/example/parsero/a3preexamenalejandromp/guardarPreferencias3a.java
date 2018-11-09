package com.example.parsero.a3preexamenalejandromp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class guardarPreferencias3a extends AppCompatActivity implements View.OnClickListener {

    EditText etNombre, etNombreUsuario, etFechaNacimiento;
    CheckBox cbHombre, cbMujer;
    Button btnGuardar, btnToPreferenciasUsuario;

    String sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_preferencias3a);

        etNombre = (EditText) findViewById(R.id.etNombre);
        etNombreUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimiento);
        cbHombre = (CheckBox) findViewById(R.id.cbHombre);
        cbMujer = (CheckBox) findViewById(R.id.cbMujer);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);

        btnToPreferenciasUsuario = (Button) findViewById(R.id.btnToPreferenciasUsuario);
        btnToPreferenciasUsuario.setOnClickListener(this);

        sexo = "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnGuardar:
                btnGuardarPreferencias();
                break;

            case R.id.btnToPreferenciasUsuario:
                ToPreferenciasUsuario();
                break;


        }
    }

    public void ToPreferenciasUsuario() {

        Intent i = new Intent(guardarPreferencias3a.this, cargarPreferencias3a.class);
        startActivity(i);

    }

    public void btnGuardarPreferencias() {
        boolean guardado = false;

//Declaramos las preferencias y cual será su clave, para pasarselo luego al otro java con su xml que las mostrará , en este caso preferenciasUsuario que se pasará en modo privado
        SharedPreferences preferenciasUsuario = getSharedPreferences("preferenciasUsuario", MODE_PRIVATE);

        //Le pasamos a los distintos Strings lo introducido en los EditText

        String nombre = etNombre.getText().toString();
        String nombreUsuario = etNombreUsuario.getText().toString();
        String fechaNacimiento = etFechaNacimiento.getText().toString();

        //Esto es un booleano para dependiendo si está seleccionado hombre o mujer asignarle a un String el dato seleccionado

        if (cbHombre.isChecked()) {
            sexo = "Hombre";
        } else {
            sexo = "Mujer";
        }

        //Creamos un editor para poder pasarle los paretros a shared preferences
        SharedPreferences.Editor editor = preferenciasUsuario.edit();

        //Aquí guardamos con clave valor lo introducido anteriormente en SharedPreferencies
        editor.putString("nombre", nombre);
        editor.putString("nombreUsuario", nombreUsuario);
        editor.putString("fechaNacimiento", fechaNacimiento);
        editor.putString("sexo", sexo);

        //Aquí simplemente comprobamos si los campos están rellenos
        if (
                (nombre.compareTo("") != 0) &&
                        (nombreUsuario.compareTo("") != 0) &&
                        (fechaNacimiento.compareTo("") != 0) &&
                        (sexo.compareTo("") != 0)) {
            guardado = true;
        }

        //Si están rellenos guardado será true y hará un editor.comit() para terminar de pasarle los parámetros a SharedPreferences
        if (guardado) {
            Toast.makeText(guardarPreferencias3a.this, "Usuario guardado con exito", Toast.LENGTH_SHORT).show();
            editor.commit();
        } else {
            Toast.makeText(guardarPreferencias3a.this, "No se ha guardado con exito , revisa los campos", Toast.LENGTH_SHORT).show();
        }
    }


}