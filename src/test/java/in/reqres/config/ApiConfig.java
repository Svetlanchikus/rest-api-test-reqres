package in.reqres.config;

import org.aeonbits.owner.Config;
@ApiConfig.Sources({
        "classpath:config.properties"
})
public interface ApiConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://reqres.in")
    public String getBaseUrl();

    @Key("basePath")
    @DefaultValue("/api")
    public String getBasePath();
}
