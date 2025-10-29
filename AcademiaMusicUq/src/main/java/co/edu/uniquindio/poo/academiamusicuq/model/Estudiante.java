package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.List;

public class Estudiante extends Persona implements IReportable{

    private Nivel nivel;
    private List<Curso> listCursos;

    public Estudiante (String id,String nombre,String apellido,String documento,String email,String telefono,Nivel nivel){

        super(id,nombre,apellido,documento,email,telefono);

        this.nivel = nivel;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Curso> getListCursos() {
        return listCursos;
    }

    public void setListCursos(List<Curso> listCursos) {
        this.listCursos = listCursos;
    }
}
