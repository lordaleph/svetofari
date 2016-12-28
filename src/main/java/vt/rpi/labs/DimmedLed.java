package vt.rpi.labs;

import com.pi4j.io.gpio.*;
import vt.rpi.labs.adc.AdcReader;

import static java.lang.Thread.sleep;
import static vt.rpi.labs.adc.AdcReaderImpl.lpad;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class DimmedLed {
    private final static boolean DEBUG = false;

    private static Pin ledPin = RaspiPin.GPIO_17; // Pin, LED

    private final GpioPinDigitalOutput ledOutput;

    private final GpioController gpioController;

    private final AdcReader adcReader;

    public DimmedLed(GpioController gpioController, AdcReader adcReader) {
        this.gpioController = gpioController;
        this.adcReader = adcReader;
        ledOutput = gpioController.provisionDigitalOutputPin(ledPin, "LED", PinState.LOW);
        ledOutput.setShutdownOptions(true, PinState.LOW);
    }

    public void control() {
        System.out.println("Listening...");
        int lastRead = 0;
        int tolerance = 5;
        while (true) {
            boolean trimPotChanged = false;
            int adc = adcReader.read();
            int postAdjust = Math.abs(adc - lastRead);
            if (postAdjust > tolerance) {
                trimPotChanged = true;
                int volume = (int) (adc / 10.23); // [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
                if (DEBUG)
                    System.out.println("read:" + Integer.toString(adc) +
                            " (0x" + lpad(Integer.toString(adc, 16).toUpperCase(), "0", 2) +
                            ", 0&" + lpad(Integer.toString(adc, 2), "0", 8) + ")");
                System.out.println("Volume:" + volume + "%");
                if (volume > 50) {
                    ledOutput.high();
                } else {
                    ledOutput.low();
                }
                lastRead = adc;
            }
            try {
                sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}