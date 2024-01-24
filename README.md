# API-Security-Testing


### Prerequisites

1. Docker should be installed : https://docs.docker.com/get-docker/

2. API spec for active scans(openAPI, soap & graphQL types are allowed)


### Steps for execution

1. Clone the repository on your local system

2. Open the cloned project in IDE

3. Update the option.prop file to set basic authentication for secure APIs scan(if required)

4. For Passive scan:- The passive scan can be done using zap-baseline.py script inside the docker and takes the API URL as input parameter. Go to the terminal and execute below command for initiating the passive scan:


   sudo docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t {{your_api_url}} -z options.prop -g name-of-config-file.conf -r name-of-report-file.html
   
5. For Active scan:- An Active scan can be done using zap-api-scan.py script. We can give the API specification document as a parameter. Go to the terminal and execute below command for initiating the active scan:


   sudo docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-weekly zap-api-scan.py -t yourApiSpec.json -f openapi -z "-config /zap/wrk/options.prop" -g name-of-config-file.conf -r name-of-report-file.html
   
  
### Steps for example execution(Api-securty-test-framework)  

1. For Passive scan:- The passive scan can be done using zap-baseline.py script inside the docker and takes the API URL as input parameter. Go to the terminal and execute below command for initiating the passive scan:


   sudo docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t https://reqres.in/api/users/2 -z options.prop -g sujeet-passive-scan.conf -r sujeet-passive-report.html

2. For Active scan:- An Active scan can be done using zap-api-scan.py script. We can give the API specification document as a parameter. Go to the terminal and execute below command for initiating the active scan:


   sudo docker run -v $(pwd):/zap/wrk/:rw -t owasp/zap2docker-weekly zap-api-scan.py -t myexample.json -f openapi -z "-config /zap/wrk/options.prop" -g sujeet-active-scan.conf -r sujeet-active-report.html



### Reference

1. Docker image of ZAP
  - https://github.com/zaproxy/zaproxy/tree/4ee71a18d7b1a39d8dca860b2a2247c15552b1e4/docker

2. Scan gets exited with exit code 2
  - https://groups.google.com/g/zaproxy-users/c/v_QSoUiAtyc?pli=1
  - https://groups.google.com/g/zaproxy-users/c/3EP7JdgZIwc

3. Scanning APIs with ZAP
  - https://www.zaproxy.org/blog/2017-06-19-scanning-apis-with-zap/
