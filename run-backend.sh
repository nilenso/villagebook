#!/bin/bash
set -e
cd $APP_HOME
lein clean
lein run migrate
lein ring server-headless
