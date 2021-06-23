# The Exploring Search

the main idea behind this project is to use as input a dataset developed in the BFH in a search engine.
<br> using a dataset that was developed in the BFH data engineering lab that contains different job offers collected from various internet websites we created an application that filters this dataset (according to the date/location/language/keyword) in order to facilitate the job seeking process for the users.
## Built with
- maven 

## Prerequisites
technologies needed: 
- java version 11 or latest
- javaFX
- [ElasticSearch](https://www.elastic.co/elasticsearch/?ultron=B-Stack-Trials-EMEA-C-Exact&gambit=Elasticsearch-install&blade=adwords-s&hulk=cpc&Device=c&thor=elasticsearch%20install&gclid=CjwKCAjwwqaGBhBKEiwAMk-FtNTCRG-2snSPRBFcyVXtGZFmIvKEQaTNN9wkCwNdclE3BO8FX43mPRoCVBIQAvD_BwE)

other: 
- job offers dataset (csv file)

## deployment
in order to run the project locally on your machine you need to: 
1. Clone this repository: `git clone git@gitlab.ti.bfh.ch:taiec1/project1-deng.git`
2. start ElasticSearch from the ```bin``` directory :
    
    - Linux and MacOS: cd elasticsearch-6.8.16/bin```<br/>```./elasticsearch```
    - <br/> Windows: ``` cd %PROGRAMFILES%\Elastic\Elasticsearch\bin``` <br/>  ```.\elasticsearch.exe```

3. index the dataset in ElasticSearch using  ```server_load.py ```
* using your IDE (Intellij/Eclipse or other): 
4. build the project : ```mvn clean install```
5. run maven project : ```mvn javafx:run```

## Checkstyle
we have used checkstyle 8.39 during this project in order to maintain a clean coding style.
<br> in the ```src/etc``` folder you will find a ```checkstyle.xml``` file that we have used. please configure your IDE accordingly if you want to checkstyle the code.

* if you use Intellij follow the steps below:
1. download the checkstyle plugin 
2. go to ```settings``` then ```tools``` then ```checkstyle```
3. in the configuration file window click on ```+``` to add a new file
4. browse to ```src/etc/checkstyle.xml``` and type a description example : oracle checkstyle
5. click on apply and checkstyle the code 






