#!/bin/bash

function start_application() {
    local command="./activator -mem 2048 -Dhttps.protocols=TLSv1 -Djava.net.preferIPv4Stack=true -Dlogger.resource=logback.xml -Dhttp.port=9000 compile run"
    local environment
    local offlineMode
    local config_file
    case $1 in
        debug) command="$command -jvm-debug 9999"
    esac
    shift


    config_file="conf/application.conf"

    if [[ "offline" == "$1" ]]; then
        offlineMode="-Doffline=true"
    else
        offlineMode=""
        shift
    fi

    if [[ ! -e ${config_file} ]]
        then
            echo "could not find $config_file"
            exit 1
        fi

    command="$command -Dconfig.file=$config_file $offlineMode"
    command="ENVIRONMENT=$environment $command"
    echo $command
    eval ${command} "$@"
}
