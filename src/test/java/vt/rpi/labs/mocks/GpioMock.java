package vt.rpi.labs.mocks;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListener;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class GpioMock implements GpioPinDigitalOutput {

    @Override
    public void high() {

    }

    @Override
    public void low() {

    }

    @Override
    public void toggle() {
        System.out.println("BLINK!");
    }

    @Override
    public Future<?> blink(long delay) {
        return null;
    }

    @Override
    public Future<?> blink(long delay, PinState blinkState) {
        return null;
    }

    @Override
    public Future<?> blink(long delay, long duration) {
        System.out.println("blink!");
        return null;
    }

    @Override
    public Future<?> blink(long delay, long duration, PinState blinkState) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, Callable<Void> callback) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, boolean blocking) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, boolean blocking, Callable<Void> callback) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, Callable<Void> callback) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, boolean blocking) {
        return null;
    }

    @Override
    public Future<?> pulse(long duration, PinState pulseState, boolean blocking, Callable<Void> callback) {
        return null;
    }

    @Override
    public void setState(PinState state) {

    }

    @Override
    public void setState(boolean state) {

    }

    @Override
    public boolean isHigh() {
        return false;
    }

    @Override
    public boolean isLow() {
        return false;
    }

    @Override
    public PinState getState() {
        return null;
    }

    @Override
    public boolean isState(PinState state) {
        return false;
    }

    @Override
    public GpioProvider getProvider() {
        return null;
    }

    @Override
    public Pin getPin() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setTag(Object tag) {

    }

    @Override
    public Object getTag() {
        return null;
    }

    @Override
    public void setProperty(String key, String value) {

    }

    @Override
    public boolean hasProperty(String key) {
        return false;
    }

    @Override
    public String getProperty(String key) {
        return null;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return null;
    }

    @Override
    public Map<String, String> getProperties() {
        return null;
    }

    @Override
    public void removeProperty(String key) {

    }

    @Override
    public void clearProperties() {

    }

    @Override
    public void export(PinMode mode) {

    }

    @Override
    public void export(PinMode mode, PinState defaultState) {

    }

    @Override
    public void unexport() {

    }

    @Override
    public boolean isExported() {
        return false;
    }

    @Override
    public void setMode(PinMode mode) {

    }

    @Override
    public PinMode getMode() {
        return null;
    }

    @Override
    public boolean isMode(PinMode mode) {
        return false;
    }

    @Override
    public void setPullResistance(PinPullResistance resistance) {

    }

    @Override
    public PinPullResistance getPullResistance() {
        return null;
    }

    @Override
    public boolean isPullResistance(PinPullResistance resistance) {
        return false;
    }

    @Override
    public Collection<GpioPinListener> getListeners() {
        return null;
    }

    @Override
    public void addListener(GpioPinListener... listener) {

    }

    @Override
    public void addListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public boolean hasListener(GpioPinListener... listener) {
        return false;
    }

    @Override
    public void removeListener(GpioPinListener... listener) {

    }

    @Override
    public void removeListener(List<? extends GpioPinListener> listeners) {

    }

    @Override
    public void removeAllListeners() {

    }

    @Override
    public GpioPinShutdown getShutdownOptions() {
        return null;
    }

    @Override
    public void setShutdownOptions(GpioPinShutdown options) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance) {

    }

    @Override
    public void setShutdownOptions(Boolean unexport, PinState state, PinPullResistance resistance, PinMode mode) {

    }
}
