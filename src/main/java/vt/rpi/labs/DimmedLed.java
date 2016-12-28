package vt.rpi.labs;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

import static com.pi4j.io.gpio.PinPullResistance.*;
import static com.pi4j.io.gpio.RaspiBcmPin.GPIO_23;
import static com.pi4j.io.gpio.RaspiPin.GPIO_02;
import static com.pi4j.io.gpio.RaspiPin.GPIO_24;
import static com.pi4j.wiringpi.Gpio.wiringPiSetup;
import static com.pi4j.wiringpi.SoftPwm.softPwmCreate;
import static com.pi4j.wiringpi.SoftPwm.softPwmWrite;
import static java.lang.Thread.sleep;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class DimmedLed {
    private static int ADC_CHANNEL = 0; // Between 0 and 7, 8 channels on the MCP3008
    private final static boolean DISPLAY_DIGIT = false;
    private final static boolean DEBUG = false;

    private static Pin spiClk = RaspiPin.GPIO_01; // Pin #18, clock
    private static Pin spiMiso = RaspiPin.GPIO_04; // Pin #23, data in.  MISO: Master In Slave Out
    private static Pin spiMosi = RaspiPin.GPIO_05; // Pin #24, data out. MOSI: Master Out Slave In
    private static Pin spiCs = RaspiPin.GPIO_06; // Pin #25, Chip Select

    private static Pin ledPin = RaspiPin.GPIO_02; // Pin, LED

    private final GpioPinDigitalInput misoInput;
    private final GpioPinDigitalOutput mosiOutput;
    private final GpioPinDigitalOutput clockOutput;
    private final GpioPinDigitalOutput chipSelectOutput;

    private final GpioPinDigitalOutput ledOutput;

    private final GpioController gpioController;

    public DimmedLed(GpioController gpioController) {
        this.gpioController = gpioController;
        mosiOutput = gpioController.provisionDigitalOutputPin(spiMosi, "MOSI", PinState.LOW);
        clockOutput = gpioController.provisionDigitalOutputPin(spiClk, "CLK", PinState.LOW);
        chipSelectOutput = gpioController.provisionDigitalOutputPin(spiCs, "CS", PinState.LOW);
        misoInput = gpioController.provisionDigitalInputPin(spiMiso, "MISO");

        ledOutput = gpioController.provisionDigitalOutputPin(ledPin, "LED", PinState.LOW);
        ledOutput.setShutdownOptions(true, PinState.LOW);
    }

    public void control() {
        System.out.println("Listening...");
        int lastRead = 0;
        int tolerance = 5;
        while (true) {
            boolean trimPotChanged = false;
            int adc = readAdc();
            int postAdjust = Math.abs(adc - lastRead);
            if (postAdjust > tolerance) {
                trimPotChanged = true;
                int volume = (int) (adc / 10.23); // [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
                if (DEBUG)
                    System.out.println("readAdc:" + Integer.toString(adc) +
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

    private int readAdc() {
        chipSelectOutput.high();

        clockOutput.low();
        chipSelectOutput.low();

        int adccommand = ADC_CHANNEL;
        adccommand |= 0x18; // 0x18: 00011000
        adccommand <<= 3;
        // Send 5 bits: 8 - 3. 8 input channels on the MCP3008.
        for (int i = 0; i < 5; i++) //
        {
            if ((adccommand & 0x80) != 0x0) // 0x80 = 0&10000000
                mosiOutput.high();
            else
                mosiOutput.low();
            adccommand <<= 1;
            clockOutput.high();
            clockOutput.low();
        }

        int adcOut = 0;
        for (int i = 0; i < 12; i++) // Read in one empty bit, one null bit and 10 ADC bits
        {
            clockOutput.high();
            clockOutput.low();
            adcOut <<= 1;

            if (misoInput.isHigh()) {
                //      System.out.println("    " + misoInput.getName() + " is high (i:" + i + ")");
                // Shift one bit on the adcOut
                adcOut |= 0x1;
            }
            if (DISPLAY_DIGIT)
                System.out.println("ADCOUT: 0x" + Integer.toString(adcOut, 16).toUpperCase() +
                        ", 0&" + Integer.toString(adcOut, 2).toUpperCase());
        }
        chipSelectOutput.high();

        adcOut >>= 1; // Drop first bit
        return adcOut;
    }

    private static String lpad(String str, String with, int len) {
        String s = str;
        while (s.length() < len)
            s = with + s;
        return s;
    }
}