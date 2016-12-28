package vt.rpi.labs;

import com.pi4j.io.gpio.GpioController;
import org.junit.Test;
import vt.rpi.labs.mocks.MockGpioController;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class TrafficLightsTest {

    @Test
    public void orchestrate() throws Exception {
        System.out.println("Testing traffic lights:");
        GpioController mockGpioController = new MockGpioController();
        new TrafficLights(mockGpioController).orchestrate();
    }
}