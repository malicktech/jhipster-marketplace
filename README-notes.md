# Testing

./mvnw clean test

# Client tests

Unit tests are run by Karma and written with Jasmine. They're located in src/test/javascript/ and can be run with:

gulp test

UI end-to-end tests are powered by Protractor, which is built on top of WebDriverJS. They're located in src/test/javascript/e2e
and can be run by starting Spring Boot in one terminal (./mvnw spring-boot:run) and running the tests (gulp itest) in a second one.

# Performance tests

run by Gatling and written in Scala. They're located in src/test/gatling and can be run with:

./mvnw gatling:execute


# Docker Compose
https://jhipster.github.io/docker-compose/

*  fully dockerize the app and all the services that it depends on :

	- Build dev image
	mvn package -Pdev docker:build
	
	- fully dockerize application and all the services that it depends on
	Build image then Run image with Mysql & ElasticSearch


	``` bash
	$ mvn package -Pprod docker:build
	$ docker-compose -f src/main/docker/mysql.yml up -d
	$ docker-compose -f src/main/docker/mysql.yml down
	$ docker-compose -f src/main/docker/app.yml up
	```
	
	sudo docker-compose -f src/main/docker/app.yml down
	
	
	elasticSearch -  publish_address {172.18.0.3:9200}, bound_addresses {[::]:9200}
	mysql - 
	 the app is accessible at :
	 localhost:8080 / 172.18.0.4:8080
	 
	 4. Start & stop mysql database in container
	 > docker-compose -f src/main/docker/mysql.yml up -d
	 > docker-compose -f src/main/docker/mysql.yml down

# clean 
--remove-orphans

# connecting to the mysql docker container 

* use phpmyadmin
https://github.com/phpmyadmin/docker

* use workbench

docker inspect docker_marketplacejhipster-mysql_1
find the forwarded port : $ docker port docker_marketplacejhipster-mysql_1 3306
fing docker machine IP : $ docker-machine ip
from mysql client workbench, COnnect given host & port


# workflow 
running just mysql in docker and the application on the host

* List the containers 
docker container ps -a

* COntainer stats
docker container stats $(docker container ps --format={{.Names}})

# Sonar
https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Maven

1. define sonar.host.url in the GLobal Maven Setting 
2. add sonar-maven-plugin in pom.xml 

A Docker Compose configuration is generated for running Sonar:

	- sudo docker-compose -f src/main/docker/sonar.yml up -d

To analyze your code, run Sonar on your project:

	- With Maven: ./mvnw sonar:sonar

The Sonar reports will be available at (docker_marketplacejhipster-sonar_1): 
	http://localhost:9000
	login : admin/admin



* analyze
/.mvnw sonar:sonar

# CI/CD

https://jhipster.github.io/setting-up-ci/

* Running the sub-generator 
	yo jhipster:ci-cd

- CI/CD : Jenkins pipeline

- tasks/integrations		
	- [OK] Perform the build in a Docker container
	- [OK] Analyze code with Sonar
	- Send build status to Bitbucket
	- [OK]  Build and publish a Docker image to a Docker registry : 
			Docker HUB private regsitry / AWS EC2 Container REgistry / google container registry
			registry-as-a-service providers
	- Deploy to Heroku/VPS/AWS

- Sonar server : default (sonar)

- Docker registry : default (https://registry.hub.docker.com)

- Jenkins Credentials ID : default (docker login)


* test jenkins localy

https://hub.docker.com/_/jenkins/

	docker-compose -f src/main/docker/jenkins.yml up -d

	jenkins will be available at : http://localhost:49001

	docker-compose -f src/main/docker/jenkins.yml up down

sudo chown 1000 ~/Docker_volumes/jenkins_home

# TODO
integration of Docker HUB + Bitbucket + jenkins
https://mail.google.com/mail/u/0/#search/docker/15ba1565ecfb516b

# credentiels

* hub.docker.com
	Docker ID : citizendockerdiop
	mdiop.sne@gmail.com
	aqwzsx123

	private repo : 

# link 
https://hub.docker.com/r/jhipster/jhipster-sample-app/

# requirement 
 quick and simple solution to get started with docker deployment
 
# TODO

* set up Dev Enviromment
	set up local jenkins and sonar : config project with this 
	
Setup production Staging & environment

volume & container data only ?

git push origin master > to deploy in prod
git push origin staging > to deploy in test:staging

 run our tests using maven
 create docker image
 
# DONE to mark
 interface de monitoring  des applis
 interface admin 
 web service crud
 
