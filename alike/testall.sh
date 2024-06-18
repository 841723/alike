#!/bin/bash


TEST_DIR="../test"


# run ./execute.sh -u "$TEST_DIR"/<all_directories>
for i in $(ls "$TEST_DIR"); do

    # run only .al files
    if [[ $i == *.al ]]; then

        # remove .al extension
        i=$(echo $i | cut -d'.' -f1)
        echo "Running test $i"
        ./execute.sh -u "$TEST_DIR"/"$i"
    fi
done
