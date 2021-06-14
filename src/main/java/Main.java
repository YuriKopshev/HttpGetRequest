import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //task1
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final ObjectMapper mapper = new ObjectMapper();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        try {
            CloseableHttpResponse response = httpclient.execute(request);
            List<Cat> catList = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
            });
            List<Cat> finalCatList = catList.stream().filter(value -> value.getUpvotes() != null).collect(Collectors.toList());
            finalCatList.forEach(System.out::println);
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //task2 jpeg from NASA
        final CloseableHttpClient httpclient2 = HttpClients.createDefault();
        final ObjectMapper mapper2 = new ObjectMapper();
        HttpGet request2 = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=oF7XZzU9BM1ojgQ2MsdU1WgAbcYVMR7MGysx40dX");
        try {
            CloseableHttpResponse response = httpclient2.execute(request2);
            Nasa instance = mapper2.readValue(response.getEntity().getContent(), new TypeReference<Nasa>() {
            });
            String url = instance.getUrl();
            String[] data = url.split("/");
            String filename = data[data.length - 1];
            URL url1 = new URL(url);
            InputStream inputStream = url1.openStream();
            Path path = Path.of(filename);
            Files.copy(inputStream, path);
            inputStream.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
