# demo
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

# How to create a docker image:
1. Start Redis: `brew services start redis`
2. Change directory to project directory
3. Run the following command to build an image: `docker build -t demo-app:v1 .`
4. Run the following command to run a container:
<pre>
   docker run \
   -p 8080:8080 \
   -e redisHostName=host.docker.internal \
   -e redisPort=6379 \
   -e dbURL=jdbc:mysql://host.docker.internal:3306/education_management \
   -e dbUserName=root \
   -e dbPassword=admin \
   -e dbDriver=com.mysql.cj.jdbc.Driver \
   -e sqlDialect=org.hibernate.dialect.MySQL8Dialect \
   --rm demo-app:v1
</pre>

<pre>
    docker run \
    - p hostPort:containerPort
    --rm demo-app:v1
</pre>

# How to format code using git-code-format plugin
`mvn git-code-format:format-code -Dgcf.globPattern=**/*`
