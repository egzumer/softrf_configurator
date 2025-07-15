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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.math.RoundingMode;
import java.util.Locale;

@DesignerComponent(
	version = 21,
	versionName = "1.2",
	description = "Developed by egzi using Fast.",
	iconName = "icon.png"
)
public class SoftRF extends AndroidNonvisibleComponent {

  private static int tryParseInt(String value, int defaultVal) {
      try {
          return Integer.parseInt(value);
      } catch (NumberFormatException e) {
          return defaultVal;
      }
  }

  private static int tryParseInt(String value) {
      return tryParseInt(value, 0);
  }

  private static float tryParseFloat(String value, float defaultVal) {
      try {
          return Float.parseFloat(value);
      } catch (NumberFormatException e) {
          return defaultVal;
      }
  }

  private static float tryParseFloat(String value) {
      return tryParseFloat(value, 0);
  }

  @SimpleFunction(description = "Returns plane SVG of aplane rotated by direction angle.")
  public static String GetPlaneSVGIcon(int angle) {
      String svgPrefix = "<svg fill=\"#000000\" width=\"30px\" height=\"30px\" viewBox=\"0 0 22 32\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" + //
                "<title>plane</title>\n" + //
                "<path d=\"";
      String path = "M6.528 10.12 l0.023 0.663  l2.98 -2.691  l-0.119 -3.401 c-0.019 -0.53  0.039 -1.417  0.264 -2.575 s0.535 -1.766  0.888 -1.778 c0.972 -0.034  1.485 1.385 1.565 4.28 l0.117 3.357  3.16 2.476  -0 -0.642 c-0.041 -0.551  0.236 -0.848  0.677 -0.864  0.53 -0.019  0.812 0.458 0.849 1.518 l0.06 1.081  2.222 1.559  -0.05 -0.772 c-0.04 -0.507  0.216 -0.782  0.679 -0.82  0.53 -0.019  0.831 0.347 0.855 1.054 l0.056 1.59 c2.493 1.726  3.77 2.832  3.786 3.317 -0.015 0.199 -0.073 0.445 -0.24 0.717 -0.169 0.227 -0.362 0.388 -0.561 0.418 -0.199 -0.015 -0.739 -0.261 -1.576 -0.786 -0.881 -0.522 -2.477 -1.285 -4.854 -2.218 -2.378 -0.978 -3.809 -1.414 -4.273 -1.421 -0.177 0.006 -0.305 0.144 -0.362 0.388 -0.081 0.224 -0.133 1.243 -0.094 2.988 0.062 1.767 0.005 3.294 -0.17 4.627 0.116 0.151  0.638 0.508  1.498 1.097 s1.407 1.012  1.568 1.205 c0.14 0.217 0.221 0.634 0.241 1.208 0.011 0.31 -0.004 0.509 -0.088 0.644 -0.574 0.02 -1.96 -0.418 -4.113 -1.271 -2.353 1.011 -3.707 1.5 -4.06 1.512  -0.091 -0.085  -0.144 -0.305  -0.154 -0.614  -0.02 -0.574  0.032 -0.996  0.2 -1.223  0.124 -0.226  0.641 -0.641  1.482 -1.268  0.817 -0.648  1.29 -1.062  1.439 -1.222  -0.311 -1.316  -0.564 -3.473  -0.671 -6.566  -0.077 -0.926  -0.268 -1.362  -0.578 -1.351 -0.465 -0.006 -1.86 0.574 -4.142 1.693 -2.303 1.142 -3.868 1.948 -4.687 2.552 -0.842 0.582 -1.34 0.887 -1.54 0.872 -0.198 0.029 -0.402 -0.118 -0.588 -0.378  -0.208 -0.236  -0.282 -0.476  -0.29 -0.698  -0.015 -0.442  1.158 -1.654  3.545 -3.573 l-0.077 -1.567 c-0.002 -0.686  0.273 -1.071  0.803 -1.089  0.464 -0.038  0.717 0.24 0.733 0.726 l0.004 0.774  2.107 -1.71  -0.014 -1.039 c-0.037 -1.06  0.211 -1.555  0.741 -1.573  0.486 -0.017  0.717 0.24 0.757 0.792z";
      String svgPostfix = "\"></path></svg>";

      Pattern pattern = Pattern.compile("(?:([MLHVCSQTAmlhvcsqta]?)([+-]?(?:[0-9]*[.])?[0-9]+) ?([+-]?(?:[0-9]*[.])?[0-9]+))");
      Matcher matcher = pattern.matcher(path);

      StringBuffer result = new StringBuffer();
      String cmd = "";
      while(matcher.find()) {
          if(!matcher.group(1).equals(""))
              cmd = matcher.group(1);
          double x = Float.parseFloat(matcher.group(2));
          double y = Float.parseFloat(matcher.group(3));
          double cx = Character.isLowerCase(cmd.charAt(0))?0:11;
          double cy = Character.isLowerCase(cmd.charAt(0))?0:16;
          double x1 = Math.cos(angle* Math.PI / 180) * (x - cx) - Math.sin(angle* Math.PI / 180) * (y - cy) + cx;
          double y1 = Math.sin(angle* Math.PI / 180) * (x - cx) + Math.cos(angle* Math.PI / 180) * (y - cy) + cy;
          DecimalFormat formatter = new DecimalFormat("#.###", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
          formatter.setRoundingMode( RoundingMode.HALF_UP );
          String replacement = " " + matcher.group(1) + formatter.format(x1) + " " + formatter.format(y1);
          matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
      }
      matcher.appendTail(result);
      return svgPrefix + result.toString() + svgPostfix;
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
  public void GNGGA(String utcTime, float latitude, float longitude, int gnssStatus, int numSatellites, float hdop, float altitude, float heightEllipsoid, float diffTime, String diffRefStation) {
      EventDispatcher.dispatchEvent(this, "GNGGA", utcTime, latitude, longitude, gnssStatus, numSatellites, hdop, altitude, heightEllipsoid, diffTime, diffRefStation);
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
  public void GNRMC(String utcTime, String positionStatus, float latitude, float longitude, float groundSpeed, float groundHeading, String utcDate, float magneticDeclination, String magneticDirection, String mode) {
      EventDispatcher.dispatchEvent(this, "GNRMC", utcTime, positionStatus, latitude, longitude, groundSpeed, groundHeading, utcDate, magneticDeclination, magneticDirection, mode);
  }

  // PFLAU,<RX>,<TX>,<GPS>,<Power>,<AlarmLevel>,<RelativeBearing>,
  // <AlarmType>,<RelativeVertical>,<RelativeDistance>[,<ID>]
  // $PFLAU,3,1,2,1,2,-30,2,-32,755*
  @SimpleEvent(
    description = "PFLAU")
  public void PFLAU(int rx, int tx, int gps, int power, int alarmLevel, int relativeBearing, int alarmType, int relativeVertical, int relativeDistance) {
      EventDispatcher.dispatchEvent(this, "PFLAU", rx, tx, gps, power, alarmLevel, relativeBearing, alarmType, relativeVertical, relativeDistance);
  }

// PFLAA,<AlarmLevel>,<RelativeNorth>,<RelativeEast>,
// <RelativeVertical>,<IDType>,<ID>,<Track>,<TurnRate>,<GroundSpeed>,
// <ClimbRate>,<AcftType>[,<NoTrack>[,<Source>,<RSSI>]]

// <AlarmLevel> Decimal integer value. Range: from 0 to 3.
// Alarm level as assessed by FLARM:
// 0 = no alarm (also used for no-alarm traffic information)
// 1 = alarm, 15-20 seconds to impact
// 2 = alarm, 10-15 seconds to impact
// 3 = alarm, 0-10 seconds to impact
// <RelativeNorth> Decimal integer value. Range: from -20000000 to 20000000.
// Relative position in meters true north from own position. If
// <RelativeEast> is empty, <RelativeNorth> represents the
// estimated distance to a target with unknown bearing
// (transponder Mode-C/S).
// <RelativeEast> Decimal integer value. Range: from -20000000 to 20000000.
// Relative position in meters true east from own position. The
// field is empty for non-directional targets.
// <RelativeVertical> Decimal integer value. Range: from -32768 to 32767.
// Relative vertical separation in meters above own position.
// Negative values indicate that the other aircraft is lower.
// Some distance-dependent random noise is applied to altitude
// data if stealth mode is activated either on the target or own
// aircraft and no alarm is present at this time.
// <IDType> Decimal integer value. Range: from 0 to 2.
// Defines the interpretation of the following <ID> field.
// 0 = random ID, configured or if stealth mode is activated
// either on the target or own aircraft
// 1 = official ICAO 24-bit aircraft address
// 2 = fixed FLARM ID (chosen by FLARM)
// The field is empty if no identification is known (e.g.
// transponder Mode-C).
// <ID> 6-digit hexadecimal value (e.g. “5A77B1”) as configured in
// the target’s PFLAC,,ID sentence. The interpretation is
// delivered in <ID-Type>.
// The field is empty if no identification is known (e.g.
// Transponder Mode-C). Random ID will be sent if stealth mode
// is activated either on the target or own aircraft and no alarm
// is present at this time.
// <Track> Decimal integer value. Range: from 0 to 359.
// The target’s true ground track in degrees. The value 0
// indicates a true north track. This field is empty if stealth
// mode is activated either on the target or own aircraft and for
// non-directional targets.
// <TurnRate> Currently this field is empty.
// <GroundSpeed> Decimal integer value. Range: from 0 to 32767.
// When the aircraft is considered moving, the target’s ground
// speed in m/s, forced to > 0.
// When the aircraft is considered on the ground, the field is
// forced to 0.
// This field is empty if stealth mode is activated either on the
// target or own aircraft and for non-directional targets.
// <ClimbRate> Decimal fixed-point number with one digit after the radix
// point (dot). Range: from -32.7 to 32.7.
// The target’s climb rate in m/s. Positive values indicate a
// climbing aircraft. This field is empty if stealth mode is
// activated either on the target or own aircraft and for nondirectional targets.
// <AcftType> Hexadecimal value. Range: from 0 to F.
// Aircraft types:
// 0 = (reserved)
// 1 = glider/motor glider (turbo, self-launch, jet) / TMG
// 2 = tow plane/tug plane
// 3 = helicopter/gyrocopter/rotorcraft
// 4 = skydiver, parachute (do not use for drop plane!)
// 5 = drop plane for skydivers
// 6 = hang glider (hard)
// 7 = paraglider (soft)
// 8 = aircraft with reciprocating engine(s)
// 9 = aircraft with jet/turboprop engine(s)
// A = unknown
// B = balloon (hot, gas, weather, static)
// C = airship, blimp, zeppelin
// D = unmanned aerial vehicle (UAV, RPAS, drone)
// E = (reserved)
// F = static obstacle
// <NoTrack> Field is omitted if data port version <8.
// The target’s configured no track setting.
// Decimal integer value. Range: from 0 to 1.
// 0 = no track option not set
// 1 = no track option set
// Targets with “no track” enabled express their intention to
// remain private. Data from these targets may thus not be
// persisted in any way (e.g. in a database). If the data is
// transmitted to a third-party system (e.g. a server), then the
// implementer must make sure the third-party system also
// respects this rule.
// Such targets will furthermore be suppressed from $PFLAA
// output if ownship does not move, unless the target is closer
// than 200m horizontally and 100m vertically.
// <Source> The field is omitted if data port version <9.
// Data source of the $PFLAA sentence:
// 0 = FLARM
// 1 = ADS-B
// 3 = ADS-R (rebroadcasting of UAT ADS-B to 1090 MHz)
// 4 = TIS-B (broadcast of location of non-ADS-B equipped
// aircraft)
// 6 = Mode-S (non-directional targets)
// If the same target is received from multiple sources, the
// following precedence applies: FLARM > ADS-B > ADS-R >
// TIS-B > Mode-S.
// For ADS-R and TIS-B, no alarm is computed (AlarmLevel =
// 0).
// Note: ADS-R and TIS-B position reports may be inaccurate
// due to a low update rate and/or extrapolation. Use for
// indicative display only.
// Note: ADS-R and TIS-B targets are sent by the ANSP only to
// so-called ADS-R and TIS-B clients. If ownship is not set up to
// act as such a client, this information may not be sent. Refer
// to DO-338 for details.
// <RSSI> The field is omitted if data port version <9.
// Signal level of the received target in dBm (example: “-71.2”).
// Empty if unknown.
// This field can be used to help assess the quality of the radio
// link. It depends on the installation of the sending station, the
// installation of the receiving station and the distance.
// Example:
// $PFLAA,0,-1234,1234,220,2,DD8F12,180,,30,-1.4,1*

  @SimpleEvent(
    description = "PFLAA")
  public void PFLAA(int alarmLevel, int relativeNorth, int relativeEast, int relativeVertical, int idType, String id, 
                    int track, /*float turnRate,*/ float groundSpeed, float climbRate, String acftType, 
                    int noTrack, String source, float rssi) {
      EventDispatcher.dispatchEvent(this, "PFLAA", alarmLevel, relativeNorth, relativeEast, relativeVertical, idType, id,
                                    track, /*turnRate,*/ groundSpeed, climbRate, acftType, noTrack, source, rssi);
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
      int ncsum;
      try {
          ncsum = Integer.parseInt(nmeaBuffer.substring(starIdx + 1, starIdx + 3), 16);
      } catch (NumberFormatException e) {
          ncsum = -1;
      }

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
      else if (nmeaSentence.startsWith("GNGGA")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 13) {
            String utcTime = parts[1];
            // Convert latitude from ddmm.mmmmmmm to decimal degrees
            float rawLat = tryParseFloat(parts[2]);
            String latHemisphere = parts[3];
            float latitude = 0.0f;
            if (rawLat != 0.0f) {
              int latDegrees = (int)(rawLat / 100);
              float latMinutes = rawLat - (latDegrees * 100);
              latitude = latDegrees + (latMinutes / 60.0f);
              if ("S".equalsIgnoreCase(latHemisphere)) {
                latitude = -latitude;
              }
            }
            float rawLon = tryParseFloat(parts[4]);
            String lonHemisphere = parts[5];
            float longitude = 0.0f;
            if (rawLon != 0.0f) {
              int lonDegrees = (int)(rawLon / 100);
              float lonMinutes = rawLon - (lonDegrees * 100);
              longitude = lonDegrees + (lonMinutes / 60.0f);
              if ("W".equalsIgnoreCase(lonHemisphere)) {
                longitude = -longitude;
              }
            }
            int gnssStatus = tryParseInt(parts[6]);
            int numSatellites = tryParseInt(parts[7]);
            float hdop = tryParseFloat(parts[8]);
            float altitude = tryParseFloat(parts[9]);
            float heightEllipsoid = tryParseFloat(parts[10]);
            float diffTime = tryParseFloat(parts[11]);
            String diffRefStation = parts[12];

            GNGGA(utcTime, latitude, longitude, gnssStatus, numSatellites, hdop, altitude, heightEllipsoid, diffTime, diffRefStation);
        }
      } 
      else if (nmeaSentence.startsWith("GNRMC")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 13) {
            String utcTime = parts[1];
            String positionStatus = parts[2];

            // Convert latitude from ddmm.mmmmmmm to decimal degrees
            float rawLat = tryParseFloat(parts[3]);
            String latHemisphere = parts[4];
            float latitude = 0.0f;
            if (rawLat != 0.0f) {
              int latDegrees = (int)(rawLat / 100);
              float latMinutes = rawLat - (latDegrees * 100);
              latitude = latDegrees + (latMinutes / 60.0f);
              if ("S".equalsIgnoreCase(latHemisphere)) {
                latitude = -latitude;
              }
            }
            float rawLon = tryParseFloat(parts[5]);
            String lonHemisphere = parts[6];
            float longitude = 0.0f;
            if (rawLon != 0.0f) {
              int lonDegrees = (int)(rawLon / 100);
              float lonMinutes = rawLon - (lonDegrees * 100);
              longitude = lonDegrees + (lonMinutes / 60.0f);
              if ("W".equalsIgnoreCase(lonHemisphere)) {
                longitude = -longitude;
              }
            }

            float groundSpeed = tryParseFloat(parts[7]);
            float groundHeading = tryParseFloat(parts[8]);
            String utcDate = parts[9];
            float magneticDeclination = tryParseFloat(parts[10]);
            String magneticDirection = parts[11];
            String mode = parts[12];

            GNRMC(utcTime, positionStatus, latitude, longitude, groundSpeed, groundHeading, utcDate, magneticDeclination, magneticDirection, mode);
        }
      }
      else if (nmeaSentence.startsWith("PFLAU")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 10) {
            int rx = tryParseInt(parts[1]);
            int tx = tryParseInt(parts[2]);
            int gps = tryParseInt(parts[3]);
            int power = tryParseInt(parts[4]);
            int alarmLevel = tryParseInt(parts[5]);
            int relativeBearing = tryParseInt(parts[6]);
            int alarmType = tryParseInt(parts[7]);
            int relativeVertical = tryParseInt(parts[8]);
            int relativeDistance = tryParseInt(parts[9]);

            PFLAU(rx, tx, gps, power, alarmLevel, relativeBearing, alarmType, relativeVertical, relativeDistance);
        }
      }
      else if (nmeaSentence.startsWith("PFLAA")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 14) {
            int alarmLevel = tryParseInt(parts[1]);
            int relativeNorth = tryParseInt(parts[2]);
            int relativeEast = tryParseInt(parts[3]);
            int relativeVertical = tryParseInt(parts[4]);
            int idType = tryParseInt(parts[5]);
            String id = parts[6];
            int track = tryParseInt(parts[7]);
            //float turnRate = tryParseFloat(parts[8]);
            float groundSpeed = tryParseFloat(parts[9]);
            float climbRate = tryParseFloat(parts[10]);
            String acftType = parts[11];
            int noTrack = parts.length > 12 ? tryParseInt(parts[12]) : 0;
            String source = parts.length > 13 ? parts[13] : "";
            float rssi = parts.length > 14 ? tryParseFloat(parts[14]) : 0.0f;

            PFLAA(alarmLevel, relativeNorth, relativeEast, relativeVertical, idType, id, track, /*turnRate,*/ groundSpeed, climbRate, acftType, noTrack, source, rssi);
        }
      }
      else if (nmeaSentence.startsWith("PGRMZ")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 4) {
            int altitude = tryParseInt(parts[1]);
            int fixType = tryParseInt(parts[2]);

            PGRMZ(altitude, fixType);
        }
      }
      else if (nmeaSentence.startsWith("LK8EX1")) {
        String[] parts = nmeaSentence.split(",");
        if (parts.length >= 6) {
            float pressure = tryParseFloat(parts[1]) / 100.0f; // Convert hPa to Pa
            int altitude = tryParseInt(parts[2]);
            float vario = tryParseFloat(parts[3]) / 10.0f; // Convert cm/s to m/s
            int temperature = tryParseInt(parts[4]);
            float battery = tryParseFloat(parts[5]);

            LK8EX1(pressure, altitude, vario, temperature, battery);
        }
      }
    }

  }
  
}
