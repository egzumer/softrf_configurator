package softrf.softrf;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.runtime.*;
import java.util.HashMap;

@DesignerComponent(
	version = 4,
	versionName = "1.1",
	description = "Developed by egzi using Fast.",
	iconName = "icon.png"
)
public class SoftRF extends AndroidNonvisibleComponent {

  private int tryParseInt(String value, int defaultVal) {
      try {
          return Integer.parseInt(value);
      } catch (NumberFormatException e) {
          return defaultVal;
      }
  }

  private int tryParseInt(String value) {
      return tryParseInt(value, 0);
  }

  public SoftRF(ComponentContainer container) {
    super(container.$form());
  }

  // @SimpleFunction(description = "Returns sum of two integer numbers.")
  // public int SumNumber(int firstNumber, int secondNumber) {
  //   return firstNumber + secondNumber;
  // }
  
  
  private HashMap<String, Integer> devConfig = new HashMap<String, Integer>();
  private String nmeaBuffer;  
  
    /*
   * ******** Getters and Setters ********
   */

  // example for setting a variable value
  // @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_STRING,  // change to what type of variable you have
  //                   defaultValue = SoftRF_NMEAParser.DEFAULT_EXAMPLE_VAR_VALUE)
  // @SimpleProperty(
  //     description = "Test variable")
  // public void ExampleVar(String exampleVar) {
  //   this.exampleVar = exampleVar;
  // }


  @SimpleProperty(
    category = PropertyCategory.BEHAVIOR,
    description = "Command for getting configuration from SoftRF device.")
  public String CmdGetCfg() {
	  return "$PSRFC,?*47";
  }


  //PSRFC,1,0,1,1,1,1,0,2,2,1,0,1,1,4,0,0,0,0,0
  @SimpleEvent(
    description = "PSRFC")
  public void PSRFC(int version, int mode, int protocol, int region, int actype, int alarm, int txpower, int volume, int ledring, 
                    int gnss, int priv, int legacy, int sens, int nmea, int gdl90, int d1090, int stealth, int no_track, int powersave) {
      EventDispatcher.dispatchEvent(this, "PSRFC", version, mode, protocol, region, actype, alarm, txpower, volume, ledring, 
                    gnss, priv, legacy, sens, nmea, gdl90, d1090, stealth, no_track, powersave);
  }

/*

  TO RETREIVE SETTINGS
  --------------------

  Query:    "$PSKVC,?*4E"

  Response: "$PSKVC,<version>,<adapter>,<connection method>,<units>,
                    <radar zoom level>,<data protocol>,<baud rate>,
                    <server name>,<server key>,
                    <screen rotation>,<radar orientation>,
                    <aircrafts database>,<aircraft ID preference>,
                    <view mode>,<voice alarm>,
                    <e-paper anti-ghosting>,<traffic filter>,
                    <power save>,<team member ID>*<checksum><CR><LF>"

  TO APPLY SETTINGS
  -----------------

  Sentence: "$PSKVC,1,
                    <adapter>,<connection method>,<units>,
                    <radar zoom level>,<data protocol>,<baud rate>,
                    <server name>,<server key>,
                    <screen rotation>,<radar orientation>,
                    <aircrafts database>,<aircraft ID preference>,
                    <view mode>,<voice alarm>,
                    <e-paper anti-ghosting>,<traffic filter>,
                    <power save>,<team member ID>*<checksum><CR><LF>"

  Response: dump of new settings followed by system restart

  EXAMPLE OF NMEA SENTENCE
  ------------------------

  $PSKVC,1,0,0,0,2,1,0,,,0,0,0,3,0,0,0,0,0,AABBCC*70

  LIST OF SETTINGS AVAILABLE
  --------------------------

  TBD

  RECOMMENDED DEFAULT SETTINGS
  ----------------------------

  TBD

  OTHER INFORMATION
  -----------------

  https://github.com/lyusupov/SoftRF/wiki/SkyView-settings

 */

  @SimpleProperty(
    category = PropertyCategory.BEHAVIOR,
    description = "Command for getting configuration from SoftRF device.")
  public String CmdGetVisualCfg() {
	  return "$PSKVC,?*4E";
  }

