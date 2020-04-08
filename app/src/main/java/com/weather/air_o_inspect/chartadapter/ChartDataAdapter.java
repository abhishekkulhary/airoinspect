package com.weather.air_o_inspect.chartadapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.viewholders.ChartViewHolder;
import com.weather.air_o_inspect.viewholders.ChartsData;

import java.util.List;

public class ChartDataAdapter extends RecyclerView.Adapter<ChartViewHolder> {

    private List<ChartsData> chartDataList;


    public ChartDataAdapter(List<ChartsData> objects) {
        this.chartDataList = objects;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem_chart, parent, false);
        return new ChartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        //getting the product of the specified position
        ChartsData chartsData = chartDataList.get(position);

        holder.chart_name.setText(chartsData.getChart_name());
        holder.unit_value.setText(chartsData.getUnit_value());
        BarData data = chartsData.getData();

        if (data != null) {
            data.setValueTextColor(Color.BLACK);
            data.setHighlightEnabled(false);
            data.setValueTextSize(5f);
//            data.calcMinMax();

            holder.chart.getDescription().setEnabled(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.enableGridDashedLine(10f, 5f, 0f);
            xAxis.setGranularityEnabled(true);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(data.getEntryCount(), true);
            xAxis.setAxisMinimum(data.getXMin());
            xAxis.setAxisMaximum(data.getXMax());
            xAxis.setDrawLabels(false);
            xAxis.setCenterAxisLabels(false);

            // Y - axis
            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setEnabled(true);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(true);
            rightAxis.setDrawLimitLinesBehindData(true);
            rightAxis.setLabelCount(7, true);
            rightAxis.setMinWidth(35f);
            rightAxis.setMaxWidth(40f);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.enableGridDashedLine(10f, 5f, 0f);
            leftAxis.setDrawLimitLinesBehindData(true);
            leftAxis.setLabelCount(7, true);
            leftAxis.setMinWidth(35f);
            leftAxis.setMaxWidth(40f);

            if (data.getYMin() > (0 + (data.getYMax() + data.getYMin()) / 2)) {
                leftAxis.setAxisMinimum(0);
                rightAxis.setAxisMinimum(0);
            } else {
                leftAxis.setAxisMinimum(data.getYMin() - (data.getYMax() + data.getYMin()) / 2);
                rightAxis.setAxisMinimum(data.getYMin() - (data.getYMax() + data.getYMin()) / 2);
            }
            rightAxis.setAxisMaximum(data.getYMax() + (data.getYMax() + data.getYMin()) / 2);
            leftAxis.setAxisMaximum(data.getYMax() + (data.getYMax() + data.getYMin()) / 2);

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