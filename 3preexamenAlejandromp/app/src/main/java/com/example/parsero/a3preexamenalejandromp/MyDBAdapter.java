package com.example.parsero.a3preexamenalejandromp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MyDBAdapter {
    // Definiciones y constantes
    private static final String DATABASE_NAME = "examen2.db";
    private static final String DATABASE_TABLE_ALUMNOS = "alumnos";
    private static final String DATABASE_TABLE_PROFESORES = "profesores";
    private static final String DATABASE_TABLE_ASIGNATURAS = "asignaturas";
    private static final int DATABASE_VERSION = 1;

    //Al crear la base de datos también le digo el la edad es un nuber , con lo que me dejara hacerle el order by
    private static final String DATABASE_CREATE_ALUMNOS = "CREATE TABLE " + DATABASE_TABLE_ALUMNOS + " (_id integer primary key autoincrement,nombre text,edad number,ciclo text,curso text);";
    private static final String DATABASE_CREATE_PROFESORES = "CREATE TABLE " + DATABASE_TABLE_PROFESORES + " (_id integer primary key autoincrement,nombre text,edad text,ciclo text,curso text,tutor text,despacho text);";
    //Creamos la tabla de asignaturas
    private static final String DATABASE_CREATE_ASIGNATURAS = "CREATE TABLE " + DATABASE_TABLE_ASIGNATURAS + " (_id integer primary key autoincrement,nombreAsignatura text,numeroAlumnos number);";
    //Por si eliminamos las bbdd
    private static final String DATABASE_DROP_ALUMNOS = "DROP TABLE IF EXISTS " + DATABASE_TABLE_ALUMNOS + ";";
    private static final String DATABASE_DROP_PROFESORES = "DROP TABLE IF EXISTS " + DATABASE_TABLE_PROFESORES + ";";
    private static final String DATABASE_DROP_ASIGNATURAS = "DROP TABLE IF EXISTS " + DATABASE_TABLE_PROFESORES + ";";


    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    //Preguntar:
    public MyDBAdapter(Context c) {
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        //OJO open();
    }

    //Este método por ejemplo trata de abrir la bbdd , si no existe llamará a onCreate
    //si existe ejecutará/abrirá la bbdd
    //Con lo que nos devuelve una base de datos nueva , actualizada o existente
    //Llamando a los métodos de MyDBHelper que hay abajo:
    public void open() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    private static class MyDbHelper extends SQLiteOpenHelper {

        //Nos obliga también a crear un constructor del padre:
        public MyDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //Método onCreate y onUpgrade se "crean" automáticamente ya que nos obliga a crearlo la interfaz de la que estamos extendiendo (SQLiteOpenHelper):
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_ALUMNOS);
            db.execSQL(DATABASE_CREATE_PROFESORES);
            db.execSQL(DATABASE_CREATE_ASIGNATURAS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP_ALUMNOS);
            db.execSQL(DATABASE_DROP_PROFESORES);
            db.execSQL(DATABASE_DROP_ASIGNATURAS);
            onCreate(db);
        }
    }


    //-------------------------------------EXAMEN-------------------------------