 @SimpleEvent(
    description = "PSKVC")
  public void PSKVC(int units, int radarZoomLevel, int screenRotation, int radarOrientation, int aircraftsDatabase, 
                    int aircraftIdPreference, int viewMode, int ePaperAntiGhosting, int trafficFilter, 
                    String teamMemberId) {
      EventDispatcher.dispatchEvent(this, "PSKVC", units, radarZoomLevel, screenRotation, radarOrientation, 
                                    aircraftsDatabase, aircraftIdPreference, viewMode, ePaperAntiGhosting, 
                                    trafficFilter, teamMemberId);
  }



  //   DESCRIPTION
  // -----------

  // Device ID:       24-bit HEX integer unique ident of SoftRF device
  // Protocol:        integer (Legacy, OGNTP, P3I, FANET, UAT, 1090ES)
  // RX packets:      integer
  // TX packets:      integer
  // Battery voltage: in centi-Volt units (0.01 V)

  // EXAMPLE OF NMEA SENTENCE
  // ------------------------
  // $PSRFH,AABBCC,1,0,0,370*76
  @SimpleEvent(
    description = "PSRFH")
  public void PSRFH(String deviceId, int protocol, int rxPackets, int txPackets, float batteryVoltage) {
      EventDispatcher.dispatchEvent(this, "PSRFH", deviceId, protocol, rxPackets, txPackets, batteryVoltage);
  }

  // Format: $GNGGA,<1>,<2>,<3>,<4>,<5>,<6>,<7>,<8>,<9>,M,<10>,M,< 11>,<12>*xx<CR><LF> 
  // E.g: $GNGGA,072446.00,3130.5226316,N,12024.0937010,E,4,27,0.5,31.924,M,0.000,M,2.0,*44 
  // Field explanation:
  // <0> $GNGGA
  // <1> UTC time, the format is hhmmss.sss
  // <2> Latitude, the format is ddmm.mmmmmmm
  // <3> Latitude hemisphere, N or S (north latitude or south latitude)
  // <4> Longitude, the format is dddmm.mmmmmmm
  // <5> Longitude hemisphere, E or W (east longitude or west longitude)
  // <6> GNSS positioning status: 0 not positioned, 1 single point positioning, 2 differential GPS fixed solution, 4 fixed solution, 5 floating point solution
  // <7> Number of satellites used
  // <8> HDOP level precision factor
  // <9> Altitude
  // <10> The height of the earth ellipsoid relative to the geoid
  // <11> Differential time
  // <12> Differential reference base station label
  @SimpleEvent(
    description = "GNGGA")
  public void GNGGA(String utcTime, String latitude, String latHemisphere, String longitude, String lonHemisphere, int gnssStatus, int numSatellites, float hdop, float altitude, float heightEllipsoid, float diffTime, String diffRefStation) {
      EventDispatcher.dispatchEvent(this, "GNGGA", utcTime, latitude, latHemisphere, longitude, lonHemisphere, gnssStatus, numSatellites, hdop, altitude, heightEllipsoid, diffTime, diffRefStation);
  }

  // Format: $GNRMC,<1>,<2>,<3>,<4>,<5>,<6>,<7>,<8>,<9>,<10>,<11>,< 12>*xx<CR><LF> 
  // E.g: $GNRMC,072446.00,A,3130.5226316,N,12024.0937010,E,0.01,0.00,040620,0.0,E,D*3D 
  // Field explanation:

  // <0> $GNRMC
  // <1> UTC time, the format is hhmmss.sss
  // <2> Positioning status, A=effective positioning, V=invalid positioning
  // <3> Latitude, the format is ddmm.mmmmmmm
  // <4> Latitude hemisphere, N or S (north latitude or south latitude)
  // <5> Longitude, the format is dddmm.mmmmmmm
  // <6> Longitude hemisphere, E or W (east longitude or west longitude)
  // <7> Ground speed
  // <8> Ground heading (take true north as the reference datum)
  // <9> UTC date, the format is ddmmyy (day, month, year)
  // <10> Magnetic declination (000.0~180.0 degrees)
  // <11> Magnetic declination direction, E (east) or W (west)
  // <12> Mode indication (A=autonomous positioning, D=differential, E=estimation, N=invalid data)
  @SimpleEvent(
    description = "GNRMC")
  public void GNRMC(String utcTime, String positionStatus, String latitude, String latHemisphere, String longitude, String lonHemisphere, float groundSpeed, float groundHeading, String utcDate, float magneticDeclination, String magneticDirection, String mode) {
      EventDispatcher.dispatchEvent(this, "GNRMC", utcTime, positionStatus, latitude, latHemisphere, longitude, lonHemisphere, groundSpeed, groundHeading, utcDate, magneticDeclination, magneticDirection, mode);
  }

