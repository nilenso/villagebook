#!/bin/bash
set -e # exit immediately on error
cd $APP_HOME
lein run migrate
lein ring server-headless
