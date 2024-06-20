package bryan.miranda.ejemplospinner

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassDoctores
import java.util.Calendar
import java.util.UUID

class pacientes : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_pacientes, container, false)

        //1- Mandar a los elementos de la vista
        val spDoctores = root.findViewById<Spinner>(R.id.spDoctores)
        val txtFechaNacimiento = root.findViewById<EditText>(R.id.txtFechaNacimiento)
        val txtNombrePaciente = root.findViewById<EditText>(R.id.txtNombrePaciente)
        val txtDireccionPaciente = root.findViewById<EditText>(R.id.txtDireccionPaciente)
        val btnGuardarPaciente = root.findViewById<Button>(R.id.btnGuardarPaciente)


        //Funcion para hacer el select de los nombres de los doctores
        fun obtenerDoctores(): List<dataClassDoctores>{
            val objConexion = ClaseConexion().cadenaConexion()

            //Creo un Statement que me ejecutará el select
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbDoctores")!!

            val listadoDoctores = mutableListOf<dataClassDoctores>()

            while (resultSet.next()){
                val uuid = resultSet.getString("DoctorUUID")
                val nombre = resultSet.getString("nombreDoctor")
                val especialidad = resultSet.getString("especialidad")
                val telefono = resultSet.getString("telefono")
                val unDoctorCompleto = dataClassDoctores(uuid, nombre, especialidad, telefono)
                listadoDoctores.add(unDoctorCompleto)
            }
            return listadoDoctores
        }

        //Programar el spinner para que me muestre los datos del select
        CoroutineScope(Dispatchers.IO).launch {
            //1- Obtengo los datos
            val listadoDeDoctores = obtenerDoctores()
            val nombreDoctores = listadoDeDoctores.map { it.nombreDoctor }

            withContext(Dispatchers.Main)  {
                //2- Crear y modificar el adaptador
                val miAdaptador = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, nombreDoctores)
                spDoctores.adapter = miAdaptador
            }
        }

//Mostrar el calendario al hacer click en el EditText txtFechaNacimientoPaciente
        txtFechaNacimiento.setOnClickListener {
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtFechaNacimiento.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }



        //Programemos que se guarde mi paciente
        btnGuardarPaciente.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //1- creo un obj de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                val doctor = obtenerDoctores()

                //2- Creo una variable que contenga un PrepareStatement
                val addPaciente = objConexion?.prepareStatement("insert into tbPacientes(pacienteUUID, doctorUUID, Nombre, fechaNacimiento, direccion) values(?, ?, ?, ?, ?)")!!
                addPaciente.setString(1, UUID.randomUUID().toString())
                addPaciente.setString(2, doctor[spDoctores.selectedItemPosition].DoctorUUID)
                addPaciente.setString(3, txtNombrePaciente.text.toString())
                addPaciente.setString(4, txtFechaNacimiento.text.toString())
                addPaciente.setString(5, txtDireccionPaciente.text.toString())
                addPaciente.executeUpdate()

                withContext(Dispatchers.Main){
                    txtNombrePaciente.setText("")
                    txtDireccionPaciente.setText("")
                    txtFechaNacimiento.setText("")
                    Toast.makeText(requireContext(), "paciente agregado", Toast.LENGTH_SHORT).show()
                }

            }

        }


        return root
    }
}