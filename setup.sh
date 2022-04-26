#!/usr/bin/bash

heroku_url=$(heroku config -a scm-daw | grep DATABASE_URL)

#echo $heroku_url

export DATABASE_URL=$(echo $heroku_url | awk -F '@' '{print $2}')

credentials=$(heroku config -a scm-daw | grep DATABASE_URL | awk -F'[/@]' '{print $3}')

export USER=$(echo $credentials | awk -F':' '{print $1}')
export PASSWORD=$(echo $credentials | awk -F':' '{print $2}')
