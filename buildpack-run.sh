#!/bin/bash

echo "cd ./front" \
&& cd ./front \
&& echo "npm install" \
&& npm install \
&& echo "npm run build" \
&& npm run build \
&& echo "cd .." \
&& cd .. \
&& echo "rm -rf ./back-office/src/main/resources/static" \
&& rm -rf ./back-office/src/main/resources/static \
&& echo "mv ./front/dist ./back-office/src/main/resources/static" \
&& mv ./front/dist ./back-office/src/main/resources/static
