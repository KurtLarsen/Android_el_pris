package kurts.com.fetchjsonarray;

import java.net.HttpURLConnection;

public class MultiThreadError extends Throwable {
    private int taskId;
    private HttpURLConnection httpURLConnection;
    public MultiThreadError(int taskId, HttpURLConnection httpURLConnection) {
        this.taskId=taskId;
        this.httpURLConnection=httpURLConnection;
    }
}
