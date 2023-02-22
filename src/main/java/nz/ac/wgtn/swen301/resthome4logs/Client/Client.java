package nz.ac.wgtn.swen301.resthome4logs.Client;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Client {
    public static void main(String[] args) throws Exception {
        if (args.length != 2 || args[1] == null || args[0] == null) {
            System.out.println("This has the following parameters (in this order):\na. type (either csv or excel) -- the type of data requested\nb. fileName -- a local file name used to store the data fetched");
            return;
        }
        String fileName = args[1];
        String type = args[0];
        OkHttpClient client = new OkHttpClient();
        Request request;

        if(type.equals("excel")){
            request = new Request.Builder().get().url("http://localhost:8080/resthome4logs/statsxls").build();
            Files.write(Path.of(fileName), Objects.requireNonNull(client.newCall(request).execute().body()).bytes());
        }
        if(type.equals("csv")){
            request = new Request.Builder().get().url("http://localhost:8080/resthome4logs/statscsv").build();
            Files.write(Path.of(fileName), Objects.requireNonNull(client.newCall(request).execute().body()).bytes());
        }
        else {
            throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
