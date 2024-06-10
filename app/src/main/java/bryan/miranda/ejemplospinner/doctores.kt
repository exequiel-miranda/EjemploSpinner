package bryan.miranda.ejemplospinner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class doctores : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root = inflater.inflate(R.layout.fragment_doctores, container, false)

        val btnGuardarDoctor = root.findViewById<Button>(R.id.btnGuardarDoctor)
        val txtNombreDoctor = root.findViewById<EditText>(R.id.txtNombreDoctor)
        val txtEspecialidadDoctor = root.findViewById<EditText>(R.id.txtEspecialidadDoctor)
        val txtTelefonoDoctor = root.findViewById<EditText>(R.id.txtTelefonoDoctor)

        //////////////TODO: guardar datos de doctores//////////////////
        //2- Programar el boton
        btnGuardarDoctor.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                //Guardar datos
                //1- Creo un objeto de la clase conexion
                val claseC = ClaseConexion().cadenaConexion()

                //2- creo una variable que contenga un PrepareStatement
                val addProducto =
                    claseC?.prepareStatement("insert into tbDoctores(DoctorUUID, nombreDoctor, Especialidad, Telefono) values(?, ?, ?, ?)")!!
                addProducto.setString(1, UUID.randomUUID().toString())
                addProducto.setString(2, txtNombreDoctor.text.toString())
                addProducto.setString(3, txtEspecialidadDoctor.text.toString())
                addProducto.setString(4, txtTelefonoDoctor.text.toString())
                addProducto.executeUpdate()

                //Abro una corrutina para mostrar una alerta y limpiar los campos
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Datos guardados", Toast.LENGTH_SHORT).show()
                    txtNombreDoctor.setText("")
                    txtEspecialidadDoctor.setText("")
                    txtTelefonoDoctor.setText("")
                }
            }
        }

        return root
    }
}