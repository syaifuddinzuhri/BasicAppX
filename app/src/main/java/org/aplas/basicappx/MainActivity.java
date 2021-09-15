package org.aplas.basicappx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Distance dist = new Distance();
    private Weight weight = new Weight();
    private Temperature temp = new Temperature();
    private Button convertBtn;
    private EditText inputTxt;
    private EditText outputTxt;
    private Spinner unitOri;
    private Spinner unitConv;
    private RadioGroup unitType;
    private CheckBox roundBox;
    private CheckBox formBox;
    private ImageView imgView;
    private ImageView imgFormula;
    private AlertDialog startDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        convertBtn = (Button) findViewById(R.id.convertButton);
        inputTxt = (EditText) findViewById(R.id.inputText);
        outputTxt = (EditText) findViewById(R.id.outputText);
        unitOri = (Spinner) findViewById(R.id.oriList);
        unitConv = (Spinner) findViewById(R.id.convList);
        unitType = (RadioGroup) findViewById(R.id.radioGroup);
        roundBox = (CheckBox) findViewById(R.id.chkRounded);
        formBox = (CheckBox) findViewById(R.id.chkFormula);
        imgView = (ImageView) findViewById(R.id.img);
        imgFormula = (ImageView) findViewById(R.id.imgFormula);

        unitType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                if(radioButton.getText().toString().equals("Distance")){
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(unitType.getContext(), R.array.distList, android.R.layout.simple_spinner_item);
                    imgView.setImageResource(R.drawable.distance);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitOri.setAdapter(adapter);
                    unitConv.setAdapter(adapter);
                } else if(radioButton.getText().toString().equals("Weight")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(unitType.getContext(), R.array.weightList, android.R.layout.simple_spinner_item);
                    imgView.setImageResource(R.drawable.weight);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitOri.setAdapter(adapter);
                    unitConv.setAdapter(adapter);
                } else if(radioButton.getText().toString().equals("Temperature")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(unitType.getContext(), R.array.tempList, android.R.layout.simple_spinner_item);
                    imgView.setImageResource(R.drawable.temperature);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    unitOri.setAdapter(adapter);
                    unitConv.setAdapter(adapter);
                }
                inputTxt.setText("0");
                outputTxt.setText("0");
            }
        });

        convertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doConvert();
            }
        });

        unitOri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doConvert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        unitConv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                doConvert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        roundBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                doConvert();
            }
        });

        formBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    imgFormula.setVisibility(View.VISIBLE);
                } else {
                    imgFormula.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startDialog = new AlertDialog.Builder(MainActivity.this).create();
        startDialog.setTitle("Application started");
        startDialog.setMessage("This app can use to convert any units");
        startDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }
        );
        startDialog.show();
    }

    protected void doConvert() {
        RadioButton radioButton = (RadioButton) findViewById(unitType.getCheckedRadioButtonId());
        double value = this.convertUnit(radioButton.getText().toString(), unitOri.getSelectedItem().toString(), unitConv.getSelectedItem().toString(), Double.parseDouble(inputTxt.getText().toString()));
        String res = this.strResult(value, true);
        outputTxt.setText(res);
    }

    protected double convertUnit(String type, String oriUnit, String convUnit, double value){
        double result;
        switch (type) {
            case "Temperature":
                result = temp.convert(oriUnit, convUnit, value);
                break;
            case "Distance":
                result = dist.convert(oriUnit, convUnit, value);
                break;
            case "Weight":
                result = weight.convert(oriUnit, convUnit, value);
                break;
            default:
                result = value;
                break;
        }
        return result;
    }

    protected String strResult(double val, boolean rounded){
        String result;
        if(rounded){
            result = String.valueOf((double) Math.round(val * 100) / 100);
        } else {
            result = String.valueOf((double) Math.round(val * 100000) / 100000);
        }
        return result;
    }
}