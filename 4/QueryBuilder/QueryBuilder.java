package QueryBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QueryBuilder {
    private final StringBuilder url;
    private boolean hasQuery;

    public QueryBuilder(String baseURL) {
        this.url = new StringBuilder(baseURL);
        this.hasQuery = false;
    }

    public void addQuery(String key, String value) throws UnsupportedEncodingException {
        if (!hasQuery) {
            this.url.append("?");
            hasQuery = true;
        } else {
            this.url.append("&");
        }
        this.encode(key, value);
    }

    private void encode(String key, String value) throws UnsupportedEncodingException {
        this.url.append(URLEncoder.encode(key, StandardCharsets.UTF_8.toString()));
        this.url.append("=");
        this.url.append(URLEncoder.encode(value, StandardCharsets.UTF_8.toString()));
    }

    @Override
    public String toString() {
        return this.url.toString();
    }

    public URL toURL() throws MalformedURLException {
        return new URL(this.toString());
    }

}
