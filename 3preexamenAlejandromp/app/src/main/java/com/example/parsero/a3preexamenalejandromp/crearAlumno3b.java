package com.example.parsero.a3preexamenalejandromp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearAlumno3b extends AppCompatActivity implements View.OnClickListener {

    EditText etNombreAlumno, etEdadAlumno, etCicloAlumno, etCursoAlumno;
    Button btnCrearAlumno;

    //Creamos isntancia de la base de datos:
    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alumno3b);

        etNombreAlumno = (EditText) findViewById(R.id.etNombreAlumno);
        etEdadAlumno = (EditText) findViewById(R.id.etEdadAlumno);
        etCicloAlumno = (EditText) findViewById(R.id.etCicloAlumno);
        etCursoAlumno = (EditText) findViewById(R.id.etCursoAlumno);

        btnCrearAlumno = (Button) findViewById(R.id.btnCrearAlumno);
        btnCrearAlumno.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnCrearAlumno:
                insertarAlumno();
                break;
        }
    }

    public void insertarAlumno() {

        //Asignamos a estos Strings lo introducido por el editText:
        String nombreAlumno = etNombreAlumno.getText().toString();
        String edadAlumno = etEdadAlumno.getText().toString();
        String cicloAlumno = etCicloAlumno.getText().toString();
        String cursoAlumno = etCursoAlumno.getText().toString();

        //Comprobamos si hay algún campo vacio
        if (
                (nombreAlumno.compareTo("") != 0) &&
                        (edadAlumno.compareTo("") != 0) &&
                        (cicloAlumno.compareTo("") != 0) &&
                        (cursoAlumno.compareTo("") != 0))
        //Ahora si ningún editText de los anteriores esta vacio:
        {

            //Creamos un objeto con la clase MyDBAdapter:
            dbAdapter = new MyDBAdapter(getApplicationContext());

            //Abrinmos la bbdd:
            dbAdapter.open();

            //Intentamos hacer el insert pasándole los Strings anteriores que nos pide el método insertarAlumno desde MyDBAdapter:
            dbAdapter.insertarAlumnoDBADAPTER(
                    nombreAlumno,
                    edadAlumno,
                    cicloAlumno,
                    cursoAlumno);

            //Si el alumno se crea con éxito muestra el toast
            Toast creado = Toast.makeText(getApplicationContext(), "Alumno creado con exito", Toast.LENGTH_SHORT);
            creado.show();

        } else {
            //De lo contrario muestra este toast:
            Toast noCreado = Toast.makeText(getApplicationContext(), "Alumno no creado revisa los campos ", Toast.LENGTH_SHORT);
            noCreado.show();
        }
    }

}

