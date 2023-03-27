package kurts.com.fetchjsonarray;
// https://www.elprisenligenu.dk/api/v1/prices/2023/03-18_DK1.json

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FetchJsonCallback {
    TextView textView;
    FetchJsonTask[] tasks = new FetchJsonTask[2];
    MultiThreadReturnData[] multiThreadReturnData = new MultiThreadReturnData[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setText("");


        String[] dates = new String[2];

        Calendar c = Calendar.getInstance();
        dates[0] = DateStringBuilder(c);
        c.add(Calendar.DATE, 1);
        dates[1] = DateStringBuilder(c);
        String region = "DK1";

        String urlBase = "https://www.elprisenligenu.dk/api/v1/prices/";

        for (int n = 0; n < tasks.length; n++) {
            tasks[n] = new FetchJsonTask(this, n);
            tasks[n].execute(urlBase + dates[n] + "_" + region + ".json");
        }


    }

    private String DateStringBuilder(Calendar calendar) {
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        if (month.length() < 2) month = "0" + month;
        if (day.length() < 2) day = "0" + day;

        return year + "/" + month + "-" + day;
    }


    int completedTasks = 0;

    /**
     * Async called from FetchJSonTask.onPostExecute()
     *
     */
    @Override
    public void onSingleTaskComplete(MultiThreadReturnData multiThreadReturnData) {
        this.multiThreadReturnData[completedTasks++] = multiThreadReturnData;
        if (multiThreadReturnData.getJsonArray() == null) {
            StopAllRunningTasks();
            TaskCompletedWithError(multiThreadReturnData);
        } else if (completedTasks == 2) {
            onAllTasksCompletedOk();
        }
    }

    private void StopAllRunningTasks() {
    }

    private void onAllTasksCompletedOk() {
        for (int n = 0; n < tasks.length; n++) {
            JSONArray jsonArray = multiThreadReturnData[n].getJsonArray();
            String s = multiThreadReturnData[n].getThreadId() + " Received " + jsonArray.length() + " items\n";
            s += jsonArray;
            textView.append(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    DataRecord record = new DataRecord(jsonArray.getJSONObject(i));
                    Log.i("kurts", i + ": " + record.getDKK_per_kWh());
                } catch (JSONException | DateTimeParseException e) {
                    Log.i("kurts", i + " "+ e);
                }
            }
        }

    }

    private void TaskCompletedWithError(MultiThreadReturnData multiThreadReturnData) {
        int responseCode;
        try {
            responseCode = multiThreadReturnData.getHttpURLConnection().getResponseCode();
        } catch (IOException e) {
            responseCode = -1;
        }
        String s = multiThreadReturnData.getThreadId() + " Error fetching JSON data. HTTP return code = " + responseCode;
        textView.append(s + "\n");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }
}
