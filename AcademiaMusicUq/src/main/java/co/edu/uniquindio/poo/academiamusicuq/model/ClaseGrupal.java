package co.edu.uniquindio.poo.academiamusicuq.model;

import java.util.ArrayList;
import java.util.List;

public class ClaseGrupal extends Clase{

    private int cupoMaximo;
    private List<Estudiante> listEstudiantes;

    public ClaseGrupal(int id,String fecha,Aula aula,String tipo,Profesor profesor,BloqueHorario horario,int cupoMaximo){
        super(id,fecha,aula,tipo,profesor,horario);

        this.cupoMaximo = cupoMaximo;
        this.listEstudiantes = new ArrayList<>();

    }

    public List<Estudiante> getListEstudiantes() {
        return listEstudiantes;
    }

    public void setListEstudiantes(List<Estudiante> listEstudiantes) {
        this.listEstudiantes = listEstudiantes;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
}
