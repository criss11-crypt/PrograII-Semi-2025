package com.ugb.miprimeraaplicacion;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

    private Spinner unidadEntradaSpinner;
    private Spinner unidadSalidaSpinner;
    private EditText valorEntradaEditText;
    private TextView resultadoConversionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab1");
        tab1.setIndicator("Consumo");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab2");
        tab2.setIndicator("Conversor");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        Button calcularButton = findViewById(R.id.calcularButton);
        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText consumoInput = findViewById(R.id.consumoInput);
                TextView resultadoTextView = findViewById(R.id.resultadoTextView);

                int metrosConsumidos = Integer.parseInt(consumoInput.getText().toString());
                double valorAPagar = calcularValor(metrosConsumidos);
                resultadoTextView.setText("Valor a pagar: $" + valorAPagar);
            }
        });

        unidadEntradaSpinner = findViewById(R.id.unidadEntradaSpinner);
        unidadSalidaSpinner = findViewById(R.id.unidadSalidaSpinner);
        valorEntradaEditText = findViewById(R.id.valorEntradaEditText);
        resultadoConversionTextView = findViewById(R.id.resultadoConversionTextView);

        String[] unidades = {"Pie Cuadrado", "Vara Cuadrada", "Yarda Cuadrada", "Metro Cuadrado", "Tareas", "Manzana", "Hectárea"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unidadEntradaSpinner.setAdapter(adapter);
        unidadSalidaSpinner.setAdapter(adapter);

        Button convertirButton = findViewById(R.id.convertirButton);
        convertirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirArea();
            }
        });
    }

    private double calcularValor(int metrosConsumidos) {
        double cuotaFija = 6.0;
        if (metrosConsumidos <= 18) {
            return cuotaFija;
        } else if (metrosConsumidos <= 28) {
            return cuotaFija + (metrosConsumidos - 18) * 0.45;
        } else {
            return cuotaFija + 10 * 0.45 + (metrosConsumidos - 28) * 0.65;
        }
    }

    private void convertirArea() {
        String unidadEntrada = unidadEntradaSpinner.getSelectedItem().toString();
        String unidadSalida = unidadSalidaSpinner.getSelectedItem().toString();
        double valorEntrada = Double.parseDouble(valorEntradaEditText.getText().toString());

        double valorEnMetrosCuadrados = convertirAMetrosCuadrados(valorEntrada, unidadEntrada);
        double valorConvertido = convertirDesdeMetrosCuadrados(valorEnMetrosCuadrados, unidadSalida);

        resultadoConversionTextView.setText(String.format("%.2f %s", valorConvertido, unidadSalida));
    }

    private double convertirAMetrosCuadrados(double valor, String unidad) {
        switch (unidad) {
            case "Pie Cuadrado":
                return valor * 0.092903;
            case "Vara Cuadrada":
                return valor * 0.6987;
            case "Yarda Cuadrada":
                return valor * 0.836127;
            case "Metro Cuadrado":
                return valor;
            case "Tareas":
                return valor * 437.5;
            case "Manzana":
                return valor * 7000;
            case "Hectárea":
                return valor * 10000;
            default:
                return 0;
        }
    }

    private double convertirDesdeMetrosCuadrados(double valor, String unidad) {
        switch (unidad) {
            case "Pie Cuadrado":
                return valor / 0.092903;
            case "Vara Cuadrada":
                return valor / 0.6987;
            case "Yarda Cuadrada":
                return valor / 0.836127;
            case "Metro Cuadrado":
                return valor;
            case "Tareas":
                return valor / 437.5;
            case "Manzana":
                return valor / 7000;
            case "Hectárea":
                return valor / 10000;
            default:
                return 0;
        }
    }

    ;
}