  // PFLAU,<RX>,<TX>,<GPS>,<Power>,<AlarmLevel>,<RelativeBearing>,
  // <AlarmType>,<RelativeVertical>,<RelativeDistance>[,<ID>]
  // $PFLAU,3,1,2,1,2,-30,2,-32,755*
  @SimpleEvent(
    description = "PFLAU")
  public void PFLAU(int rx, int tx, int gps, int power, int alarmLevel, int relativeBearing, int alarmType, int relativeVertical, int relativeDistance) {
      EventDispatcher.dispatchEvent(this, "PFLAU", rx, tx, gps, power, alarmLevel, relativeBearing, alarmType, relativeVertical, relativeDistance);
  }

  // Altitude (PGRMZ)
  // $PGRMZ,<1>, f, <2>*hh<CR><LF>
  // <1> Current altitude, feet
  // <2> Fix type: 1 = no fix, 2 = 2D fix, 3 = 3D fix 
  @SimpleEvent(
    description = "PGRMZ")
  public void PGRMZ(int altitude, int fixType) {
      EventDispatcher.dispatchEvent(this, "PGRMZ", altitude, fixType);
  }

//   LK8000 EXTERNAL INSTRUMENT SERIES 1 - NMEA SENTENCE: LK8EX1
// VERSION A, 110217

// LK8EX1,pressure,altitude,vario,temperature,battery,*checksum

// Field 0, raw pressure in hPascal:
// 	hPA*100 (example for 1013.25 becomes  101325) 
// 	no padding (987.25 becomes 98725, NOT 098725)
// 	If no pressure available, send 999999 (6 times 9)
// 	If pressure is available, field 1 altitude will be ignored

// Field 1, altitude in meters, relative to QNH 1013.25
// 	If raw pressure is available, this value will be IGNORED (you can set it to 99999
// 	but not really needed)!
// 	(if you want to use this value, set raw pressure to 999999)
// 	This value is relative to sea level (QNE). We are assuming that
// 	currently at 0m altitude pressure is standard 1013.25.
// 	If you cannot send raw altitude, then send what you have but then
// 	you must NOT adjust it from Basic Setting in LK.
// 	Altitude can be negative
// 	If altitude not available, and Pressure not available, set Altitude
// 	to 99999  (5 times 9)
// 	LK will say "Baro altitude available" if one of fields 0 and 1 is available.

// Field 2, vario in cm/s
// 	If vario not available, send 9999  (4 times 9)
// 	Value can also be negative

// Field 3, temperature in C , can be also negative
// 	If not available, send 99

// Field 4, battery voltage or charge percentage
// 	Cannot be negative
// 	If not available, send 999 (3 times 9)
// 	Voltage is sent as float value like: 0.1 1.4 2.3  11.2 
// 	To send percentage, add 1000. Example 0% = 1000
// 	14% = 1014 .  Do not send float values for percentages.
// 	Percentage should be 0 to 100, with no decimals, added by 1000!
@SimpleEvent(
    description = "LK8EX1")
  public void LK8EX1(float pressure, int altitude, float vario, int temperature, float battery) {
      EventDispatcher.dispatchEvent(this, "LK8EX1", pressure, altitude, vario, temperature, battery);
  }


