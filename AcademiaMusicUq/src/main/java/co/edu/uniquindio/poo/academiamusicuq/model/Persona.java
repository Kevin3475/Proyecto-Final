package co.edu.uniquindio.poo.academiamusicuq.model;

public abstract class  Persona {
    protected String id;
    protected String nombre;
    protected String apellido;
    protected String documento;
    protected String email;
    protected String telefono;

    public Persona(String id,String nombre, String apellido,String documento, String email, String telefono ){
        this.id=id;
        this.nombre = nombre;
        this.apellido=apellido;
        this.documento=documento;
        this.email=email;
        this.telefono=telefono;

    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre (String nombre){
        this.nombre=nombre;
    }
    public String getApellido(){
        return apellido;
    }
    public void setApellido(String Apellido){
        this.apellido=apellido;
    }
    public String getDocumento(){
        return documento;
    }
    public void setDocumento(String documento){
        this.documento=documento;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String Email){
        this.email=email;
    }
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
