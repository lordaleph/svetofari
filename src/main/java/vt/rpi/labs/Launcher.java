package vt.rpi.labs;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class Launcher {


    public static void main(String[] args) {
        GpioController gpioController = GpioFactory.getInstance();
//        TrafficLights trafficLights = new TrafficLights(gpioController);
//        trafficLights.orchestrate();
        new DimmedLed(gpioController).control();
        gpioController.shutdown();
    }
}
