# Deployment and CI/CD Information

This document provides information about the deployment process and the CI/CD pipeline for the Gasprice Calculator Backend Application.

## CI/CD Pipeline

The CI/CD pipeline for this project is managed using GitHub Actions. The pipeline includes the following stages:

1. **Testing**: The application's test suite is run to ensure that all features are working as expected.
2. **SonarCloud Analysis**: The code is analyzed using SonarCloud to identify potential bugs, code smells, and security vulnerabilities.
3. **Build**: The application is built into a JAR file.

After the JAR file is built, it is uploaded to an Amazon S3 bucket. From there, it is deployed to an Amazon Elastic Beanstalk environment.

## AWS Infrastructure

The application is hosted on an Amazon Web Services (AWS) infrastructure. Here are some key details about this setup:

- The system runs in its own Virtual Private Cloud (VPC).
- The database is hosted on Amazon RDS, which is located in a private subnet and cannot be accessed directly from the internet.
- To connect to the RDS instance from outside the VPC, you need to use an EC2 instance from the Elastic Beanstalk environment as a jump host.

To create a tunnel for connecting to the RDS instance, use the following command:

```shell
ssh -i ec2-key-pair.pem -f -N -L 5432:YOUR_DATABASE_DNS_ADDRESS:5432 ec2-user@YOUR_EC2_IP -v
```
This command creates a tunnel that allows you to connect to the RDS instance at `localhost:5432`.

To connect to the EC2 instance, use the following command:
```shell
ssh -i ec2-key-pair.pem -l ec2-user YOUR_EC2_IP
```

### Setup Information
- The EC2 instance requires a key pair to establish an SSH connection. You can learn how to add a new key pair to your existing AWS EC2 instances in this [tutorial](https://linux.how2shout.com/add-a-new-key-pair-to-your-exisitng-aws-ec2-instances/).
- After installing RDS, the database is not created automatically. You need to create it manually. This can be done by establishing an SSH tunnel, connecting to the database, and creating the database.

### References
For more information about the deployment process and the CI/CD pipeline, you can refer to the following resources:
- [YouTube Video: CI/CD for Spring Boot Applications](https://www.youtube.com/watch?v=buqBSiEEdQc)
- [Blog Post: Hands-on CI/CD for Spring Boot Applications using GitHub Actions and AWS](https://plainenglish.io/blog/hands-on-ci-cd-for-spring-boot-applications-using-github-actions-and-aws-1cbc1e2c9d54)
- [Blog Post: Integrating with GitHub Actions CI/CD Pipeline to Deploy a Web App to Amazon EC2](https://aws.amazon.com/blogs/devops/integrating-with-github-actions-ci-cd-pipeline-to-deploy-a-web-app-to-amazon-ec2/)
- [Tutorial: Add a New Key Pair to Your Existing AWS EC2 Instances](https://linux.how2shout.com/add-a-new-key-pair-to-your-exisitng-aws-ec2-instances/)