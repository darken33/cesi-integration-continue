project-new --named $PROJECT_NAME --top-level-package com.example.project --final-name $PROJECT_NAME

jpa-setup --provider Eclipse Link --db-type POSTGRES --data-source-name java:comp/DefaultDataSource
jpa-new-entity --named Speaker
jpa-new-field --named firstname
jpa-new-field --named surname
jpa-new-field --named bio --length 2000
jpa-new-field --named twitter

constraint-add --on-property firstname --constraint NotNull
constraint-add --on-property surname --constraint NotNull
constraint-add --on-property bio --constraint Size --max 2000

rest-setup ;
rest-generate-endpoints-from-entities --targets com.example.project.model.Speaker ;

scaffold-setup --provider AngularJS ;
scaffold-generate --provider AngularJS --targets com.example.project.model.*
