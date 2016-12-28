package vt.rpi.labs.adc;

import com.pi4j.io.gpio.*;

/**
 * Created by RaspberryPi Labs (VT).
 */
public class AdcReaderImpl implements AdcReader {

    private final static boolean DISPLAY_DIGIT = false;
    private final static boolean DEBUG = false;

    private static int ADC_CHANNEL = 0; // Between 0 and 7, 8 channels on the MCP3008

    private static Pin spiClk = RaspiPin.GPIO_01; // Pin #18, clock
    private static Pin spiMiso = RaspiPin.GPIO_04; // Pin #23, data in.  MISO: Master In Slave Out
    private static Pin spiMosi = RaspiPin.GPIO_05; // Pin #24, data out. MOSI: Master Out Slave In
    private static Pin spiCs = RaspiPin.GPIO_06; // Pin #25, Chip Select

    private final GpioPinDigitalInput misoInput;
    private final GpioPinDigitalOutput mosiOutput;
    private final GpioPinDigitalOutput clockOutput;
    private final GpioPinDigitalOutput chipSelectOutput;

    private final GpioController gpioController;

    public AdcReaderImpl(GpioController gpioController) {
        this.gpioController = gpioController;

        mosiOutput = gpioController.provisionDigitalOutputPin(spiMosi, "MOSI", PinState.LOW);
        clockOutput = gpioController.provisionDigitalOutputPin(spiClk, "CLK", PinState.LOW);
        chipSelectOutput = gpioController.provisionDigitalOutputPin(spiCs, "CS", PinState.LOW);
        misoInput = gpioController.provisionDigitalInputPin(spiMiso, "MISO");
    }

    @Override
    public int read() {
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

    public static String lpad(String str, String with, int len) {
        String s = str;
        while (s.length() < len)
            s = with + s;
        return s;
    }
}
