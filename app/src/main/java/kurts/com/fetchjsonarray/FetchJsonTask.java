package kurts.com.fetchjsonarray;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@SuppressWarnings("deprecation")
public class FetchJsonTask extends AsyncTask<String , Void, MultiThreadReturnData> {

    private final FetchJsonCallback taskOwner;
    private final int taskId;
//    private MultiThreadReturnData multiThreadReturnData;

    /**
     * Constructor
     */
    public FetchJsonTask(FetchJsonCallback taskOwner,int taskId) {
        this.taskOwner = taskOwner;
        this.taskId =taskId;
    }

    /**
     * Called on FetchJsonTask.Execute()
     * @return MultiThreadReturnData, that is send to this.onPostExecute()
     */
    @Override
    public MultiThreadReturnData doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                return new MultiThreadReturnData(taskId,connection);
            }

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader((connection.getInputStream())));

            StringBuilder stringBuilder = new StringBuilder();
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }

            connection.disconnect();

            String json = stringBuilder.toString();
            return new MultiThreadReturnData(taskId,new JSONArray(json));

        } catch (Exception e) {
            return new MultiThreadReturnData(taskId);
        }
    }

    /**
     * Async called when this.doInBackground() as finished
     */
    @Override
    protected void onPostExecute(MultiThreadReturnData multiThreadReturnData) {
            taskOwner.onSingleTaskComplete(multiThreadReturnData);
    }
}

