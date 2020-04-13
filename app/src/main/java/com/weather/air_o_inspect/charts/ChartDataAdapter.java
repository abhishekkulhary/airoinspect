package com.weather.air_o_inspect.charts;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.R;

import java.util.ArrayList;
import java.util.List;

public class ChartDataAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    private List<ChartsData> chartDataList;
    private MyApplication myApplication;


    public ChartDataAdapter(List<ChartsData> objects) {
        this.chartDataList = objects;
        this.myApplication = new MyApplication();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_chart, parent, false);
        return new ChartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        //getting the product of the specified position
        ChartsData chartsData = chartDataList.get(position);

        holder.chart_name.setText(chartsData.getChart_name());
        holder.unit_value.setText(chartsData.getUnit_value());
        BarData data = chartsData.getData();

        final ArrayList<Long> xValues = chartsData.getxValues();

        if (data != null) {
            data.setValueTextColor(Color.WHITE);
            data.setHighlightEnabled(false);
            data.setValueTextSize(5f);

            holder.chart.getDescription().setEnabled(false);


            XAxis xAxis = holder.chart.getXAxis();

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.enableGridDashedLine(10f, 5f, 0f);
            xAxis.setGranularityEnabled(true);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(data.getEntryCount() + 2, true);
            xAxis.setAxisMinimum(data.getXMin() - 1);
            xAxis.setAxisMaximum(data.getXMax() + 1);
            xAxis.setDrawLabels(true);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    if (value >= 0 && value < xValues.size()) {
                        return myApplication.getSimpleTimeFormat().format(xValues.get((int) value) * 1000);
                    }
                    return "";
                }
            });

            //xAxis.setLabelRotationAngle(-80f);

            xAxis.setCenterAxisLabels(false);
            xAxis.setTextColor(Color.WHITE);

            // Y - axis
            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setEnabled(true);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(true);
            rightAxis.setDrawLimitLinesBehindData(true);
            rightAxis.setLabelCount(7, true);
            rightAxis.setMinWidth(35f);
            rightAxis.setMaxWidth(40f);
            rightAxis.setTextColor(Color.WHITE);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.enableGridDashedLine(10f, 5f, 0f);
            leftAxis.setDrawLimitLinesBehindData(true);
            leftAxis.setLabelCount(7, true);
            leftAxis.setMinWidth(35f);
            leftAxis.setMaxWidth(40f);
            leftAxis.setTextColor(Color.WHITE);

            rightAxis.setAxisMaximum(myApplication.getCOLUMNS_MAXVALUE()[position] + (myApplication.getCOLUMNS_MAXVALUE()[position] + data.getYMin()) / 2);
            leftAxis.setAxisMaximum(myApplication.getCOLUMNS_MAXVALUE()[position] + (myApplication.getCOLUMNS_MAXVALUE()[position] + data.getYMin()) / 2);

            leftAxis.setAxisMinimum(0f);
            rightAxis.setAxisMinimum(0f);

            Legend legend = holder.chart.getLegend();
            legend.setEnabled(false);

            holder.chart.setScaleEnabled(false);

            holder.chart.setData(data);
            holder.chart.invalidate();
        }
    }

    // Replace the contents of a view (invoked by the layout manager)


    @Override
    public int getItemCount() {
        return chartDataList.size();
    }

}