  @SimpleFunction(description = "Consume NMEA data")
  public void ConsumeNMEA(String nmea) {
    nmeaBuffer += nmea;

    int dollarIdx = nmeaBuffer.indexOf('$');
    if (dollarIdx != -1) {
      nmeaBuffer = nmeaBuffer.substring(dollarIdx);
    } else {
      nmeaBuffer = "";
      return;
    }

    int starIdx = nmeaBuffer.indexOf('*');
    if(starIdx != -1 && nmeaBuffer.length() > starIdx + 2) {
      int ncsum = Integer.parseInt(nmeaBuffer.substring(starIdx + 1, starIdx + 3), 16);
      int csum = 0;

      String nmeaSentence = nmeaBuffer.substring(1, starIdx);
      
      for (char c : nmeaSentence.toCharArray()) {
        csum ^= c;
      }
      nmeaBuffer = nmeaBuffer.substring(starIdx + 3);   

      if (csum != ncsum) {
        return; // Invalid NMEA sentence
      }
      

      if (nmeaSentence.startsWith("PSRFC")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 20) {
            int version = tryParseInt(parts[1]);
            int mode = tryParseInt(parts[2]);
            int protocol = tryParseInt(parts[3]);
            int region = tryParseInt(parts[4]);
            int actype = tryParseInt(parts[5]);
            int alarm = tryParseInt(parts[6]);
            int txpower = tryParseInt(parts[7]);
            int volume = tryParseInt(parts[8]);
            int ledring = tryParseInt(parts[9]);
            int gnss = tryParseInt(parts[10]);
            int priv = tryParseInt(parts[11]);
            int legacy = tryParseInt(parts[12]);
            int sens = tryParseInt(parts[13]);
            int nmeaFlag = tryParseInt(parts[14]);
            int gdl90 = tryParseInt(parts[15]);
            int d1090 = tryParseInt(parts[16]);
            int stealth = tryParseInt(parts[17]);
            int no_track = tryParseInt(parts[18]);
            int powersave = tryParseInt(parts[19]);

            devConfig.put("version", version);
            devConfig.put("mode", mode);
            devConfig.put("protocol", protocol);
            devConfig.put("region", region);
            devConfig.put("actype", actype);
            devConfig.put("alarm", alarm);
            devConfig.put("txpower", txpower);
            devConfig.put("volume", volume);
            devConfig.put("ledring", ledring);
            devConfig.put("gnss", gnss);
            devConfig.put("priv", priv);
            devConfig.put("legacy", legacy);
            devConfig.put("sens", sens);
            devConfig.put("nmeaFlag", nmeaFlag);
            devConfig.put("gdl90", gdl90);
            devConfig.put("d1090", d1090);
            devConfig.put("stealth", stealth);
            devConfig.put("no_track", no_track);
            devConfig.put("powersave", powersave);

            PSRFC(version, mode, protocol, region, actype, alarm, txpower, volume, ledring, 
                  gnss, priv, legacy, sens, nmeaFlag, gdl90, d1090, stealth, no_track, powersave);
        }
      } 
      else if (nmeaSentence.startsWith("PSKVC")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 20) {
            // int version = tryParseInt(parts[1]);
            // int adapter = tryParseInt(parts[2]);
            // int connectionMethod = tryParseInt(parts[3]);
            int units = tryParseInt(parts[4]);
            int radarZoomLevel = tryParseInt(parts[5]);
            // int dataProtocol = tryParseInt(parts[6]);
            // int baudRate = tryParseInt(parts[7]);
            // String serverName = parts[8];
            // String serverKey = parts[9];
            int screenRotation = tryParseInt(parts[10]);
            int radarOrientation = tryParseInt(parts[11]);
            int aircraftsDatabase = tryParseInt(parts[12]);
            int aircraftIdPreference = tryParseInt(parts[13]);
            int viewMode = tryParseInt(parts[14]);
            // int voiceAlarm = tryParseInt(parts[15]);
            int ePaperAntiGhosting = tryParseInt(parts[16]);
            int trafficFilter = tryParseInt(parts[17]);
            // int powerSave = tryParseInt(parts[18]);
            String teamMemberId = parts[19];

            PSKVC(units, radarZoomLevel, screenRotation, radarOrientation, aircraftsDatabase, aircraftIdPreference,
                  viewMode, ePaperAntiGhosting, trafficFilter, teamMemberId);
        }
      }
      else if (nmeaSentence.startsWith("PSRFH")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 6) {
            String deviceId = parts[1];
            int protocol = tryParseInt(parts[2]);
            int rxPackets = tryParseInt(parts[3]);
            int txPackets = tryParseInt(parts[4]);
            float batteryVoltage = tryParseInt(parts[5]) / 100.0f; // Convert centi-Volts to Volts

            PSRFH(deviceId, protocol, rxPackets, txPackets, batteryVoltage);
        }
      }     
      // else if (nmeaSentence.startsWith("GNGGA")) {
      //   String[] parts = nmeaSentence.split(",");
      //   if (parts.length >= 13) {
      //       String utcTime = parts[1];
      //       String latitude = parts[2];
      //       String latHemisphere = parts[3];
      //       String longitude = parts[4];
      //       String lonHemisphere = parts[5];
      //       int gnssStatus = Integer.parseInt(parts[6]);
      //       int numSatellites = Integer.parseInt(parts[7]);
      //       float hdop = Float.parseFloat(parts[8]);
      //       float altitude = Float.parseFloat(parts[9]);
      //       float heightEllipsoid = Float.parseFloat(parts[10]);
      //       float diffTime = Float.parseFloat(parts[11]);
      //       String diffRefStation = parts[12];

      //       GNGGA(utcTime, latitude, latHemisphere, longitude, lonHemisphere, gnssStatus, numSatellites, hdop, altitude, heightEllipsoid, diffTime, diffRefStation);
      //   }
      // } else if (nmeaSentence.startsWith("GNRMC")) {
      //   String[] parts = nmeaSentence.split(",");
      //   if (parts.length >= 13) {
      //       String utcTime = parts[1];
      //       String positionStatus = parts[2];
      //       String latitude = parts[3];
      //       String latHemisphere = parts[4];
      //       String longitude = parts[5];
      //       String lonHemisphere = parts[6];
      //       float groundSpeed = Float.parseFloat(parts[7]);
      //       float groundHeading = Float.parseFloat(parts[8]);
      //       String utcDate = parts[9];
      //       float magneticDeclination = Float.parseFloat(parts[10]);
      //       String magneticDirection = parts[11];
      //       String mode = parts[12];

      //       GNRMC(utcTime, positionStatus, latitude, latHemisphere, longitude, lonHemisphere, groundSpeed, groundHeading, utcDate, magneticDeclination, magneticDirection, mode);
      //   }
      // }
      // else if (nmeaSentence.startsWith("PFLAU")) {
      //   String[] parts = nmeaSentence.split(",");
      //   if (parts.length >= 10) {
      //       int rx = Integer.parseInt(parts[1]);
      //       int tx = Integer.parseInt(parts[2]);
      //       int gps = Integer.parseInt(parts[3]);
      //       int power = Integer.parseInt(parts[4]);
      //       int alarmLevel = Integer.parseInt(parts[5]);
      //       int relativeBearing = Integer.parseInt(parts[6]);
      //       int alarmType = Integer.parseInt(parts[7]);
      //       int relativeVertical = Integer.parseInt(parts[8]);
      //       int relativeDistance = Integer.parseInt(parts[9]);

      //       PFLAU(rx, tx, gps, power, alarmLevel, relativeBearing, alarmType, relativeVertical, relativeDistance);
      //   }
      // }
      // else if (nmeaSentence.startsWith("PGRMZ")) {
      //   String[] parts = nmeaSentence.split(",");
      //   if (parts.length >= 4) {
      //       int altitude = Integer.parseInt(parts[1]);
      //       int fixType = Integer.parseInt(parts[2]);

      //       PGRMZ(altitude, fixType);
      //   }
      // }
      // else if (nmeaSentence.startsWith("LK8EX1")) {
      //   String[] parts = nmeaSentence.split(",");
      //   if (parts.length >= 6) {
      //       float pressure = Float.parseFloat(parts[1]) / 100.0f; // Convert hPa to Pa
      //       int altitude = Integer.parseInt(parts[2]);
      //       float vario = Float.parseFloat(parts[3]) / 10.0f; // Convert cm/s to m/s
      //       int temperature = Integer.parseInt(parts[4]);
      //       float battery = Float.parseFloat(parts[5]);

      //       LK8EX1(pressure, altitude, vario, temperature, battery);
      //   }
      // }
    }

  }
  
}
