#!/bin/bash

if [ ! -z `printenv jmeter` ]; then
  echo "jmeter was not added in ENV PATH"
  exit

else
  results_path="./jmeter_results"

  if [ ! -d "${results_path}" ]; then
    mkdir -p "./jmeter_results"
  fi

  tests="./src/test/jmeter/*"

  for jmx in $tests
  do
      arr=(${jmx//"/"/" "})
      fileName=(${arr[4]//".jmx"/" "})
      if [ ! -d "${results_path}/${fileName}" ]; then
        mkdir -p "${results_path}/${fileName}"
      else
        rm -rf "${results_path}/${fileName}"
        mkdir -p "${results_path}/${fileName}"
      fi
    jmeter -n -t $jmx -l "${results_path}/${fileName}/export.csv" -e -o "${results_path}/${fileName}/export"
  done
fi

