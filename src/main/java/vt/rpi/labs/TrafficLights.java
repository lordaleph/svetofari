package vt.rpi.labs;

import com.pi4j.io.gpio.*;

import static java.lang.Thread.sleep;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class TrafficLights {

    private final GpioController gpioController;

    private final GpioPinDigitalOutput green;
    private final GpioPinDigitalOutput yellow;
    private final GpioPinDigitalOutput red;

    /**
     * @param gpioController the {@link GpioController} instance to use for the traffic lights orchestration
     */
    public TrafficLights(GpioController gpioController) {
        this.gpioController = gpioController;
        this.green = gpioController.provisionDigitalOutputPin(
                RaspiPin.GPIO_01, "Green", PinState.HIGH);
        this.yellow = gpioController.provisionDigitalOutputPin(
                RaspiPin.GPIO_02, "Yellow", PinState.HIGH);
        this.red = gpioController.provisionDigitalOutputPin(
                RaspiPin.GPIO_03, "Red", PinState.HIGH);
        green.setShutdownOptions(true, PinState.LOW);
        yellow.setShutdownOptions(true, PinState.LOW);
        red.setShutdownOptions(true, PinState.LOW);
    }

    public void orchestrate() {
        try {
            while (true) {
                System.out.println("GREEN");
                onOff(green);
                System.out.println("YELLOW");
                onOff(yellow);
                System.out.println("RED");
                onOff(red);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onOff(GpioPinDigitalOutput pin) throws InterruptedException {
        pin.high();
        sleep(1000);
        pin.low();
    }
}
