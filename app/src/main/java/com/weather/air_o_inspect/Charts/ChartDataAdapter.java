package com.weather.air_o_inspect.Charts;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.R;

import java.util.ArrayList;
import java.util.List;

public class ChartDataAdapter extends RecyclerView.Adapter<ChartViewHolder> {
    private List<ChartsData> chartsDataList;

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_chart, parent, false);
        return new ChartViewHolder(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ChartViewHolder holder, final int position) {
        //getting the product of the specified position

        ChartsData chartsData = chartsDataList.get(position);
        if (chartsData != null) {

            holder.chart_name.setText(chartsData.getChart_name());
            holder.unit_value.setText(chartsData.getUnit_value());
            BarData data = chartsData.getData();

            final ArrayList<Long> xValues = chartsData.getxValues();

            data.setValueTextColor(Color.WHITE);
            data.setHighlightEnabled(true);
            data.setValueTextSize(5f);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setTextColor(Color.WHITE);
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setGranularityEnabled(true);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f);
            xAxis.setAxisMaxLabels(data.getEntryCount() + 2);
            xAxis.setLabelCount(data.getEntryCount() + 2);
            xAxis.setAxisMinimum(data.getXMin() - 1);
            xAxis.setAxisMaximum(data.getXMax() + 1);
            xAxis.setDrawLabels(true);
            xAxis.setCenterAxisLabels(false);

            holder.chart.setXAxisRenderer(new DoubleXLabelAxisRenderer(holder.chart.getViewPortHandler(), xAxis,
                    holder.chart.getTransformer(YAxis.AxisDependency.LEFT),
                    new ValueFormatter() {
                        String temp = "";

                        @Override
                        public String getFormattedValue(float value) {
                            if (value >= 0 && value < xValues.size()) {
                                if (temp.equals(MyApplication.getSimpleDateFormat().format(xValues.get((int) value) * 1000))) {
                                    return "";
                                } else {
                                    temp = MyApplication.getSimpleDateFormat().format(xValues.get((int) value) * 1000);
                                    return MyApplication.getSimpleDateInChart().format(xValues.get((int) value) * 1000);
                                }
                            }
                            return "";
                        }
                    }, new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value >= 0 && value < xValues.size()) {
                        return MyApplication.getSimpleTimeFormat().format(xValues.get((int) value) * 1000);
                    }
                    return "";
                }
            }));

            // Y - axis
            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setEnabled(false);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.enableGridDashedLine(10f, 5f, 0f);
            leftAxis.setDrawLimitLinesBehindData(true);
            leftAxis.setLabelCount(5, true);
            leftAxis.setMinWidth(40f);
            leftAxis.setMaxWidth(40f);
            leftAxis.setTextColor(Color.WHITE);
            leftAxis.setAxisMaximum(MyApplication.getChartMaxyValue().get(position));

            leftAxis.setAxisMinimum(0f);

            Legend legend = holder.chart.getLegend();
            legend.setEnabled(false);

            holder.chart.getDescription().setEnabled(false);
            holder.chart.setVisibleXRangeMaximum(24f);

            holder.chart.setScaleEnabled(false);

            holder.chart.setData(data);
            holder.chart.notifyDataSetChanged();

            holder.chart.invalidate();
        }

    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return this.chartsDataList.size();
    }

    public void setChartsDataList(List<ChartsData> chartsDataList) {
        this.chartsDataList = chartsDataList;
        notifyDataSetChanged();
    }
}