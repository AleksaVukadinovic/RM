package QueryBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class QueryBuilderTest {
    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base url: ");
        String baseUrl = sc.nextLine();

        QueryBuilder queryBuilder = new QueryBuilder(baseUrl);

        System.out.println("Enter headers in form <key> <value> or \"exit\" ");
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.equalsIgnoreCase("exit"))
                break;

            String[] parts = line.split(" ", 2);
            queryBuilder.addQuery(parts[0], parts[1]);
        }
        System.out.println(queryBuilder);
        System.out.println(queryBuilder.toURL());
    }
}
