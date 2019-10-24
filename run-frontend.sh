#!/bin/bash
set -e # exit immediately on error
cd $APP_HOME
npm install
npx shadow-cljs compile :villagebookUI
npx shadow-cljs watch :villagebookUI
