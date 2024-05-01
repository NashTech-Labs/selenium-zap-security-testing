# Automate API Security Test Using RestAssured

### Prerequisites

1. URL to download ZAP: https://www.zaproxy.org/

### Steps for execution

1. Open the OSWAP ZAP Tool
2. Clone the repository on your local system
3. Open the cloned project in IDE
4. Create the ".env" file inside the project directory and update the environment variable based on ".env.templates" file
5. Update the "proxy_port_zap" & "proxy_apiKey_zap" environment variable with your own "proxy" & "api key" respectively, which is shown in OSWAP ZAP tool.
6. For Passive scan:- call "waitForPassiveScanCompletion()" method inside your "ZapTest.java" class.
7. For Active scan:-  call "waitForActiveScanCompletion()" method inside your "ZapTest.java" class.

### Steps for execution(Automate-Api-Security-Test-Framework)
Right Click on 'textNG.xml' file and click on 'RUN'. It will execute the ZapTest class & the latest HTML report file is created i.e, 'api-report.html'. 

### Text Execution Report
Open the 'api-report.html' on your browser.

### Reference

Dummy API URL
https://reqres.in/
Passive Scan
http://localhost:8081/UI/pscan/ 
ZAP Client API
https://www.zaproxy.org/docs/api/
Active Scan
https://www.zaproxy.org/docs/api/#zap-api-ascan
Reports Template
http://localhost:8081/UI/reports/action/generate/

NOTE: Instead of 8081 port, you have to used your own port number.