
This folder contains everything related to the design and database setup.

[country_parser](https://github.com/gzougianos/mye030-cs-uoi/tree/master/database_related/country_parser) subfolder contains a **Maven** project with the source code of the parser.

[country_parser.jar](https://github.com/gzougianos/mye030-cs-uoi/blob/master/database_related/country_parser.jar) is the jar file that can be executed to fetch and store country data into the database.
It's usage:
```
usage: java -jar country_parser.jar -c ALB,GRC -u "jdbc:mysql://localhost/my_db?user=root&password=root"
 -c,--country <arg>   A country CODE or multiple country CODES to parse.
 -f,--file <arg>      A file that contains country names (each line
                      contains a country name)
 -u,--url <arg>       The MySQL Url to connect to.
```

For our project we have (randomly) selected the countries that are inside [our_countries](https://github.com/gzougianos/mye030-cs-uoi/blob/master/database_related/our_countries) file:
```
GHA
GIB
GRC
GRL
GRD
GUM
GTM
GIN
GNB
GUY
HTI
HND
HKG
HUN
ISL
```
[our_build.bat](https://github.com/gzougianos/mye030-cs-uoi/blob/master/database_related/our_build.bat) file contains the whole process of our build.

[all_country_codes](https://github.com/gzougianos/mye030-cs-uoi/blob/master/database_related/all_country_codes.txt) is a text file that contains the country code of each country that can be found in [https://data.worldbank.org/country](https://data.worldbank.org/country) .

[schema.sql](https://github.com/gzougianos/mye030-cs-uoi/blob/master/database_related/schema.sql) contains the database schema, which looks like:

![schema](https://raw.githubusercontent.com/gzougianos/mye030-cs-uoi/master/database_related/schema.png)


