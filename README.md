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

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```
