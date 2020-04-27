package com.weather.air_o_inspect.SeekBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import com.weather.air_o_inspect.R;

@SuppressWarnings("ALL")
public class FloatSeekBarPreference extends Preference {

    private static final String TAG = "SeekBarPreference";
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            Float mSeekBarValue;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            Float mMinValue;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mTrackingTouch;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            SeekBar mSeekBar;
    // Whether the SeekBar should respond to the left/right keys
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mAdjustable;
    // Whether the SeekBarPreference should continuously save the Seekbar value while it is being
    // dragged.
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mUpdatesContinuously;
    private Float mMaxValue;
    private Float mStepValue;
    private TextView mSeekBarValueTextView;
    // Whether to show the SeekBar value TextView next to the bar
    private boolean mShowSeekBarValue;
    /**
     * Listener reacting to the {@link SeekBar} changing value by the user
     */
    private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser && (mUpdatesContinuously || !mTrackingTouch)) {
                syncValueInternal(seekBar);
            } else {
                // We always want to update the text while the seekbar is being dragged
                updateLabelValue((progress / 1000.0f) + mMinValue);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mTrackingTouch = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mTrackingTouch = false;
            if ((seekBar.getProgress() / 1000.0f) + mMinValue != mSeekBarValue) {
                syncValueInternal(seekBar);
            }
        }
    };

    /**
     * Listener reacting to the user pressing DPAD left/right keys if {@code
     * adjustable} attribute is set to true; it transfers the key presses to the {@link SeekBar}
     * to be handled accordingly.
     */
    private View.OnKeyListener mSeekBarKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() != KeyEvent.ACTION_DOWN) {
                return false;
            }

            if (!mAdjustable && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                    || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
                // Right or left keys are pressed when in non-adjustable mode; Skip the keys.
                return false;
            }

            // We don't want to propagate the click keys down to the SeekBar view since it will
            // create the ripple effect for the thumb.
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                return false;
            }

            if (mSeekBar == null) {
                Log.e(TAG, "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
            return mSeekBar.onKeyDown(keyCode, event);
        }
    };

    private FloatSeekBarPreference(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.FloatSeekBarPreference, defStyleAttr, defStyleRes);

        // The ordering of these two statements are important. If we want to set max first, we need
        // to perform the same steps by changing min/max to max/min as following:
        // mMax = a.getInt(...) and setMin(...).
        mMinValue = a.getFloat(R.styleable.FloatSeekBarPreference_minValue, 0.0f);
        setMax(a.getFloat(R.styleable.FloatSeekBarPreference_maxValue, 1.0f));
        setStepValue(a.getFloat(R.styleable.FloatSeekBarPreference_stepValue, 0.001f));
        mAdjustable = a.getBoolean(R.styleable.FloatSeekBarPreference_adjustable, true);
        mShowSeekBarValue = a.getBoolean(R.styleable.FloatSeekBarPreference_showSeekBarValue, false);
        mUpdatesContinuously = a.getBoolean(R.styleable.FloatSeekBarPreference_updatesContinuously,
                false);
        a.recycle();
    }

    private FloatSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FloatSeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public FloatSeekBarPreference(Context context) {
        this(context, null);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        view.itemView.setOnKeyListener(mSeekBarKeyListener);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mSeekBarValueTextView = (TextView) view.findViewById(R.id.seekbar_value);
        mSeekBarValueTextView.setWidth(200);

        if (mShowSeekBarValue) {
            mSeekBarValueTextView.setVisibility(View.VISIBLE);
        } else {
            mSeekBarValueTextView.setVisibility(View.GONE);
            mSeekBarValueTextView = null;
        }

        if (mSeekBar == null) {
            Log.e(TAG, "SeekBar view is null in onBindViewHolder.");
            return;
        }
        mSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mSeekBar.setMax(((Float) ((mMaxValue - mMinValue) * 1000)).intValue());
        // If the increment is not zero, use that. Otherwise, use the default mKeyProgressIncrement
        // in AbsSeekBar when it's zero. This default increment value is set by AbsSeekBar
        // after calling setMax. That's why it's important to call setKeyProgressIncrement after
        // calling setMax() since setMax() can change the increment value.
        if (mStepValue != 0) {
            mSeekBar.setKeyProgressIncrement(((Float) (mStepValue * 1000.0f)).intValue());
        } else {
            mStepValue = mSeekBar.getKeyProgressIncrement() / 1000.0f;
        }

        mSeekBar.setProgress(((Float) ((mSeekBarValue - mMinValue) * 1000.0f)).intValue());
        updateLabelValue(mSeekBarValue);
        mSeekBar.setEnabled(isEnabled());
    }

    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = 0f;
        }
        setValue(getPersistedFloat(Float.parseFloat("" + defaultValue)));
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getFloat(index, 0);
    }

    /**
     * Gets the lower bound set on the {@link SeekBar}.
     *
     * @return The lower bound set
     */
    public Float getMin() {
        return mMinValue;
    }

    /**
     * Sets the lower bound on the {@link SeekBar}.
     *
     * @param min The lower bound to set
     */
    public void setMin(Float min) {
        if (min > mMaxValue) {
            min = mMaxValue;
        }
        if (!min.equals(mMinValue)) {
            mMinValue = min;
            notifyChanged();
        }
    }

    /**
     * Returns the amount of increment change via each arrow key click. This value is derived from
     * user's specified increment value if it's not zero. Otherwise, the default value is picked
     * from the default mKeyProgressIncrement value in {@link android.widget.AbsSeekBar}.
     *
     * @return The amount of increment on the {@link SeekBar} performed after each user's arrow
     * key press
     */
    public final Float getStepValue() {
        return mStepValue;
    }

    /**
     * Sets the increment amount on the {@link SeekBar} for each arrow key press.
     *
     * @param stepValue The amount to increment or decrement when the user presses an
     *                  arrow key.
     */
    public final void setStepValue(Float stepValue) {
        if (!stepValue.equals(mStepValue)) {
            mStepValue = Math.min(mMaxValue - mMinValue, stepValue);
            notifyChanged();
        }
    }

    /**
     * Gets the upper bound set on the {@link SeekBar}.
     *
     * @return The upper bound set
     */
    public Float getMax() {
        return mMaxValue;
    }

    /**
     * Sets the upper bound on the {@link SeekBar}.
     *
     * @param max The upper bound to set
     */
    public final void setMax(Float max) {
        if (max < mMinValue) {
            max = mMinValue;
        }
        if (!max.equals(mMaxValue)) {
            mMaxValue = max;
            notifyChanged();
        }
    }

    /**
     * Gets whether the {@link SeekBar} should respond to the left/right keys.
     *
     * @return Whether the {@link SeekBar} should respond to the left/right keys
     */
    public boolean isAdjustable() {
        return mAdjustable;
    }

    /**
     * Sets whether the {@link SeekBar} should respond to the left/right keys.
     *
     * @param adjustable Whether the {@link SeekBar} should respond to the left/right keys
     */
    public void setAdjustable(boolean adjustable) {
        mAdjustable = adjustable;
    }

    /**
     * Gets whether the {@link SeekBarPreference} should continuously save the {@link SeekBar} value
     * while it is being dragged. Note that when the value is true,
     * {@link Preference.OnPreferenceChangeListener} will be called continuously as well.
     *
     * @return Whether the {@link SeekBarPreference} should continuously save the {@link SeekBar}
     * value while it is being dragged
     * @see #setUpdatesContinuously(boolean)
     */
    public boolean getUpdatesContinuously() {
        return mUpdatesContinuously;
    }

    /**
     * Sets whether the {@link SeekBarPreference} should continuously save the {@link SeekBar} value
     * while it is being dragged.
     *
     * @param updatesContinuously Whether the {@link SeekBarPreference} should continuously save
     *                            the {@link SeekBar} value while it is being dragged
     * @see #getUpdatesContinuously()
     */
    public void setUpdatesContinuously(boolean updatesContinuously) {
        mUpdatesContinuously = updatesContinuously;
    }

    /**
     * Gets whether the current {@link SeekBar} value is displayed to the user.
     *
     * @return Whether the current {@link SeekBar} value is displayed to the user
     * @see #setShowSeekBarValue(boolean)
     */
    public boolean getShowSeekBarValue() {
        return mShowSeekBarValue;
    }

    /**
     * Sets whether the current {@link SeekBar} value is displayed to the user.
     *
     * @param showSeekBarValue Whether the current {@link SeekBar} value is displayed to the user
     * @see #getShowSeekBarValue()
     */
    public void setShowSeekBarValue(boolean showSeekBarValue) {
        mShowSeekBarValue = showSeekBarValue;
        notifyChanged();
    }

    private void setValueInternal(Float seekBarValue, boolean notifyChanged) {
        if (seekBarValue < mMinValue) {
            seekBarValue = mMinValue;
        }
        if (seekBarValue > mMaxValue) {
            seekBarValue = mMaxValue;
        }

        if (!seekBarValue.equals(mSeekBarValue)) {
            mSeekBarValue = seekBarValue;
            updateLabelValue(mSeekBarValue);
            persistFloat(seekBarValue);
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    /**
     * Gets the current progress of the {@link SeekBar}.
     *
     * @return The current progress of the {@link SeekBar}
     */
    public Float getValue() {
        return mSeekBarValue;
    }

    /**
     * Sets the current progress of the {@link SeekBar}.
     *
     * @param seekBarValue The current progress of the {@link SeekBar}
     */
    public void setValue(Float seekBarValue) {
        setValueInternal(seekBarValue, true);
    }

    /**
     * Persist the {@link SeekBar}'s SeekBar value if callChangeListener returns true, otherwise
     * set the {@link SeekBar}'s value to the stored value.
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void syncValueInternal(SeekBar seekBar) {
        float seekBarValue = mMinValue + (seekBar.getProgress() / 1000.0f);
        if (seekBarValue != mSeekBarValue) {
            if (callChangeListener(seekBarValue)) {
                setValueInternal(seekBarValue, false);
            } else {
                seekBar.setProgress(((Float) ((mSeekBarValue - mMinValue) * 1000.0f)).intValue());
                updateLabelValue(mSeekBarValue);
            }
        }
    }

    /**
     * Attempts to update the TextView label that displays the current value.
     *
     * @param value the value to display next to the {@link SeekBar}
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void updateLabelValue(Float value) {
        if (mSeekBarValueTextView != null) {
            mSeekBarValueTextView.setText(String.valueOf(value));
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        // Save the instance state
        final SavedState myState = new SavedState(superState);
        myState.mSeekBarValue = mSeekBarValue;
        myState.mMin = mMinValue;
        myState.mMax = mMaxValue;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!state.getClass().equals(SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mSeekBarValue = myState.mSeekBarValue;
        mMinValue = myState.mMin;
        mMaxValue = myState.mMax;
        notifyChanged();
    }

    /**
     * SavedState, a subclass of {@link BaseSavedState}, will store the state of this preference.
     *
     * <p>It is important to always call through to super methods.
     */
    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        Float mSeekBarValue;
        Float mMin;
        Float mMax;

        SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            mSeekBarValue = source.readFloat();
            mMin = source.readFloat();
            mMax = source.readFloat();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeFloat(mSeekBarValue);
            dest.writeFloat(mMin);
            dest.writeFloat(mMax);
        }
    }
}
