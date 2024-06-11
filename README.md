Esta aplicación es para realizar la práctica del spinner
Esta aplicacion aun no tiene programdo el Spinner y en claes lo vamos a programar


CREATE TABLE tbDoctores (
    DoctorUUID VARCHAR2(50) PRIMARY KEY,
    nombreDoctor VARCHAR2(50),
    Especialidad VARCHAR2(50),
    Telefono VARCHAR2(20)
);

CREATE TABLE tbPacientes (
    PacienteUUID VARCHAR2(50) PRIMARY KEY,
    DoctorUUID VARCHAR2(50),
    Nombre VARCHAR2(50),
    FechaNacimiento VARCHAR2(50),
    Direccion VARCHAR2(200),
    FOREIGN KEY (DoctorUUID) REFERENCES tbDoctores(DoctorUUID)
);

select * from tbDoctores;
select * from tbPacientes;
