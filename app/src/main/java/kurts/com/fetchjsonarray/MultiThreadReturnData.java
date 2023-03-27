package kurts.com.fetchjsonarray;

import org.json.JSONArray;

import java.net.HttpURLConnection;

public class MultiThreadReturnData {
    private int threadId;
    private JSONArray jsonArray;
    private HttpURLConnection httpURLConnection;



    public int getThreadId() {
        return threadId;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public HttpURLConnection getHttpURLConnection(){
        return httpURLConnection;
    }



    public MultiThreadReturnData(int taskId,JSONArray jsonArray){
        this.threadId=taskId;
        this.jsonArray=jsonArray;
    }

    public MultiThreadReturnData(int taskId, HttpURLConnection connection) {
        this.threadId=taskId;
            this.httpURLConnection=connection;
    }

    /**
     * Used in case of unknown error
     * @param taskId
     */
    public MultiThreadReturnData(int taskId) {
        this.threadId=taskId;
    }
}
