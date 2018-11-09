package com.example.parsero.a3preexamenalejandromp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class consultasPreexamen extends AppCompatActivity implements View.OnClickListener {
    TextView texto;
    EditText et_filter, etViejoNombreAlumno, etNuevoNombreAlumno;
    Button btnEliminarPorCiclo, btnMostrarAlumnos, btnUpdateNombreAlumno;
    private MyDBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_preexamen);
        texto = (TextView) findViewById(R.id.texto);
        et_filter = (EditText) findViewById(R.id.et_filter);
        etViejoNombreAlumno = (EditText) findViewById(R.id.etViejoNombreAlumno);
        etNuevoNombreAlumno = (EditText) findViewById(R.id.etNuevoNombreAlumno);

        btnEliminarPorCiclo = (Button) findViewById(R.id.btnEliminarPorCiclo);
        btnEliminarPorCiclo.setOnClickListener(this);

        btnMostrarAlumnos = (Button) findViewById(R.id.btnMostrarAlumnos);
        btnMostrarAlumnos.setOnClickListener(this);

        btnUpdateNombreAlumno = (Button) findViewById(R.id.btnUpdateNombreAlumno);
        btnUpdateNombreAlumno.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnEliminarPorCiclo:
                eliminarAlumnoPorCiclo();
                break;

            case R.id.btnMostrarAlumnos:
                mostrarAlumnos();
                break;

            case R.id.btnUpdateNombreAlumno:
                actualizarNombre();
                break;

        }
    }

    public void mostrarAlumnos() {
        dbAdapter = new MyDBAdapter(getApplicationContext());
        dbAdapter.open();
        ArrayList<String> alumnos = dbAdapter.recuperarAlumnos();

        for (int i = 0; i < alumnos.size(); i++) {
            texto.setText(texto.getText() + "\n" + alumnos.get(i));
        }
    }


    //Este método elimina un alumno por el ciclo que introduzcamos en el edit text principal
    public void eliminarAlumnoPorCiclo() {
        dbAdapter = new MyDBAdapter(getApplicationContext());
        dbAdapter.open();
        String query = et_filter.getText().toString();
        dbAdapter.eliminarAlumnoPorCiclo(query);
    }

    //Este método actualiza el nombre del Usuario.nombre que introduzcamos por String
    public void actualizarNombre() {
        String nombreViejo = etViejoNombreAlumno.getText().toString();
        String nombreNuevo = etNuevoNombreAlumno.getText().toString();
        dbAdapter.cambioNombre(nombreViejo,nombreNuevo);
    }
    //Prueba sourceTree
}



