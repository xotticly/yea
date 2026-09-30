package com.example.settings;

public class NumberSetting extends Setting {
    private double value, min, max, step;

    public NumberSetting(String name, double defaultValue, double min, double max, double step) {
        super(name);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double getValue() { return value; }
    public float getFloat() { return (float) value; }
    public int getInt() { return (int) value; }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getStep() { return step; }

    public void setValue(double v) {
        v = Math.max(min, Math.min(max, v));
        value = Math.round(v / step) * step;
        value = Math.round(value * 100.0) / 100.0;
    }

    public double getProgress() { return (value - min) / (max - min); }
}
