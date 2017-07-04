package com.example.android.cszadatak;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    GridLayout mainScreen;
    int brojStupaca;
    int brojRedaka;
    int[] uneseniRedci;
    int[] uneseniStupci;
    ArrayList<int[]> old;
    Button changePosition;
    TextView prviView;
    TextView drugiView;
    int brojac = 1;
    GridLayout.LayoutParams parametri1;
    GridLayout.LayoutParams parametri2;
    ArrayList<String> indexPosition;
    ArrayList<Integer> indexPositionReturned;
    int id1;
    int id2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        brojRedaka = prefs.getInt("broj redaka", 0);
        brojStupaca = prefs.getInt("broj stupaca", 0);
        String redciUnos = prefs.getString("unos redaka", "");
        StringTokenizer st = new StringTokenizer(redciUnos, ",");
        if(!redciUnos.equals("")) {
            uneseniRedci = new int[brojRedaka];
            for (int i = 0; i < brojRedaka; i++) {
                uneseniRedci[i] = Integer.parseInt(st.nextToken());
            }
        }

        String stupciUnos = prefs.getString("unos stupaca", "");
        if(!stupciUnos.equals("")) {
            StringTokenizer st1 = new StringTokenizer(stupciUnos, ",");
            uneseniStupci = new int[brojStupaca];
            for (int i = 0; i < brojStupaca; i++) {
                uneseniStupci[i] = Integer.parseInt(st1.nextToken());
            }
        }

        indexPositionReturned = new ArrayList<>();
        indexPositionReturned.clear();
        int velicinaIndexa = prefs.getInt("Status_size", 0);

        if(velicinaIndexa > 0) {
            for (int i = 0; i < velicinaIndexa; i++) {
                indexPositionReturned.add(Integer.valueOf(prefs.getString("Status_" + i, null)));
            }
        }

        Intent mIntent = getIntent();
        if( mIntent.getExtras() != null) {
            brojRedaka = mIntent.getIntExtra("broj redaka", 0);
            brojStupaca = mIntent.getIntExtra("broj stupaca", 0);
            uneseniRedci = mIntent.getExtras().getIntArray("uneseni redci");
            uneseniStupci = mIntent.getExtras().getIntArray("uneseni stupci");
        }

        changePosition = (Button) findViewById(R.id.btn_change_position);

        changePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              drugiView.setLayoutParams(parametri1);
              prviView.setLayoutParams(parametri2);
                drugiView.setId(id1);
                prviView.setId(id2);

            }
        });

        old = new ArrayList<>();

        mainScreen = (GridLayout) findViewById(R.id.mainScreen);
        int total = brojStupaca * brojRedaka;
        int column = brojStupaca;
        int row = brojRedaka;
        mainScreen.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        mainScreen.setColumnCount(column);
        mainScreen.setRowCount(row + 1);
        outerloop:
        for (int x= 0, i = 0, c = 0, r = 0; i < total; i++, c++) {
            if (c == uneseniRedci[x]) {
                c = 0;
                if(r == row - 1) {
                    break;
                }
                r++;
                if(x == uneseniRedci.length -1) {
                    break;
                }
                x++;
            }

            for (int [] index : old) {
               // if(index[2] > 1) {
                    for(int z = 0; z < index[2]; z++){
                        if(r == (index[0] + index[2] - z) && c == index[1] && index[2] != 0)  {
                            i--;
                            continue  outerloop;
                        }
                    }
                }

            TextView item = new TextView(this);
            item.setText(String.valueOf(i + 1));
            item.setTextSize(22);
            item.setGravity(Gravity.CENTER);
            Random rnd = new Random();
            int colorCode = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            item.setBackgroundColor(colorCode);

            //RETCI!
            if(c + 1 == uneseniRedci[x] && uneseniRedci[x] < column) {
                int span = 1 + (column - uneseniRedci[x]);
                GridLayout.LayoutParams parem = new GridLayout.LayoutParams(GridLayout.spec(r, 1f), GridLayout.spec(c, span, 1f));
                item.setLayoutParams(parem);
            }

            //STUPCI!
            else if (r + 1 == uneseniStupci[c] && uneseniStupci[c] < row) {
                int span = 1 + (row - uneseniStupci[c]);
                GridLayout.LayoutParams parem1 = new GridLayout.LayoutParams(GridLayout.spec(r, span, 1f), GridLayout.spec(c, 1f));
                item.setLayoutParams(parem1);
                int [] oldIndex = new int [3];
                oldIndex[0] = r;
                oldIndex[1] = c;
                oldIndex[2] = span - 1;
                old.add(oldIndex);
            }
            else {
                GridLayout.LayoutParams parem2 = new GridLayout.LayoutParams(GridLayout.spec(r, 1f), GridLayout.spec(c, 1f));
                item.setLayoutParams(parem2);
            }

            item.setSaveEnabled(true);
            item.setClickable(true);
            item.setId(i);
            mainScreen.addView(item);
        }

        changeView();
    }

    @Override
    public void onPause() {
        super.onPause();
        indexPosition = new ArrayList<>();
        for(int x = 0; x < mainScreen.getChildCount(); x++){
            indexPosition.add(String.valueOf(mainScreen.getChildAt(x).getId()));
        }

        SharedPreferences prefs = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putInt("broj redaka", brojRedaka);
        editor.putInt("broj stupaca", brojStupaca);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < uneseniRedci.length; i++) {
            str.append(uneseniRedci[i]).append(",");
        }

        StringBuilder str1 = new StringBuilder();
        for (int i = 0; i < uneseniStupci.length; i++) {
            str1.append(uneseniStupci[i]).append(",");
        }

        editor.putString("unos redaka", str.toString());
        editor.putString("unos stupaca", str1.toString());

        editor.putInt("Status_size", indexPosition.size());

        for(int i=0;i<indexPosition.size();i++)
        {
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, indexPosition.get(i));
        }

        editor.commit();
    }

    public void changeView() {
        for (int i = 0; i < mainScreen.getChildCount(); i++) {
            mainScreen.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(brojac == 1) {
                        prviView = (TextView) v;
                        parametri1 = (GridLayout.LayoutParams) prviView.getLayoutParams();
                        id1 = prviView.getId();
                        prviView.setTypeface(null, Typeface.BOLD);
                        prviView.setTextColor(Color.parseColor("#D50000"));
                        brojac++;
                    }
                    else if(brojac == 2) {
                        drugiView = (TextView) v;
                        parametri2 = (GridLayout.LayoutParams) drugiView.getLayoutParams();
                        id2 = drugiView.getId();
                        drugiView.setTypeface(null, Typeface.BOLD);
                        drugiView.setTextColor(Color.parseColor("#D50000"));
                        brojac++;
                    }
                    else {
                     for (int i = 0; i < mainScreen.getChildCount(); i++) {
                        TextView view = (TextView) mainScreen.getChildAt(i);
                         view.setTypeface(null, Typeface.NORMAL);
                         view.setTextColor(Color.parseColor("#000000"));
                        }
                     brojac = 1;
                     prviView = (TextView) v;
                     parametri1 = (GridLayout.LayoutParams) prviView.getLayoutParams();
                        id1 = prviView.getId();
                     prviView.setTypeface(null, Typeface.BOLD);
                     prviView.setTextColor(Color.parseColor("#D50000"));
                     brojac++;
                    }
                }
            });
        }
    }

}
