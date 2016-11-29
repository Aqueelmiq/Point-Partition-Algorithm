import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aqueelmiqdad on 11/24/16.
 */
public class RW {

    public static String filename = "out.txt";

    public static List<Point> read() {

        File file = new File(filename);
        List<Point> points = new ArrayList<>();
        Scanner in;
        try {
            in = new Scanner(file);
            in.nextLine();
            while (in.hasNext()) {
                points.add(new Point(in.nextDouble(), in.nextDouble()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input files ended before 100");
            System.exit(0);
        }

        return points;

    }

    public static List<Point> read(String file) {
        filename = file;
        return read();
    }

    public static void write(List<String> out) {

        File output = new File(filename);

        try {
            if (!output.getParentFile().exists())
                output.getParentFile().mkdirs();
            if (!output.exists())
                output.createNewFile();
            Path path = Paths.get(filename);
            Files.write(path, out, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void write(List<String> out, String path) {
        filename = path;
        write(out);
    }


}
