# jfilter
Dynamic filter object properties in the controller.
Support spring mvc and spring boot.
Version requirement.
- spring 4.2+ 4.3+ .
- spring 5.0+ .
- jackson 2.8.0+ .

# spring 4.2+ 4.3+
```java
com.github.jackpanz.json.spring4.JFilterHttpMessageConverter
```
# spring 5.0+
```java
com.github.jackpanz.json.spring5.JFilterHttpMessageConverter
```

# spring boot
```java
@ComponentScan({"com.github.jackpanz.json"})
```
```java
@Order(0)
@Bean
public JFilterHttpMessageConverter jFilterHttpMessageConverter(ObjectMapper objectMapper) {
    JFilterHttpMessageConverter messageConverter = new JFilterHttpMessageConverter(objectMapper);
    return messageConverter;
}
```

# spring mvc
```java
<context:component-scan base-package="com.github.jackpanz.json"/>
<mvc:annotation-driven>
    <mvc:message-converters>
        <bean class="com.github.jackpanz.json.spring4.JFilterHttpMessageConverter">
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
    ...
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
   ...
}
```

RestController

```java
@JFilter(clazz = User.class, property = "id,status")
@GetMapping("rmaps")
public List maps() {
    ...
}
```

