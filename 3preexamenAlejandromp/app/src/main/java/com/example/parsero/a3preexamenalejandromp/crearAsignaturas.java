package com.example.parsero.a3preexamenalejandromp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearAsignaturas extends AppCompatActivity implements View.OnClickListener {

    EditText etNombreAsignatura, etAlumnosMatriculados;
    Button btnCrearAsignatura;

    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_asignaturas);

        etNombreAsignatura = (EditText) findViewById(R.id.etNombreAsignatura);
        etAlumnosMatriculados = (EditText) findViewById(R.id.etAlumnosMatriculados);
        btnCrearAsignatura = (Button) findViewById(R.id.btnCrearAsignatura);
        btnCrearAsignatura.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCrearAsignatura:
                insertarAsignatura();
                break;
        }
    }

    public void insertarAsignatura() {

        //Asignamos a estos Strings lo introducido por el editText:
        String nombreAsignatura = etNombreAsignatura.getText().toString();
        String numeroAlumnos = etAlumnosMatriculados.getText().toString();


        //Comprobamos si hay algún campo vacio
        if (
                        (nombreAsignatura.compareTo("") != 0) &&
                        (numeroAlumnos.compareTo("") != 0))
        //Ahora si ningún editText de los anteriores esta vacio:
        {

            //Creamos un objeto con la clase MyDBAdapter:
            dbAdapter = new MyDBAdapter(getApplicationContext());

            //Abrinmos la bbdd:
            dbAdapter.open();

            //Intentamos hacer el insert pasándole los Strings anteriores que nos pide el método insertarAlumno desde MyDBAdapter:
            dbAdapter.insertarAsignaturas(
                    nombreAsignatura,
                    numeroAlumnos);

            //Si el alumno se crea con éxito muestra el toast
            Toast creado = Toast.makeText(getApplicationContext(), "Asignatura creado con exito", Toast.LENGTH_SHORT);
            creado.show();

        } else {
            //De lo contrario muestra este toast:
            Toast noCreado = Toast.makeText(getApplicationContext(), "Asignatura no creada revisa los campos ", Toast.LENGTH_SHORT);
            noCreado.show();
        }
    }

}
