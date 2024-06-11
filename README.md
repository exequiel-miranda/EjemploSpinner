Esta aplicación es para realizar la práctica del spinner
Esta aplicacion aun no tiene programdo el Spinner y en claes lo vamos a programar
</br></br>

CREATE TABLE tbDoctores (</br>
    DoctorUUID VARCHAR2(50) PRIMARY KEY,</br>
    nombreDoctor VARCHAR2(50),</br>
    Especialidad VARCHAR2(50),</br>
    Telefono VARCHAR2(20)</br>
);
</br></br>
CREATE TABLE tbPacientes (</br>
    PacienteUUID VARCHAR2(50) PRIMARY KEY,</br>
    DoctorUUID VARCHAR2(50),</br>
    Nombre VARCHAR2(50),</br>
    FechaNacimiento VARCHAR2(50),</br>
    Direccion VARCHAR2(200),</br>
    FOREIGN KEY (DoctorUUID) REFERENCES tbDoctores(DoctorUUID)</br>
);</br>
</br>
select * from tbDoctores;</br>
select * from tbPacientes;</br>