//Método para isnertar Asignaturas:
    public void insertarAsignaturas(String nombre, String numAlumnos) {
        //Creamos un nuevo registro de valores a insertar (Des esta manera nos deja insertarlos en la bbdd (Clave valor):
        Cursor cursor = db.query(DATABASE_TABLE_ASIGNATURAS, null, null, null, null, null, null);
        ContentValues newValues = new ContentValues();

        //Asignamos los valores de cada campo que nos vendrán por parámetro desde crearAsignatura:
        newValues.put("nombreAsignatura", nombre);
        newValues.put("numeroAlumnos", numAlumnos);


        //Lo insertamos en la tabla de alumnos:
        db.insert(DATABASE_TABLE_ASIGNATURAS, null, newValues);

    }

    //Método que te devuelve los alumnos que hay matriculados en x asignatura:

    //Método para hacer una consulta a al bbdd y traernos todos los Alumnos:
    public ArrayList<String> recuperarAlumnos() {
        ArrayList<String> alumnos = new ArrayList<String>();
        //Recuperamos en un cursor la consulta realizada:
        //Le añadimos en el order by "edad" con lo que nos ordenará lo que saque por este parametro de la abbdd
        //En selection es donde añadiriamos la sentencia WHERE , en este caso solo se mostrarán aquellos que sean mayores de 20 años y los ordenará por el campo que introduzamos en el order by
        Cursor cursor = db.query(DATABASE_TABLE_ALUMNOS, null, "edad > 20", null, null, null, "edad");

        //Recorremos el cursor y vamos a añadiendo al array list de alumnos cada uno de los alumnos
        //Nos desplazamos a la 1a posición del cursor y vamos iterando sobre el mismo:
        if (cursor != null && cursor.moveToFirst()) {
            do {
                alumnos.add(cursor.getString(1) + " \n " + cursor.getString(2) + " \n " + cursor.getString(3) + " \n " + cursor.getString(4));
            } while (cursor.moveToNext());
        }
        return alumnos;
    }

    public ArrayList<String> recuperarAlumnosAsignatura(String query) {
        //Lo que tenemos que sacar de la bbbdd es un array con todos los alumnos que coincidan con laasignatura introducidoa en el editText de consultasVarias3c por lo tanto:
        //Creamos un array list de Strings que lo iremos rellenando con el cursor que se encargará de pasar por la bbdd y recoger los datos:
        ArrayList<String> alumnosAsignatura = new ArrayList<String>();
        //String query lo que hace es recoger lo que añadimos en el editText para hacer la consulta con lo introducido en sqlite por lo tanto:
        String asignatura = query;

        //Creamos un cursor que es el que accederá a la bbdd y lo igualamos a la consulta sql
        Cursor cursorAlumnosPorAsignatura = db.query(DATABASE_TABLE_ASIGNATURAS, null, "nombreAsignatura=?", new String[]{asignatura}, null, null, null);

        //Cuando el cursor (Puntero) accede a la bbdd y va recuperando datos que los introducimod en el Array list , por lo tanto:
        //Nos desplazamos a la 1a posición y vamos iterando:
        if (cursorAlumnosPorAsignatura != null && cursorAlumnosPorAsignatura.moveToFirst()) {
            do {
                alumnosAsignatura.add(cursorAlumnosPorAsignatura.getString(1) + "En la asignatura introducida hay :" + cursorAlumnosPorAsignatura.getString(2));
            } while (cursorAlumnosPorAsignatura.moveToNext());
        }
        //Bueno y finalmente devolvemos el arraylist y lo mostraremos desde consultasVarias3c ---->
        return alumnosAsignatura;
    }
