package com.weather.air_o_inspect.Repository;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.weather.air_o_inspect.Database.DatabaseUtils;
import com.weather.air_o_inspect.Database.WeatherDatabase;
import com.weather.air_o_inspect.Database.WeatherForecastDAO;
import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherCurrentRequired;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;
import com.weather.air_o_inspect.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeatherRespository {

    private final static Integer INSERT_OPERTION = 0;
    private final static Integer UPDATE_OPERTION = 1;
    private final static Integer DELETE_OPERTION = 2;

    private static WeatherForecastDAO weatherForecastDAO;

    private LiveData<List<WeatherForecast>> weatherForecastLiveData;
    private LiveData<List<WeatherCurrent>> weatherCurrentLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;
    private LiveData<List<WeatherForecastDaily>> weatherForecastDailyLiveData;

    private final Context context;

    public WeatherRespository(Application application) {

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        weatherForecastDAO = weatherDatabase.weatherUpdateDAO();

        weatherForecastLiveData = weatherForecastDAO.getAllWeatherForecast();

        weatherCurrentLiveData = weatherForecastDAO.getWeatherCurrent();

        preferencesLiveData = weatherForecastDAO.getPreferences();

        weatherForecastDailyLiveData = weatherForecastDAO.getAllWeatherForecastDaily();

        context = application.getApplicationContext();

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

    public void insertWeatherForecastDaily(WeatherForecastDaily weatherForecastDaily) {
        new WeatherForeCastDailyAsyncTask(weatherForecastDAO, INSERT_OPERTION).execute(weatherForecastDaily);
    }

    public void updateWeatherForecastDaily(WeatherForecastDaily weatherForecastDaily) {
        new WeatherForeCastDailyAsyncTask(weatherForecastDAO, UPDATE_OPERTION).execute(weatherForecastDaily);
    }

    public void deleteAllWeatherForecastDaily() {
        new WeatherForeCastDailyAsyncTask(weatherForecastDAO, DELETE_OPERTION).execute(Objects.requireNonNull(weatherForecastDailyLiveData.getValue()).toArray(new WeatherForecastDaily[weatherForecastDailyLiveData.getValue().size()]));
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
                return Transformations.switchMap(preferencesLiveData, new Function<List<Preferences>, LiveData<WeatherCurrentRequired>>() {
                    @Override
                    public LiveData<WeatherCurrentRequired> apply(final List<Preferences> preferences) {
                        return Transformations.map(weatherForecastDailyLiveData, new Function<List<WeatherForecastDaily>, WeatherCurrentRequired>() {
                            @Override
                            public WeatherCurrentRequired apply(List<WeatherForecastDaily> weatherForecastDailyList) {
                                if (weatherCurrents != null && !weatherCurrents.isEmpty() && preferences != null && !preferences.isEmpty()
                                        && weatherForecastDailyList != null && !weatherForecastDailyList.isEmpty()) {
                                    WeatherCurrentRequired required = new WeatherCurrentRequired();
                                    Preferences preference = preferences.get(0);
                                    WeatherCurrent weatherCurrent = weatherCurrents.get(0);
                                    int index = -1;
                                    for (WeatherForecastDaily forecastDaily : weatherForecastDailyList) {
                                        if (forecastDaily.getSunriseTimeInMillis() <= weatherCurrent.getTimeInMillis()
                                                && weatherCurrent.getTimeInMillis() <= forecastDaily.getSunsetTimeInMillis()) {
                                            index = weatherForecastDailyList.indexOf(forecastDaily);
                                            break;
                                        }
                                    }
                                    float sunshine = 0.0f;
                                    if (index != -1) {
                                        sunshine = (1 - weatherCurrent.getCloudCover()) * 60.0f;
                                    }
                                    required.setSunshine(sunshine);
                                    required.setTemperature(weatherCurrent.getTemperature());
                                    required.setWindSpeed(weatherCurrent.getWindSpeed());
                                    required.setWindGust(weatherCurrent.getWindGust());
                                    required.setPrecipIntensity(weatherCurrent.getPrecipIntensity());
                                    required.setPrecipProbability(weatherCurrent.getPrecipProbability());
                                    required.setDateTime(weatherCurrent.getDateTime());
                                    required.setFlyStatus(getFlyStatusColor(required, preference));

                                    String[] cityName = MyApplication.getCityName(context, weatherCurrent.getLatitude(), weatherCurrent.getLongitude());
                                    required.setCityName(cityName[1] + "," + cityName[2]);

                                    return required;
                                }
                                return null;
                            }
                        });
                    }
                });

            }
        });
    }

    private int getFlyStatusColor(WeatherCurrentRequired weatherCurrent1, Preferences preferences1) {
        Boolean[] checks = {preferences1.getSunshineSwitch(), preferences1.getTemperatureSwitch(), preferences1.getWindSpeedSwitch(),
                preferences1.getWindGustSwitch(), preferences1.getPrecipitationIntensitySwitch(),
                preferences1.getPrecipitationProbabilitySwitch()};

        Boolean[] thresoldChecks = {
                weatherCurrent1.getSunshine() > preferences1.getSunshineThresold(),
                weatherCurrent1.getTemperature() < preferences1.getTemperatureThresold(),
                weatherCurrent1.getWindSpeed() < preferences1.getWindSpeedThresold(),
                weatherCurrent1.getWindGust() < preferences1.getWindGustThresold(),
                weatherCurrent1.getPrecipIntensity() < preferences1.getPrecipitationIntensityThresold(),
                weatherCurrent1.getPrecipProbability() < preferences1.getPrecipitationProbabilityThresold()};
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

    public LiveData<List<WeatherForecastDaily>> getWeatherForecastDailyLiveData() {
        return weatherForecastDailyLiveData;
    }

    public LiveData<List<ChartsData>> getWeatherForecastIndividually() {
        return Transformations.switchMap(weatherForecastLiveData, new Function<List<WeatherForecast>, LiveData<List<ChartsData>>>() {
            @Override
            public LiveData<List<ChartsData>> apply(final List<WeatherForecast> weatherForecasts) {
                return Transformations.switchMap(preferencesLiveData, new Function<List<Preferences>, LiveData<List<ChartsData>>>() {
                    @Override
                    public LiveData<List<ChartsData>> apply(final List<Preferences> preferences) {
                        return Transformations.map(weatherForecastDailyLiveData, new Function<List<WeatherForecastDaily>, List<ChartsData>>() {
                            @Override
                            public List<ChartsData> apply(List<WeatherForecastDaily> weatherForecastDailyList) {
                                List<ChartsData> chartDataList = new ArrayList<>();
                                if (preferences != null && !preferences.isEmpty() && weatherForecastDailyList != null
                                        && !weatherForecastDailyList.isEmpty() && weatherForecasts != null && !weatherForecasts.isEmpty()) {
                                    Preferences preferences1 = preferences.get(0);
                                    for (String column : MyApplication.getCOLUMNS()) {
                                        ArrayList<BarEntry> barEntries = new ArrayList<>();
                                        ArrayList<Integer> colors = new ArrayList<>();
                                        ArrayList<Long> timesInMillis = new ArrayList<>();
                                        for (int i = 0; i < weatherForecasts.size(); i++) {
                                            WeatherForecast forecast = weatherForecasts.get(i);
                                            if (column.equals(MyApplication.getSunshineColumn())) {
                                                int index = -1;
                                                for (WeatherForecastDaily forecastDaily : weatherForecastDailyList) {
                                                    if (forecastDaily.getSunriseTimeInMillis() <= forecast.getTimeInMillis()
                                                            && forecast.getTimeInMillis() <= forecastDaily.getSunsetTimeInMillis()) {
                                                        index = weatherForecastDailyList.indexOf(forecastDaily);
                                                        break;
                                                    }
                                                }
                                                float sunshine = 0.0f;
                                                if (index != -1) {
                                                    sunshine = (1 - forecast.getCloudCover()) * 60.0f;
                                                }
                                                barEntries.add(new BarEntry(i, sunshine));
                                                if (preferences1.getSunshineSwitch()) {
                                                    if (sunshine >= preferences1.getSunshineThresold()) {
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
                                            } else if (column.equals(MyApplication.getPrecipIntensityColumn())) {
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

                                            chartsData = new ChartsData(barData, column, MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(column)), timesInMillis);
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
        });
    }

    public LiveData<ChartsData> getWeatherForecastFlyStatus() {
        return Transformations.switchMap(weatherForecastLiveData, new Function<List<WeatherForecast>, LiveData<ChartsData>>() {
            @Override
            public LiveData<ChartsData> apply(final List<WeatherForecast> weatherForecasts) {
                return Transformations.switchMap(preferencesLiveData, new Function<List<Preferences>, LiveData<ChartsData>>() {
                    @Override
                    public LiveData<ChartsData> apply(final List<Preferences> preferences) {
                        return Transformations.map(weatherForecastDailyLiveData, new Function<List<WeatherForecastDaily>, ChartsData>() {
                            @Override
                            public ChartsData apply(List<WeatherForecastDaily> weatherForecastDailyList) {
                                ChartsData chartDataList = null;
                                if (preferences != null && !preferences.isEmpty()) {
                                    Preferences preferences1 = preferences.get(0);
                                    ArrayList<BarEntry> flyStatusBarEntry = new ArrayList<>();
                                    for (int i = 0; i < weatherForecasts.size(); i++) {
                                        flyStatusBarEntry.add(new BarEntry(i, 1));
                                    }
                                    BarData barData;
                                    ArrayList<Integer> colors = new ArrayList<>();
                                    ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                                    ArrayList<Long> timesInMillis = new ArrayList<>();

                                    for (WeatherForecast forecast : weatherForecasts) {
                                        int i = 0;
                                        timesInMillis.add(forecast.getTimeInMillis());
                                        if (preferences1.getSunshineSwitch()) {
                                            int index = -1;
                                            for (WeatherForecastDaily forecastDaily : weatherForecastDailyList) {
                                                if (forecastDaily.getSunriseTimeInMillis() <= forecast.getTimeInMillis()
                                                        && forecast.getTimeInMillis() <= forecastDaily.getSunsetTimeInMillis()) {
                                                    index = weatherForecastDailyList.indexOf(forecastDaily);
                                                    break;
                                                }
                                            }
                                            float sunshine = 0.0f;
                                            if (index != -1) {
                                                sunshine = (1 - forecast.getCloudCover()) * 60.0f;
                                            }
                                            if (sunshine < preferences1.getSunshineThresold()) {
                                                i++;
                                            }
                                        }
                                        if (preferences1.getTemperatureSwitch()) {
                                            if (forecast.getTemperature() > preferences1.getTemperatureThresold()) {
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
                                        if (i == 0) {
                                            colors.add(Color.GREEN);
                                        } else {
                                            colors.add(Color.RED);
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
        });
    }

    public static class RePopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private Location location;

        public RePopulateDbAsyncTask(Location location) {
            this.location = location;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseUtils databaseUtils = new DatabaseUtils();

            String dataFromUrl = databaseUtils.getDataFromUrl(location.getLatitude() + "," + location.getLongitude(), MyApplication.getQuery());

            List<WeatherForecast> weatherForecastList = databaseUtils.convertJsonToWeatherForecastList(dataFromUrl);
            WeatherCurrent weatherCurrent = databaseUtils.convertJsonToWeatherCurrent(dataFromUrl);
            List<WeatherForecastDaily> weatherForecastDailyList = databaseUtils.convertJsonToWeatherForecastDaily(dataFromUrl);


            for (WeatherForecast forecast : weatherForecastList) {
                weatherForecastDAO.updateWeatherForecast(forecast);
            }

            for (WeatherForecastDaily forecastDaily : weatherForecastDailyList) {
                weatherForecastDAO.updateWeatherForecastDaily(forecastDaily);
            }

            weatherForecastDAO.updateWeatherCurrent(weatherCurrent);

            return null;
        }
    }
}
