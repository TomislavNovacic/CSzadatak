package com.example.android.cszadatak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomi on 2.7.2017..
 */

public class EnterParamsActivity extends AppCompatActivity {

    EditText brojStupaca;
    EditText brojRedaka;
    LinearLayout linearLayout;
    Button potvrdi;
    LinearLayout layout;
    LinearLayout enter_form;
    Button kreirajGrid;
    ArrayList<EditText> EdsStupci;
    ArrayList<EditText> EdsRedci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_params);

        brojStupaca = (EditText) findViewById(R.id.columnsNum);
        brojRedaka = (EditText) findViewById(R.id.rowsNum);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_params);
        potvrdi = (Button) findViewById(R.id.btnConfirm);
        enter_form = (LinearLayout) findViewById(R.id.enter_form);

        potvrdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText brojRedaka = (EditText) findViewById(R.id.rowsNum);
                EditText brojStupaca = (EditText) findViewById(R.id.columnsNum);
                String redci = brojRedaka.getText().toString();
                String stupci = brojStupaca.getText().toString();

                if(TextUtils.isEmpty(redci)) {
                    brojRedaka.setError("Ovo polje ne može biti prazno.");
                    return;
                }

                if(TextUtils.isEmpty(stupci)) {
                    brojStupaca.setError("Ovo polje ne može biti prazno.");
                    return;
                }

                if (enter_form != null) {
                    enter_form.removeAllViewsInLayout();
                }

                EdsStupci = new ArrayList<EditText>();
                EdsRedci = new ArrayList<EditText>();

                for (int i = 1; i < Integer.parseInt(brojRedaka.getText().toString()) + 1 ; i++) {
                    layout = new LinearLayout(EnterParamsActivity.this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    TextView vrijednost1_1 = new TextView(EnterParamsActivity.this);
                    vrijednost1_1.setText(i + ". redak");
                    vrijednost1_1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vrijednost1_1.getLayoutParams();
                    params.rightMargin = 30;
                    vrijednost1_1.setLayoutParams(params);
                    EditText vrijednost_1 = new EditText(EnterParamsActivity.this);
                    EdsRedci.add(vrijednost_1);
                    vrijednost_1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                    vrijednost_1.setGravity(Gravity.CENTER);
                    vrijednost_1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout.addView(vrijednost1_1);
                    layout.addView(vrijednost_1);
                    enter_form.addView(layout);
                }

                for (int i = 1; i < Integer.parseInt(brojStupaca.getText().toString()) + 1; i++) {
                    layout = new LinearLayout(EnterParamsActivity.this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    TextView vrijednost1 = new TextView(EnterParamsActivity.this);
                    vrijednost1.setText(i + ". stupac");
                    vrijednost1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) vrijednost1.getLayoutParams();
                    params2.rightMargin = 30;
                    vrijednost1.setLayoutParams(params2);
                    EditText vrijednost = new EditText(EnterParamsActivity.this);
                    EdsStupci.add(vrijednost);
                    vrijednost.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                    vrijednost.setGravity(Gravity.CENTER);
                    vrijednost.setInputType(InputType.TYPE_CLASS_NUMBER);
                    layout.addView(vrijednost1);
                    layout.addView(vrijednost);
                    enter_form.addView(layout);
                }

                kreirajGrid = new Button(EnterParamsActivity.this);
                kreirajGrid.setText("Kreiraj mrežu");
                kreirajGrid.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                ViewGroup.MarginLayoutParams parametar = (ViewGroup.MarginLayoutParams) kreirajGrid.getLayoutParams();
                parametar.topMargin = 50;
                kreirajGrid.setLayoutParams(parametar);
                enter_form.addView(kreirajGrid);
                postaviClickerNakonUnosa();
            }

            public void postaviClickerNakonUnosa() {
                kreirajGrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < enter_form.getChildCount() - 1; i++) {
                            LinearLayout inner = (LinearLayout) enter_form.getChildAt(i);
                            for(int j = 0; j < inner.getChildCount(); j++ ) {
                                TextView view = (TextView) inner.getChildAt(j);
                                String strView = view.getText().toString();

                                if (TextUtils.isEmpty(strView)) {
                                    view.setError("Ovo polje ne može biti prazno.");
                                    return;
                                }
                            }
                        }
                        int redci = Integer.parseInt(brojRedaka.getText().toString());
                        int stupci = Integer.parseInt(brojStupaca.getText().toString());

                        int [] uneseniRedci = new int [EdsRedci.size()];

                        for(int i=0; i < EdsRedci.size(); i++){
                            uneseniRedci[i] = Integer.parseInt(EdsRedci.get(i).getText().toString());
                        }

                        int [] uneseniStupci = new int [EdsStupci.size()];

                        for(int i=0; i < EdsStupci.size(); i++){
                            uneseniStupci[i] = Integer.parseInt(EdsStupci.get(i).getText().toString());
                        }

                        Intent intent = new Intent(EnterParamsActivity.this, MainActivity.class);
                        intent.putExtra("broj redaka", redci);
                        intent.putExtra("broj stupaca", stupci);
                        intent.putExtra("uneseni redci", uneseniRedci);
                        intent.putExtra("uneseni stupci", uneseniStupci);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
