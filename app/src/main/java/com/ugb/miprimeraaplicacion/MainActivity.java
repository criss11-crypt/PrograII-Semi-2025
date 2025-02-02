package com.ugb.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText txtNum1, txtNum2;
    TextView lblRespuesta;
    RadioGroup rgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnCalcular);
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        lblRespuesta = findViewById(R.id.lblRespuesta);
        rgo = findViewById(R.id.rgoOpciones);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double num1 = Double.parseDouble(txtNum1.getText().toString());
                double num2 = Double.parseDouble(txtNum2.getText().toString());
                double respuesta = 0;

                int selectedId = rgo.getCheckedRadioButtonId(); // Obtener el ID del RadioButton seleccionado

                if (selectedId == R.id.optSuma) {
                    respuesta = num1 + num2;
                } else if (selectedId == R.id.optResta) {
                    respuesta = num1 - num2;
                } else if (selectedId == R.id.optMultiplicacion) {
                    respuesta = num1 * num2;
                } else if (selectedId == R.id.optDivision) {
                    respuesta = num1 / num2;
                } else if (selectedId == R.id.optExponenciacion) {
                    respuesta = Math.pow(num1, num2);
                } else if (selectedId == R.id.optPorcentaje) {
                    respuesta = (num1 * num2) / 100;
                } else if (selectedId == R.id.optRaiz) {
                    respuesta = Math.sqrt(num1);
                } else if (selectedId == R.id.optFactorial) {
                    // Calcular el factorial de num1
                    respuesta = 1; // Inicializar la variable respuesta
                    for (int i = 1; i <= num1; i++) {
                        respuesta *= i; // Multiplicar respuesta por i
                    }
                    // Opcional: Manejar el caso en que no haya ninguna opción seleccionada
                    respuesta = 0;
                }

                lblRespuesta.setText("Respuesta: " + respuesta);
            }
        });
    }
}