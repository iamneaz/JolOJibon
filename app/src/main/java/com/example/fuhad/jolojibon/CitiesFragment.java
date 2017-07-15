package com.example.fuhad.jonojog;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuhad.jolojibon.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitiesFragment extends Fragment {

    private RequestQueue requestQueue;
    private StringRequest request;

    private PieChart mChart;
    float[] yDataDhaka = new float[]{20.65f,13.15f,13.99f,3.3f,0,0,0,1.91f} ;
    private String[] yData;
    private String[] xData = { "Blockage of Main Drainage",
            "Blockage of Small Drainage",
            "Excessive Rainfall",
            "Garbage Beside the Drain",
            "Insufficient Drainage",
            "Poor Water Management",
            "Seepage from Canals",
            "Breaking Hardpan at a Canal Bed" };

    View v = null;
    Button button1;
    TextView text1;
    MenuItem camera;
    ImageView imageview;
    TextView title;
    TextView description;
    AutoCompleteTextView autoText;
    ImageButton imageButton;
    Button cause;
    Button counterAction;
    Button affectedAreas;
    int counter=0;
    String city="";
    String[] cities={"Dhaka","Faridpur","Chittagong","Comilla"};
    int b1=0;
    String text="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_cities_fragment, container, false);

        /*yData = Config.ReadFile().split(",");
        for(int i=0 ; i<yData.length; i++){
            yDataDhaka[i]= Float.parseFloat(yData[i]);
        }*/
        mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.setDescription("");

        //imageview=(ImageView)v.findViewById(R.id.descriptionImage);
        title=(TextView)v.findViewById(R.id.descriptionTitle);
        //description=(TextView)v.findViewById(R.id.problemDescription);
        autoText=(AutoCompleteTextView)v.findViewById(R.id.autoCompleteTextView);
        imageButton=(ImageButton)v.findViewById(R.id.citySearch);
        cause =(Button)v.findViewById(R.id.Cause);
        counterAction =(Button)v.findViewById(R.id.CounterAction);
//        affectedAreas=(Button)v.findViewById(R.id.affectedAreas);
//        affectedAreas.setVisibility(View.INVISIBLE);
        title.setText("Select a CITY");


        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cities);
        autoText.setAdapter(adapter);
        autoText.setThreshold(1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("");

                String cityName = String.valueOf(autoText.getText());
                if (cityName.contains("Dhaka")) {

                    city = "Dhaka";

                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                            // display msg when value selected
                            if (e == null)
                                return;
                            Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                            a.reset();

                            //Toast.makeText(getActivity(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
                            title.setText(xData[e.getXIndex()] + "=" + e.getVal() + "%");
                            title.clearAnimation();
                            title.setAnimation(a);

                            String string = (String) title.getText();
                            //description.setText("yo");
                            if (string.contains("Excessive Rainfall")) {
                                //description.setText(R.string.AirPolution);
                                title.setBackgroundColor(Color.rgb(204, 255, 136));
                            } else if (string.contains("Garbage Beside the Drain")) {
                                title.setBackgroundColor(Color.rgb(255, 254, 136));
                            } else if (string.contains("Blockage of Main Drainage")) {
                                title.setBackgroundColor(Color.rgb(255, 220, 138));
                            } else if (string.contains("Insufficient Drainage")) {
                                title.setBackgroundColor(Color.rgb(255, 137, 153));
                            } else if (string.contains("Blockage of Small Drainage")) {
                                title.setBackgroundColor(Color.rgb(121, 84, 136));
                            } else if (string.contains("Breaking Hardpan at a Canal Bed")) {
                                title.setBackgroundColor(Color.rgb(254, 153, 0));
                            } else if (string.contains("Seepage from Canals")) {
                                title.setBackgroundColor(Color.rgb(254, 254, 120));
                            } else if (string.contains("Poor Water Management")) {
                                title.setBackgroundColor(Color.rgb(103, 170, 135));
                            }
                        }

                        @Override
                        public void onNothingSelected() {


                        }
                    });

                    // add data
                    addData(xData, yDataDhaka);


                }
            }

        });



        cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                cause.startAnimation(myAnim);



                if(city=="Dhaka")
                {
                    String string = (String) title.getText();


                    if (string.isEmpty())
                    {
                        Toast.makeText(getActivity(),"Select a Slice of Pie Chart",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setInverseBackgroundForced(true);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        if (string.contains("Excessive Rainfall")) {
                            builder1.setMessage(R.string.AirPollution_Cause_Dhaka);
                        } else if (string.contains("Blockage of Main Drainage")) {
                            builder1.setMessage(R.string.Garbage_Causes_Dhaka);
                        } else if (string.contains("Poor Water Management")) {
                            builder1.setMessage(R.string.ThermalPollution_Cause_Dhaka);
                        } else if (string.contains("Insufficient Drainage")) {
                            builder1.setMessage(R.string.NoisePollution_Cause_Dhaka);
                        } else if (string.contains("Blockage of Small Drainage")) {
                            builder1.setMessage(R.string.WaterPollution_Cause_Dhaka);
                        } else if (string.contains("Seepage from Canals")) {
                            builder1.setMessage(R.string.LightPollution_Cause_Dhaka);
                        } else if (string.contains("Breaking Hardpan at a Canal Bed")) {
                            builder1.setMessage(R.string.VisualPollution_Cause_Dhaka);
                        } else if (string.contains("Garbage Beside the Drain")) {
                            builder1.setMessage(R.string.SoilPollution_Cause_Dhaka);
                        }


                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }


                }
                else
                {
                    Toast.makeText(getActivity(),"Select a City",Toast.LENGTH_LONG).show();
                }

            }
        });
