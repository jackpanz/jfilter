# jfilter
Dynamic filter object properties in the controller.
Support spring mvc and spring boot.
Version requirement.
- spring 4.2+ .
- spring 5.0+ .
- jackson 2.8.0+ .

# spring boot

```
@ComponentScan({"com.bj.json"})
```
```
@Order(0)
@Bean
public JFilterHttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
    JFilterHttpMessageConverter messageConverter = new JFilterHttpMessageConverter(objectMapper);
    return messageConverter;
}
```

# spring mvc

## 11111111111

```
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="com.bj.json.spring4.JFilterHttpMessageConverter">
            <property name="objectMapper">
                <bean class="com.fasterxml.jackson.databind.ObjectMapper">
                    <property name="dateFormat">
                          <bean class="java.text.SimpleDateFormat">
                              <constructor-arg type="java.lang.String" value="yyyy-MM-dd HH:mm:ss" />
                          </bean>
                      </property>
                </bean>
            </property>
            <property name="supportedMediaTypes">
                <list>
                    <value>text/html;charset=UTF-8</value>
                    <value>application/json;charset=UTF-8</value>
                </list>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
<context:component-scan base-package="com.bj.json"/>
```
# Usage
filter only Object
```
@JFilter(clazz = User.class, property = "id,status")
@ResponseBody
@RequestMapping("users")
public List users() {
    return new ArrayList() {{
        add(getUser());
        add(getUser());
        add(getUser());
        add(getUser());
    }};
}
```
filter Multiple Objects

```
@JFilters({
        @JFilter(clazz = User.class, property = "id,status")
        , @JFilter(clazz = Admin.class, property = "password,create_date")
})
@ResponseBody
@RequestMapping("maps")
public List maps() {
    return new ArrayList() {{
        add(getData());
        add(getData());
        add(getData());
        add(getData());
    }};
}
```


