## Deployment CI/CD Information

TODO: The notes need to be transferred into a real documentation!

- we're using GitHub action for our pipeline (Test, SonarCloud, Build)
- After the jar is build, it will be uploaded to S3 and then deploy to Elastic Beanstalk

More Information about the AWS Infrastructure:
- The System runs in an own VPC
- The RDS is in a private subnet and can't be reached outside
- To connect outside to the RDS, we need to use the EC2 instance from our Elastic Beanstalk as a jump host
```
ssh -i ec2-key-pair.pem -f -N -L 5432:database-1.c52fr7utzoxw.eu-central-1.rds.amazonaws.com:5432 root@52.59.51.27 -v
```
<-- It creates a Tunnel, so we can connect locally against localhost:5432 against our RDS.

### Setup Infos:
- EC2 Instance need a Key Pair to connect via ssh to the instance. [Tutorial](https://linux.how2shout.com/add-a-new-key-pair-to-your-exisitng-aws-ec2-instances/)
- After the RDS installation, the Database is not created, so we need to create it manually. (SSH Tunnel, Connect to DB, Create DB)

Inspiration:

https://www.youtube.com/watch?v=buqBSiEEdQc
https://plainenglish.io/blog/hands-on-ci-cd-for-spring-boot-applications-using-github-actions-and-aws-1cbc1e2c9d54
https://aws.amazon.com/blogs/devops/integrating-with-github-actions-ci-cd-pipeline-to-deploy-a-web-app-to-amazon-ec2/
https://linux.how2shout.com/add-a-new-key-pair-to-your-exisitng-aws-ec2-instances/