//        affectedAreas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
//                builder1.setCancelable(true);
//
//                builder1.setPositiveButton(
//                        "Close",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                if(city=="Dhaka")
//                {
//                    String string = (String) title.getText();
//
//
//                    if (string.contains("Excessive Rainfall")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);
//                    } else if (string.contains("Blockage of Main Drainage")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);
//                    } else if (string.contains("Garbage Beside the Drain")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);}
//                    else if (string.contains("Insufficient Drainage")) {
//                        builder1.setMessage(R.string.NoisePollution_Area_Dhaka);
//                    } else if (string.contains("Blockage of Small Drainage")) {
//                        builder1.setMessage(R.string.WaterPollution_Area_Dhaka);
//                    } else if (string.contains("Breaking Hardpan at a Canal Bed")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);
//                    } else if (string.contains("Seepage from Canals")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);
//                    } else if (string.contains("Poor Water Management")) {
//                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);
//                    }
//                }
//                AlertDialog alert11 = builder1.create();
//                alert11.show();
//            }
//        });

        counterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                counterAction.startAnimation(myAnim);


                if(city=="Dhaka")
                {
                    String string = (String) title.getText();

                    if (string.isEmpty())
                    {
                        Toast.makeText(getActivity(),"Select a Slice of Pie Chart",Toast.LENGTH_LONG).show();

                    }
                    else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });


                        if (string.contains("Excessive Rainfall")) {
                            builder1.setMessage(R.string.AirPollution_Counter_Dhaka);
                        } else if (string.contains("Blockage of Main Drainage")) {
                            builder1.setMessage(R.string.Garbage_Counter_Dhaka);
                        } else if (string.contains("Poor Water Management")) {
                            builder1.setMessage(R.string.ThermalPollution_Counter_Dhaka);
                        } else if (string.contains("Insufficient Drainage")) {
                            builder1.setMessage(R.string.NoisePollution_Counter_Dhaka);
                        } else if (string.contains("Blockage of Small Drainage")) {
                            builder1.setMessage(R.string.WaterPollution_Counter_Dhaka);
                        } else if (string.contains("Seepage from Canals")) {
                            builder1.setMessage(R.string.LightPollution_Counter_Dhaka);
                        } else if (string.contains("Breaking Hardpan at a Canal Bed")) {
                            builder1.setMessage(R.string.VisualPollution_Counter_Dhaka);
                        } else if (string.contains("Garbage Beside the Drain")) {
                            builder1.setMessage(R.string.SoilPollution_Counter_Dhaka);
                        }
                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }



                }
                else
                {
                    Toast.makeText(getActivity(),"Select a City",Toast.LENGTH_LONG).show();
                }

            }


        });



        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        //mChart.setCenterTextTypeface(tf);
        mChart.setCenterText("POLLUTION");
        //mChart.setCenterTextSize(10f);
        //mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);


        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);


        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);




        // set a chart value selected listener
        // mChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));

        //Button
//        button1=(Button)v.findViewById(R.id.button1);
        //text
        //text1=(TextView)v.findViewById(R.id.textView1);
        //text1.setVisibility(View.GONE);







        return v;

    }


    private void addData(String[] xData,float[] yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> demo = new ArrayList<>();

        for (int i = 0; i < xData.length; i++)
        {
            xVals.add(xData[i]);
            demo.add("");
        }

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(5);
        dataSet.setSelectionShift(5);




        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(demo,dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}
