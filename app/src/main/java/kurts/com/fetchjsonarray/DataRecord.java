package kurts.com.fetchjsonarray;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
"DKK_per_kWh":0.29613,
"EUR_per_kWh":0.03966,
"EXR":7.46671,
"time_start":"2023-03-26T00:00:00+01:00",
"time_end":"2023-03-26T01:00:00+01:00"
 */
public class DataRecord {
    private final double DKK_per_kWh;
    private final double EUR_per_kWh;
    private final double EXR;
    private final LocalDateTime time_start;
    private final LocalDateTime time_end;

    public DataRecord(JSONObject jsonObject) throws JSONException,java.time.format.DateTimeParseException {
        this.DKK_per_kWh = Double.parseDouble(jsonObject.getString("DKK_per_kWh"));
        this.EUR_per_kWh = Double.parseDouble(jsonObject.getString("EUR_per_kWh"));
        this.EXR = Double.parseDouble(jsonObject.getString("EXR"));
        this.time_start=LocalDateTime.parse(jsonObject.getString("time_start"), DateTimeFormatter.ISO_DATE_TIME);
        this.time_end=LocalDateTime.parse(jsonObject.getString("time_end"), DateTimeFormatter.ISO_DATE_TIME);
    }

    public double getDKK_per_kWh() {
        return DKK_per_kWh;
    }

    public double getEUR_per_kWh() {
        return EUR_per_kWh;
    }

    public double getEXR() {
        return EXR;
    }

    public LocalDateTime getTime_start() {
        return time_start;
    }

    public LocalDateTime getTime_end() {
        return time_end;
    }
}
