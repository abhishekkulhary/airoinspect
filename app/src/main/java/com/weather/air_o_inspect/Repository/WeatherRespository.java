package com.weather.air_o_inspect.Repository;

import android.app.Application;
import android.graphics.Color;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.Database.WeatherDatabase;
import com.weather.air_o_inspect.Database.WeatherForecastDAO;
import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherCurrentRequired;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeatherRespository {

    private final static Integer INSERT_OPERTION = 0;
    private final static Integer UPDATE_OPERTION = 1;
    private final static Integer DELETE_OPERTION = 2;

    private WeatherForecastDAO weatherForecastDAO;

    private LiveData<List<WeatherForecast>> weatherForecastLiveData;
    private LiveData<List<WeatherCurrent>> weatherCurrentLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;

    public WeatherRespository(Application application) {

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        weatherForecastDAO = weatherDatabase.weatherUpdateDAO();


        weatherForecastLiveData = weatherForecastDAO.getAllWeatherForecast();

        weatherCurrentLiveData = weatherForecastDAO.getWeatherCurrent();

        preferencesLiveData = weatherForecastDAO.getPreferences();

    }

    public void insertWeatherCurrent(WeatherCurrent weatherCurrent) {
        new WeatherCurrentAsyncTask(weatherForecastDAO, INSERT_OPERTION).execute(weatherCurrent);
    }

    public void updateWeatherCurrent(WeatherCurrent weatherCurrent) {
        new WeatherCurrentAsyncTask(weatherForecastDAO, UPDATE_OPERTION).execute(weatherCurrent);
    }

    public void deleteWeatherCurrent(WeatherCurrent weatherCurrent) {
        new WeatherCurrentAsyncTask(weatherForecastDAO, DELETE_OPERTION).execute(weatherCurrent);
    }

    public void insertWeatherForecast(WeatherForecast weatherForecast) {
        new WeatherForeCastAsyncTask(weatherForecastDAO, INSERT_OPERTION).execute(weatherForecast);
    }

    public void updateWeatherForecast(WeatherForecast weatherForecast) {
        new WeatherForeCastAsyncTask(weatherForecastDAO, UPDATE_OPERTION).execute(weatherForecast);
    }

    public void deleteAllWeatherForecast() {
        new WeatherForeCastAsyncTask(weatherForecastDAO, DELETE_OPERTION).execute(Objects.requireNonNull(weatherForecastLiveData.getValue()).toArray(new WeatherForecast[weatherForecastLiveData.getValue().size()]));
    }

    public void insertPreferences(Preferences preferences) {
        new PreferencesAsyncTask(weatherForecastDAO, INSERT_OPERTION).execute(preferences);
    }

    public void updatePreferences(Preferences preferences) {
        new PreferencesAsyncTask(weatherForecastDAO, UPDATE_OPERTION).execute(preferences);
    }

    public void deletePreferences(Preferences preferences) {
        new PreferencesAsyncTask(weatherForecastDAO, DELETE_OPERTION).execute(preferences);
    }

    public LiveData<List<Preferences>> getPreferencesLiveData() {
        return preferencesLiveData;
    }

    public LiveData<WeatherCurrentRequired> getWeatherCurrentRequiredLiveData() {
        return Transformations.switchMap(weatherCurrentLiveData, new Function<List<WeatherCurrent>, LiveData<WeatherCurrentRequired>>() {
            @Override
            public LiveData<WeatherCurrentRequired> apply(final List<WeatherCurrent> weatherCurrents) {
            return Transformations.map(preferencesLiveData, new Function<List<Preferences>, WeatherCurrentRequired>() {
                @Override
                public WeatherCurrentRequired apply(List<Preferences> preferences) {
                if (weatherCurrents != null && !weatherCurrents.isEmpty() && preferences != null && !preferences.isEmpty()) {
                    WeatherCurrentRequired required = new WeatherCurrentRequired();
                    Preferences preference = preferences.get(0);
                    WeatherCurrent weatherCurrent = weatherCurrents.get(0);
                    required.setPrecipIntensity(weatherCurrent.getPrecipIntensity());
                    required.setPrecipProbability(weatherCurrent.getPrecipProbability());
                    required.setTemperature(weatherCurrent.getTemperature());
                    required.setPressure(weatherCurrent.getPressure());
                    required.setWindSpeed(weatherCurrent.getWindSpeed());
                    required.setWindGust(weatherCurrent.getWindGust());
                    required.setCloudCover(weatherCurrent.getCloudCover());
                    required.setVisibility(weatherCurrent.getVisibility());
                    required.setDateTime(weatherCurrent.getDateTime());
                    required.setFlyStatus(getFlyStatusColor(weatherCurrent, preference));
                    return required;
                }
                return null;
                }
            });

            }
        });
    }

    private int getFlyStatusColor(WeatherCurrent weatherCurrent1, Preferences preferences1) {
        Boolean[] checks = {preferences1.getPrecipitationIntensitySwitch(), preferences1.getPrecipitationProbabilitySwitch(),
            preferences1.getTemperatureSwitch(), preferences1.getPressureSwitch(), preferences1.getWindSpeedSwitch(),
            preferences1.getWindGustSwitch(), preferences1.getCloudCoverSwitch(), preferences1.getVisibilitySwitch()};

        Boolean[] thresoldChecks = {weatherCurrent1.getPrecipIntensity() < preferences1.getPrecipitationIntensityThresold(),
            weatherCurrent1.getPrecipProbability() < preferences1.getPrecipitationProbabilityThresold(),
            weatherCurrent1.getTemperature() < preferences1.getTemperatureThresold(),
            weatherCurrent1.getPressure() < preferences1.getPressureThresold(),
            weatherCurrent1.getWindSpeed() < preferences1.getWindSpeedThresold(),
            weatherCurrent1.getWindGust() < preferences1.getWindGustThresold(),
            weatherCurrent1.getCloudCover() < preferences1.getCloudCoverThresold(),
            weatherCurrent1.getVisibility() < preferences1.getVisibilityThresold()};
        boolean finalDecision = true;
        int i = 0;
        for (Boolean check : checks) {

            if (check) {

                if (!thresoldChecks[i]) {

                    finalDecision = false;
                    break;
                }

            }
            i++;
        }
        if (finalDecision) {
            return Color.GREEN;
        } else {

            return Color.RED;
        }
    }

    public LiveData<List<ChartsData>> getWeatherForecastIndividually() {
        return Transformations.switchMap(weatherForecastLiveData, new Function<List<WeatherForecast>, LiveData<List<ChartsData>>>() {
            @Override
            public LiveData<List<ChartsData>> apply(final List<WeatherForecast> weatherForecasts) {
            return Transformations.map(preferencesLiveData, new Function<List<Preferences>, List<ChartsData>>() {
                @Override
                public List<ChartsData> apply(final List<Preferences> preferences) {
                List<ChartsData> chartDataList = new ArrayList<>();
                if (preferences != null && !preferences.isEmpty()) {
                    Preferences preferences1 = preferences.get(0);
                    for (String column : MyApplication.getCOLUMNS()) {
                        ArrayList<BarEntry> barEntries = new ArrayList<>();
                        ArrayList<Integer> colors = new ArrayList<>();
                        ArrayList<Long> timesInMillis = new ArrayList<>();
                        for (int i = 0; i < weatherForecasts.size(); i++) {
                            WeatherForecast forecast = weatherForecasts.get(i);
                            if (column.equals(MyApplication.getPrecipIntensityColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getPrecipIntensity()));
                                if (preferences1.getPrecipitationIntensitySwitch()) {
                                    if (forecast.getPrecipIntensity() <= preferences1.getPrecipitationIntensityThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getPrecipProbabilityColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getPrecipProbability()));
                                if (preferences1.getPrecipitationProbabilitySwitch()) {
                                    if (forecast.getPrecipProbability() <= preferences1.getPrecipitationProbabilityThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getTemperatureColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getTemperature()));
                                if (preferences1.getTemperatureSwitch()) {
                                    if (forecast.getTemperature() <= preferences1.getTemperatureThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getPressureColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getPressure()));
                                if (preferences1.getPressureSwitch()) {
                                    if (forecast.getPressure() <= preferences1.getPressureThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getWindSpeedColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getWindSpeed()));
                                if (preferences1.getWindSpeedSwitch()) {
                                    if (forecast.getWindSpeed() <= preferences1.getWindSpeedThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getWindGustColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getWindGust()));
                                if (preferences1.getWindGustSwitch()) {
                                    if (forecast.getWindGust() <= preferences1.getWindGustThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getCloudCoverColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getCloudCover()));
                                if (preferences1.getCloudCoverSwitch()) {
                                    if (forecast.getCloudCover() <= preferences1.getCloudCoverThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            } else if (column.equals(MyApplication.getVisibilityColumn())) {
                                barEntries.add(new BarEntry(i, forecast.getVisibility()));
                                if (preferences1.getVisibilitySwitch()) {
                                    if (forecast.getVisibility() <= preferences1.getVisibilityThresold()) {
                                        colors.add(Color.GREEN);
                                    } else {
                                        colors.add(Color.RED);
                                    }
                                } else {
                                    colors.add(Color.DKGRAY);
                                }
                                timesInMillis.add(forecast.getTimeInMillis());
                            }
                        }
                        BarData barData = new BarData();
                        ChartsData chartsData = null;
                        if (!barEntries.isEmpty()) {
                            BarDataSet barDataSet = new BarDataSet(barEntries, "");
                            barDataSet.setColors(colors);
                            barDataSet.setDrawValues(false);
                            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                            barData.addDataSet(barDataSet);
                            chartsData = new ChartsData(barData, MyApplication.getLABELS().get(MyApplication.getCOLUMNS().indexOf(column)), MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(column)), timesInMillis);
                        }
                        chartDataList.add(chartsData);
                    }
                }
                return chartDataList;
                }
            });
            }
        });
    }


    public LiveData<ChartsData> getWeatherForecastFlyStatus() {
        return Transformations.switchMap(weatherForecastLiveData, new Function<List<WeatherForecast>, LiveData<ChartsData>>() {
            @Override
            public LiveData<ChartsData> apply(final List<WeatherForecast> weatherForecasts) {
            return Transformations.map(preferencesLiveData, new Function<List<Preferences>, ChartsData>() {
                @Override
                public ChartsData apply(final List<Preferences> preferences) {
                ChartsData chartDataList = null;
                if (preferences != null && !preferences.isEmpty()) {
                    Preferences preferences1 = preferences.get(0);
                    ArrayList<BarEntry> flyStatusBarEntry = new ArrayList<>();
                    for (int i = 0; i < weatherForecasts.size(); i++) {
                        flyStatusBarEntry.add(new BarEntry(i, 1));
                    }
                    int green = Color.rgb(110, 190, 102);
                    int red = Color.rgb(211, 74, 88);
                    BarData barData;
                    ArrayList<Integer> colors = new ArrayList<>();
                    ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                    ArrayList<Long> timesInMillis = new ArrayList<>();

                    for (WeatherForecast forecast : weatherForecasts) {
                        int i = 0;
                        timesInMillis.add(forecast.getTimeInMillis());
                        if (preferences1.getPrecipitationIntensitySwitch()) {
                            if (forecast.getPrecipIntensity() > preferences1.getPrecipitationIntensityThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getPrecipitationProbabilitySwitch()) {
                            if (forecast.getPrecipProbability() > preferences1.getPrecipitationProbabilityThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getTemperatureSwitch()) {
                            if (forecast.getTemperature() > preferences1.getTemperatureThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getPressureSwitch()) {
                            if (forecast.getPressure() > preferences1.getPressureThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getWindSpeedSwitch()) {
                            if (forecast.getWindSpeed() > preferences1.getWindSpeedThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getWindGustSwitch()) {
                            if (forecast.getWindGust() > preferences1.getWindGustThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getCloudCoverSwitch()) {
                            if (forecast.getCloudCover() > preferences1.getCloudCoverThresold()) {
                                i++;
                            }
                        }
                        if (preferences1.getVisibilitySwitch()) {
                            if (forecast.getVisibility() > preferences1.getVisibilityThresold()) {
                                i++;
                            }
                        }
                        if (i == 0) {
                            colors.add(green);
                        } else {
                            colors.add(red);
                        }
                    }
                    // Here each dataset would be processed, temp, sunshine etc.
                    BarDataSet barDataSet = new BarDataSet(flyStatusBarEntry, "Flying Status");
                    barDataSet.setColors(colors);

                    barDataSet.setDrawValues(false);
                    barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    barDataSets.add(barDataSet);

                    barData = new BarData(barDataSets);

                    chartDataList = new ChartsData(barData, "Fly Status", "Color: green/red", timesInMillis);
                }
                return chartDataList;
                }
            });
            }
        });
    }
}
