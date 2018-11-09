package com.example.parsero.a3preexamenalejandromp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class crearProfesor3b extends AppCompatActivity implements View.OnClickListener {
    EditText etNombreProfesor, etEdadProfesor, etCicloProfesor, etCursoProfesor, etTutorProfesor, etDespachoProfesor;
    Button btnCrear;
    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_profesor3b);
        etNombreProfesor = (EditText) findViewById(R.id.etNombreProfesor);
        etEdadProfesor = (EditText) findViewById(R.id.etEdadProfesor);
        etCicloProfesor = (EditText) findViewById(R.id.etCicloProfesor);
        etCursoProfesor = (EditText) findViewById(R.id.etCursoProfesor);
        etTutorProfesor = (EditText) findViewById(R.id.etTutorProfesor);
        etDespachoProfesor = (EditText) findViewById(R.id.etDespachoProfesor);
        btnCrear = (Button) findViewById(R.id.btnCrearProfesor);
        btnCrear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnCrearProfesor:
                insertarProfesor();
                break;
        }
    }

    public void insertarProfesor() {
        String nombreProfesor = etNombreProfesor.getText().toString();
        String edadProfesor = etEdadProfesor.getText().toString();
        String cicloProfesor = etCicloProfesor.getText().toString();
        String cursoProfesor = etCursoProfesor.getText().toString();
        String tutorProfesor = etTutorProfesor.getText().toString();
        String despachoProfesor = etDespachoProfesor.getText().toString();

        if (
                (nombreProfesor.compareTo("") != 0) &&
                        (edadProfesor.compareTo("") != 0) &&
                        (cicloProfesor.compareTo("") != 0) &&
                        (cursoProfesor.compareTo("") != 0) &&
                        (tutorProfesor.compareTo("") != 0) &&
                        (despachoProfesor.compareTo("") != 0)) {


            dbAdapter = new MyDBAdapter(getApplicationContext());
            dbAdapter.open();
            dbAdapter.insertarProfesorDBADAPTER(
                    nombreProfesor,
                    edadProfesor,
                    cicloProfesor,
                    cursoProfesor,
                    tutorProfesor,
                    despachoProfesor);

            Toast creado = Toast.makeText(getApplicationContext(), "Profesor creado con exito", Toast.LENGTH_SHORT);
            creado.show();

        } else {

            Toast noCreado = Toast.makeText(getApplicationContext(), "Profesor no creado , revisa los campos", Toast.LENGTH_SHORT);
            noCreado.show();

        }
    }

}