//---------------------------------FIN DE EXAMEN-----------------------------


    public void insertarAlumnoDBADAPTER(String nombre, String edad, String ciclo, String curso) {
        //Creamos un nuevo registro de valores a insertar (Des esta manera nos deja insertarlos en la bbdd (Clave valor):
        Cursor cursor = db.query(DATABASE_TABLE_ALUMNOS, null, null, null, null, null, "edad");
        ContentValues newValues = new ContentValues();

        //Asignamos los valores de cada campo que nos vendrán por parámetro desde crearAlumno3b:
        newValues.put("nombre", nombre);
        newValues.put("edad", edad);
        newValues.put("ciclo", ciclo);
        newValues.put("curso", curso);

        //Lo insertamos en la tabla de alumnos:
        db.insert(DATABASE_TABLE_ALUMNOS, null, newValues);

    }

    public void insertarProfesorDBADAPTER(String nombreProfesor, String edadProfesor, String cicloProfesor, String cursoProfesor, String tutorProfesor, String despachoProfesor) {
        //Creamos un nuevo registro de valores a insertar (Des esta manera nos deja insertarlos en la bbdd (Clave valor):
        ContentValues newValues = new ContentValues();

        //Asignamos los valores de cada campo que nos vendrán por parámetro desde crearAlumno3b:
        newValues.put("nombre", nombreProfesor);
        newValues.put("edad", edadProfesor);
        newValues.put("ciclo", cicloProfesor);
        newValues.put("curso", cursoProfesor);
        newValues.put("tutor", tutorProfesor);
        newValues.put("despacho", despachoProfesor);

        //Lo insertamos en la tabla de alumnos:
        db.insert(DATABASE_TABLE_PROFESORES, null, newValues);

    }


    public ArrayList<String> recuperarProfesores() {
        ArrayList<String> profesores = new ArrayList<String>();
        Cursor cursor = db.query(DATABASE_TABLE_PROFESORES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                profesores.add(cursor.getString(1) + " \n " + cursor.getString(2) + " \n " + cursor.getString(3) + " \n" + cursor.getString(4) + "\n" + cursor.getString(5) + " \n " + cursor.getString(6));
            } while (cursor.moveToNext());
        }
        return profesores;
    }

    //Método que devuelve con todos los datos
    public ArrayList<String> recuperarTodo() {
        ArrayList<String> todo = new ArrayList<String>();

        Cursor cursorProfesores = db.query(DATABASE_TABLE_PROFESORES, null, null, null, null, null, null);
        if (cursorProfesores != null && cursorProfesores.moveToFirst()) {
            do {
                todo.add("PROFESOR :" + " \n " + cursorProfesores.getString(1) + " \n " + cursorProfesores.getString(2) + " \n " + cursorProfesores.getString(3) + " \n" + cursorProfesores.getString(4) + "\n" + cursorProfesores.getString(5) + " \n " + cursorProfesores.getString(6) + " \n ");

            } while (cursorProfesores.moveToNext());
        }
        Cursor cursorAlumnos = db.query(DATABASE_TABLE_ALUMNOS, null, null, null, null, null, null);
        if (cursorAlumnos != null && cursorAlumnos.moveToFirst()) {
            do {
                todo.add("ALUMNOS :" + "\n " + cursorAlumnos.getString(1) + " \n " + cursorAlumnos.getString(2) + " \n " + cursorAlumnos.getString(3) + " \n " + cursorAlumnos.getString(4));
            } while (cursorAlumnos.moveToNext());
        }
        return todo;
    }

    //Método que devuelve con la sequencia WHERE ciclo igual a x
    public ArrayList<String> recuperarAlumnoCiclo(String query) {
        //Lo que tenemos que sacar de la bbbdd es un array con todos los alumnos que coincidan con el ciclo introducido en el editText de consultasVarias3c por lo tanto:
        //Creamos un array list de Strings que lo iremos rellenando con el cursor que se encargará de pasar por la bbdd y recoger los datos:
        ArrayList<String> alumnoPorCiclo = new ArrayList<String>();
        //String query lo que hace es recoger lo que añadimos en el editText para hacer la consulta con lo introducido en sqlite por lo tanto:
        String ciclo = query;

        //Creamos un cursor que es el que accederá a la bbdd y lo igualamos a la consulta sql
        Cursor cursorAlumnosPorCiclo = db.query(DATABASE_TABLE_ALUMNOS, null, "ciclo=?", new String[]{ciclo}, null, null, null);

        //Cuando el cursor (Puntero) accede a la bbdd y va recuperando datos que los introducimod en el Array list , por lo tanto:
        //Nos desplazamos a la 1a posición y vamos iterando:
        if (cursorAlumnosPorCiclo != null && cursorAlumnosPorCiclo.moveToFirst()) {
            do {
                alumnoPorCiclo.add(cursorAlumnosPorCiclo.getString(1) + " \n " + cursorAlumnosPorCiclo.getString(2) + " \n " + cursorAlumnosPorCiclo.getString(3) + " \n " + cursorAlumnosPorCiclo.getString(4));
            } while (cursorAlumnosPorCiclo.moveToNext());
        }
        //Bueno y finalmente devolvemos el arraylist y lo mostraremos desde consultasVarias3c ---->
        return alumnoPorCiclo;
    }

    //Método que devuelve con la sequencia WHERE curso igual igual a x
    public ArrayList<String> recuperarAlumnoCurso(String query) {
        ArrayList<String> alumnoPorCurso = new ArrayList<String>();
        String curso = query;
        Cursor cursorAlumnosPorCurso = db.query(DATABASE_TABLE_ALUMNOS, null, "curso=?", new String[]{curso}, null, null, null);

        if (cursorAlumnosPorCurso != null && cursorAlumnosPorCurso.moveToFirst()) {
            do {
                alumnoPorCurso.add("ALUMNOS :" + "\n " + cursorAlumnosPorCurso.getString(1) + " \n " + cursorAlumnosPorCurso.getString(2) + " \n " + cursorAlumnosPorCurso.getString(3) + " \n " + cursorAlumnosPorCurso.getString(4));
            } while (cursorAlumnosPorCurso.moveToNext());
        }
        return alumnoPorCurso;
    }

    //--------------------------!!PREEXAMEN ACCESO A DATOS:¡¡---------------------------------------
    public void eliminarAlumnoPorCiclo(String query) {
        String ciclo = query;
        db.delete(DATABASE_TABLE_ALUMNOS, "ciclo=?", new String[]{ciclo});
    }

    //Este método recibe 2 parámetros el nombre del alumno viejo , y el nombre del alumno nuevo a actualizar:
    public void cambioNombre(String nombreViejo, String nombreNuevo) {

        //String ACTUALIZAR_NOMBRE = "UPDATE "+DATABASE_TABLE_PROFESORES+ " SET nombre ='" +nombreNuevo +"' WHERE nombre ='" +nombreViejo + "';";
        //db.execSQL(ACTUALIZAR_NOMBRE);
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombreNuevo);
        db.update(DATABASE_TABLE_ALUMNOS, cv, "nombre =?", new String[]{nombreViejo});

    }
}