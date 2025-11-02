LogLens
Log Analysis and Alerting Tool

Features:

Regex-based log parsing for structured & unstructured formats
1. Real-time log categorization and summarization
2. Automated email alerts for error-level logs via Gmail SMTP
3. Update Credetials before running the application

create app pass in gmail
1. update application.properties
2. update email address in code to whom you want to trigger the email.
   
Steps to run:
1. git clone https://github.com/amarkrsoni/loglens.git
2. cd loglens
3. mvn clean install
4. mvn spring-boot:run
