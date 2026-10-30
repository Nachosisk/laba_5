import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Main  {

    public static void main(String[] args) {
        // ВСТАВЬТЕ СЮДА ПРЯМУЮ ССЫЛКУ НА MP3
        String urlString = "https://cdnproxy3.mp3xa.me/1XhT3tDzQ-ETJK-Urb29hw/1793398782/L21wMy8yMDIwLzAyL9Cd0LXRgNCy0YsgLSDQndC10YDQstGLLm1wMw";
        String filename = "song.mp3";

        Thread downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Подождите, идет скачивание...");
                try {
                    saveUrl(filename, urlString);
                    System.out.println("Скачивание завершено");
                } catch (IOException ex) {
                    System.out.println("Ошибка при скачивании: " + ex.getMessage());
                }
            }
        });

        downloadThread.start();

        try {
            downloadThread.join(); // Ждем, пока поток докачает
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveUrl(final String filename, final String urlString) throws IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            // В Java 20+ лучше использовать: new URI(urlString).toURL().openStream()
            // Но для простоты оставим так (будет предупреждение, но работать будет)
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte[] data = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) in.close();
            if (fout != null) fout.close();
        }
    }
}