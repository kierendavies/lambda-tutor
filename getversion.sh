#!/bin/bash

if [ -f $(dirname $0)/version ]; then
    version=`cat $(dirname $0)/version`
else
    version=`mvn help:evaluate -Dexpression=project.version | grep "^[0-9]\{1,\}\."`
    echo $version > $(dirname $0)/version
fi

