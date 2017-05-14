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
	
	$ docker-compose -f src/main/docker/app.yml up -d
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

execute a "SonarQube Scanner" Step within my Jenkins 2.x Pipeline :

https://jhipster.github.io/code-quality/
https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Jenkins
https://github.com/SonarSource/sonar-scanner-maven

Confiure Jenkins global configuration - Manage Jenkins > Configure system > SonarQube servers
Config jenkins scanner - Manage Jenkins > Global Tool Configuration

To analyze your code, run Sonar on your project:

	- With Maven: ./mvnw sonar:sonar

The Sonar reports will be available at (docker_marketplacejhipster-sonar_1): 
	
	Local:
		http://localhost:9100
		login : admin/admin

	VPS:
		http://vps365425.ovh.net:9000
		login : admin/admin



# Jenkins

test jenkins localy

* jenkins will be available at :

	Local:
		url : http://localhost:49001
		login : citizenjenkins / aqwzsx123

	VPS:
		url : http://vps365425.ovh.net:8080
		login : citizendiop/mySNEjenkins123
		
* https://hub.docker.com/_/jenkins/

	docker-compose -f src/main/docker/jenkins.yml up -d
	jenkins will be available at : http://localhost:49001


# CI/CD

Jenkins Pipeline + SonarQube Scanner for Maven

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

# credentiels

* hub.docker.com
	Docker ID : citizendockerdiop
	mdiop.sne@gmail.com
	aqwzsx123

	private repo : 
	
* Bitbucket :
	

# link 
https://hub.docker.com/r/jhipster/jhipster-sample-app/

# requirement 
 quick and simple solution to get started with docker deployment
 
# TODO

integration of Docker HUB + Bitbucket + jenkins
https://mail.google.com/mail/u/0/#search/docker/15ba1565ecfb516b

Setup production Staging & environment

volume & container data only ?

git push origin master > to deploy in prod
git push origin staging > to deploy in test:staging

 run our tests using maven
 create docker image
 
- Set up jhipster console - ELK
https://blog.netapsys.fr/vos-logs-passent-la-seconde-avec-elk-elasticsearch-logstash-kibana/comment-page-1/
https://wooster.checkmy.ws/2014/04/elk-elasticsearch-logstash-kibana/
https://www.monitoring-fr.org/2016/04/ne-dites-plus-elk-mais-the-elastic-stack/
 
# DONE to mark
set up Dev Enviromment
	set up local jenkins and sonar
	add jenkins user to docker
	setup local CI/CD
	
 interface de monitoring  des applis
 interface admin 
 web service crud
 
 # workflow
 
 Lancher le server de dév avec live-relaod :
 > yarn install
 
 Linter le code TypeScript
 > yarn run lint
 > yarn run lint:fix
 
