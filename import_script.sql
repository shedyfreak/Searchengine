DROP TABLE jobs;
Create table jobs(_id varchar(255),id varchar(255),title varchar(255),description text,location varchar(100),url varchar(3000),htmlpage text,date date,language text);
COPY jobs
FROM '/home/shedy/project1-deng/jobs_dataset.csv'
DELIMITER ','
CSV HEADER; 

