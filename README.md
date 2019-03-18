# jfilter
Dynamic filter object properties in the controller.
Support spring mvc and spring boot.
Version requirement.
- spring 4.2+ 4.3+ .
- spring 5.0+ .
- jackson 2.8.0+ .

# spring 4.2+ 4.3+
```java
com.bj.json.spring4.JFilterHttpMessageConverter
```
# spring 5.0+
```java
com.bj.json.spring5.JFilterHttpMessageConverter
```

# spring boot
```java
@ComponentScan({"com.bj.json"})
```
```java
@Order(0)
@Bean
public JFilterHttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
    JFilterHttpMessageConverter messageConverter = new JFilterHttpMessageConverter(objectMapper);
    return messageConverter;
}
```

# spring mvc
```java
<context:component-scan base-package="com.bj.json"/>
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="com.bj.json.spring4.JFilterHttpMessageConverter">
            <property name="supportedMediaTypes">
                <list>
                    <value>text/html;charset=UTF-8</value>
                    <value>application/json;charset=UTF-8</value>
                </list>
            </property>
        </bean>
    </mvc:message-converters>
</mvc:annotation-driven>
```
# Usage
Only Object
```java
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
Multiple Object

```java
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

RestController

```java
@JFilter(clazz = User.class, property = "id,status")
@GetMapping("rmaps")
public List maps() {
    return new ArrayList() {{
        add(TController.getData());
        add(TController.getData());
        add(TController.getData());
        add(TController.getData());
    }};
}
```

