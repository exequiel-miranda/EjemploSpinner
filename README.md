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
select * from tbPacientes;</br></br>


Ejemplo para colocar un calendario:</br></br>
  // Mostrar el calendario al hacer click en el EditText txtFechaNacimientoPaciente

        txtFechaNacimiento.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)

            // Calcular la fecha máxima (hace 18 años a partir de hoy)
            val fechaMaxima = Calendar.getInstance()
            fechaMaxima.set(anio - 18, mes, dia)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtFechaNacimiento.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )

            // Configurar la fecha máxima a hace 18 años a partir de hoy
            datePickerDialog.datePicker.maxDate = fechaMaxima.timeInMillis

            datePickerDialog.show()
        }

