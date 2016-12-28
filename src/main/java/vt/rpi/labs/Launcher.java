package vt.rpi.labs;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import vt.rpi.labs.adc.AdcReaderImpl;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class Launcher {


    public static void main(String[] args) {
        GpioController gpioController = GpioFactory.getInstance();
//        TrafficLights trafficLights = new TrafficLights(gpioController);
//        trafficLights.orchestrate();

        AdcReaderImpl adcReader = new AdcReaderImpl(gpioController);
        new DimmedLed(gpioController, adcReader).control();
        gpioController.shutdown();
    }
}
